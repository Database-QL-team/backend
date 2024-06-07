package ggyuel.ggyuup.db2024PStogether.service;

import ggyuel.ggyuup.db2024PStogether.dto.PSTogetherRequestDTO;
import ggyuel.ggyuup.global.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PSTogetherDelete {
    public static Boolean PSTogethercheckPW(PSTogetherRequestDTO.PSTogetherDeleteDTO delete) {

        try {
            // DB 연결
            Connection conn = DBConnection.getDbPool().getConnection();

            // 사용자가 삭제하고자 하는 게시물 id, pw 추출
            int togetherid = delete.getTogetherid();
            String pw = delete.getPw();

            // 비밀번호 일치 여부
            Boolean PWcheck = Boolean.FALSE;

            // 쿼리 실행
            String query = "SELECT * FROM DB2024_PStogether WHERE togetherid="+togetherid;
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            System.out.println(query);

            if(rs.next()) {
                // DB에서 pw 추출
                String realPW = rs.getString("pw");

                // 입력된 비밀 번호와 실제 비밀 번호 비교 -> PWcheck
                if (pw.equals(realPW)) {
                    PWcheck = Boolean.TRUE;
                }
                else {
                    PWcheck = Boolean.FALSE;
                }
            }

            // 자원 해제
            rs.close();
            pstmt.close();
            conn.close();
            return PWcheck;


        } catch (SQLException e){
            System.out.println(e);
        }
        return null;
    }

    public static String PSTogetherDelete (PSTogetherRequestDTO.PSTogetherDeleteDTO delete) {

        // 반환 메시지
        String resultMessage = "삭제되었습니다";

        try {
            // DB 연결
            Connection conn = DBConnection.getDbPool().getConnection();

            // 게시물 id 추출
            int togetherid = delete.getTogetherid();
            String query = "DELETE FROM DB2024_PStogether WHERE togetherid = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                // 매개 변수 입력
                pstmt.setInt(1, togetherid);

                // 쿼리 실행
                int rowsAffected = pstmt.executeUpdate();

                // 해당 게시물이 존재하지 않을 경우
                if(rowsAffected == 0) {
                    resultMessage = "삭제할 데이터가 없습니다";
                }
            }

            // 자원 해제
            conn.close();

        } catch (SQLException e){
            System.out.println(e);
        }
        return resultMessage;


    }
}

package ggyuel.ggyuup.db2024PStogether.service;

import ggyuel.ggyuup.db2024PStogether.dto.PSTogetherRequestDTO;
import ggyuel.ggyuup.db2024PStogether.dto.PSTogetherResponseDTO;
import ggyuel.ggyuup.global.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PSTogetherSave {
    public static PSTogetherResponseDTO.PSTogetherDetailDTO psTogetherSave(PSTogetherRequestDTO.PSTogetherSaveDTO request) {
        if (request == null || request.getTogethertitle() == null || request.getPid() == 0 || request.getLink() == null || request.getHandle() == null || request.getPw() == null) {
            System.out.println("입력 값이 유효하지 않습니다.");
            return null;
        }

        try {
            Connection conn = DBConnection.getDbPool().getConnection();
            System.out.println("DB 연결");

            String sql = "INSERT INTO DB2024_PStogether (togethertitle, pid, link, handle, pw) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, new String[]{"togetherid"}); // togetherid 반환

            // 사용자 입력 받아오기
            String togethertitle = request.getTogethertitle();
            int pid = request.getPid();
            String link = request.getLink();
            String handle = request.getHandle();
            String pw = request.getPw();

            // 입력 값 로그 기록
            System.out.println("입력 값: " + togethertitle + ", " + pid + ", " + link + ", " + handle + ", " + pw);

            // DB에 저장
            pstmt.setString(1, togethertitle);
            pstmt.setInt(2, pid);
            pstmt.setString(3, link);
            pstmt.setString(4, handle);
            pstmt.setString(5, pw);
            pstmt.executeUpdate();

            // 생성된 키 받아오기
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            int togetherid = 0;
            if (generatedKeys.next()) {
                togetherid = generatedKeys.getInt(1);
            } else {
                throw new SQLException("게시물을 추가하는 동안 키 생성 실패");
            }

            conn.commit();

            pstmt.close();
            conn.close();

            // PSTogetherDetailDTO에 togetherId 포함하여 반환
            return new PSTogetherResponseDTO.PSTogetherDetailDTO(togetherid, pid, togethertitle, handle, link, pw);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}



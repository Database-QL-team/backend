package ggyuel.ggyuup.db2024PStogether.service;

import ggyuel.ggyuup.db2024PStogether.dto.PSTogetherResponseDTO;
import ggyuel.ggyuup.global.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * PSTogether의 세부 정보를 가져오는 서비스를 제공하는 클래스입니다.
 */
public class PSTogetherGetDetail {

    /**
     * 주어진 게시글의 ID를 사용하여 해당하는 세부 정보를 가져옵니다.
     *
     * @param num 게시글의 ID
     * @return PSTogether의 세부 정보를 담은 DTO 객체를 반환합니다. 정보를 가져오지 못할 경우 null을 반환합니다.
     */
    public static PSTogetherResponseDTO.PSTogetherDetailDTO psTogetherGetDetail(int num) {

        try {
            Connection conn = DBConnection.getDbPool().getConnection();

            // 사용자 입력 게시글 id
            int whichtogetherid = num;

            String sql = "SELECT * FROM DB2024_PStogether WHERE togetherid = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // 매개 변수 삽입
            pstmt.setInt(1, whichtogetherid);

            ResultSet rs = pstmt.executeQuery();

            // 튜플로부터 togetherid, pid, handle, link, pw 추출
            if (rs.next()) {
                int togetherid = rs.getInt("togetherid");
                int pid = rs.getInt("pid");
                String title = rs.getString("togethertitle");
                String handle = rs.getString("handle");
                String link = rs.getString("link");
                String pw = rs.getString("pw");

                // 자원 반환 및 연결 종료
                rs.close();
                pstmt.close();
                conn.close();

                // PSTogether 세부 정보 DTO 객체 생성 및 반환
                return new PSTogetherResponseDTO.PSTogetherDetailDTO(togetherid, pid, title, handle, link, pw);
            }
        } catch (SQLException e) {
            // 예외 처리
            System.out.println("SQLException occurred: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}


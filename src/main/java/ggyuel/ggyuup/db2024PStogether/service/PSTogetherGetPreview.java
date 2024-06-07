package ggyuel.ggyuup.db2024PStogether.service;

import ggyuel.ggyuup.db2024PStogether.dto.PSTogetherResponseDTO;
import ggyuel.ggyuup.global.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 게시글의 미리보기 정보를 가져오는 서비스를 제공하는 클래스입니다.
 */
public class PSTogetherGetPreview {

    /**
     * 모든 게시글의 미리보기 정보를 가져옵니다.
     *
     * @return 게시글 미리보기 정보를 담은 DTO 객체의 리스트를 반환합니다. 정보를 가져오지 못할 경우 null을 반환합니다.
     */
    public static ArrayList<PSTogetherResponseDTO.PSTogetherPreviewDTO> getPSTogetherPreview() {
        try {
            // DB 연결
            Connection conn = DBConnection.getDbPool().getConnection();
            System.out.println("DB 연결");

            // SQL 쿼리 작성 및 실행
            String query = "SELECT * FROM DB2024_PStogether";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            ArrayList<PSTogetherResponseDTO.PSTogetherPreviewDTO> result = new ArrayList<>();

            // 결과 처리
            while (rs.next()) {
                // 튜플에서 togetherid, pid, handle, togethertitle 추출 후 PSTogetherPreviewDTO 생성 및 ArrayList 삽입
                int togetherId = rs.getInt("togetherid");
                int problemId = rs.getInt("pid");
                String handle = rs.getString("handle");
                String title = rs.getString("togethertitle");
                result.add(new PSTogetherResponseDTO.PSTogetherPreviewDTO(togetherId, problemId, title, handle));
            }

            // 자원 반환 및 연결 종료
            rs.close();
            pstmt.close();
            conn.close();

            // 게시글 미리보기 리스트 반환
            return result;

        } catch (SQLException e){
            // SQL 예외 처리
            System.out.println("SQLException occurred: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}


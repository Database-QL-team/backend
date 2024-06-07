package ggyuel.ggyuup.db2024PStogether.service;

import ggyuel.ggyuup.db2024PStogether.dto.PSTogetherResponseDTO;
import ggyuel.ggyuup.global.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * PSTogetherSearch 클래스는 PSTogether 게시물을 검색하는 기능을 제공합니다.
 */
public class PSTogetherSearch {

    /**
     * 주어진 p_id에 해당하는 PSTogether 게시물들을 검색하고, 검색 결과를 PSTogetherResponseDTO.PSTogetherPreviewDTO의 리스트로 반환합니다.
     *
     * @param p_id 검색할 PSTogether 게시물들의 p_id
     * @return PSTogetherResponseDTO.PSTogetherPreviewDTO의 리스트로 구성된 PSTogether 게시물 검색 결과, 검색에 실패하면 null 반환
     */
    public static ArrayList<PSTogetherResponseDTO.PSTogetherPreviewDTO> getPSTogetherSearch(int p_id) {
        try {
            // DB 연결
            Connection conn = DBConnection.getDbPool().getConnection();
            System.out.println("DB 연결");

            // 쿼리 준비
            int whichPid = p_id;
            String query = "SELECT * FROM DB2024_PStogether WHERE pid=" + whichPid;

            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            ArrayList<PSTogetherResponseDTO.PSTogetherPreviewDTO> result = new ArrayList<>();

            // 튜플에서 togetherid, pid, handle, togethertitle 추출
            while (rs.next()) {
                int togetherid = rs.getInt("togetherid");
                int pid = rs.getInt("pid");
                String handle = rs.getString("handle");
                String togethertitle = rs.getString("togethertitle");

                // 추출된 데이터 바탕으로 PSTogetherPreviewDTO 생성한 뒤 각각의 DTO ArrayList에 삽입
                result.add(new PSTogetherResponseDTO.PSTogetherPreviewDTO(togetherid, pid, togethertitle, handle));
            }

            // 리소스 닫기
            rs.close();
            pstmt.close();
            conn.close();

            // 게시물 리스트 반환
            return result;

        } catch (SQLException e){
            System.out.println(e);
        }
        return null;
    }
}

package ggyuel.ggyuup.db2024Students.service;

import ggyuel.ggyuup.db2024Students.dto.StudentRankResponseDTO;
import ggyuel.ggyuup.global.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * StudentRank 클래스는 학생 순위 관련 기능을 제공합니다.
 */
public class StudentRank {

    /**
     * 학생들을 순위에 따라 정렬하여 반환합니다.
     *
     * @return 학생 순위에 따라 정렬된 학생 정보 리스트
     */
    public static ArrayList<StudentRankResponseDTO.StudentRankDTO> getStudentsOrderedByRank() {

        try {
            // DB 연결
            Connection conn = DBConnection.getDbPool().getConnection();

            // 쿼리 작성
            String query = "SELECT * FROM DB2024_Students ORDER BY rank_ingroup ASC";
            PreparedStatement pstmt = conn.prepareStatement(query);

            // 결과 처리
            ResultSet rs = pstmt.executeQuery();
            ArrayList<StudentRankResponseDTO.StudentRankDTO> result = new ArrayList<>();

            while (rs.next()) {
                // 튜플로부터 rank_ingroup, handle, userlink, tier, solvednum 추출
                int rank_ingroup = rs.getInt("rank_ingroup");
                String handle = rs.getString("handle");
                String userlink = rs.getString("userlink");
                String tier = rs.getString("tier");
                int solvednum = rs.getInt("solvednum");

                // 추출된 데이터를 통해 StudentRankDTO 객체 생성, ArrayList에 삽입
                result.add(new StudentRankResponseDTO.StudentRankDTO(rank_ingroup, handle, userlink, tier, solvednum));
            }

            // 자원 해제
            rs.close();
            pstmt.close();
            conn.close();

            return result;

        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
}


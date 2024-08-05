package ggyuel.ggyuup.Problems.service;

import ggyuel.ggyuup.Algorithms.domain.Algo;
import ggyuel.ggyuup.Problems.dto.ProblemRequestDTO;
import ggyuel.ggyuup.global.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 티어에 따라 문제를 가져오는 서비스 클래스입니다.
 */
public class ProblemTier {
    /**
     * 지정된 티어에 따라 문제 목록을 반환합니다.
     *
     * @param tier 문제의 티어
     * @return 문제 목록을 포함하는 ArrayList 객체
     */
    public static ArrayList<ProblemRequestDTO.ProblemTierDTO> getProblemsByTier(int tier) {
        try {
            // DB 연결
            Connection conn = DBConnection.getDbPool().getConnection();

            // 요청된 티어
            int whichTier = tier;

            // 쿼리 작성
            String query = "SELECT * FROM problems WHERE tier = ? ORDER BY solved_people DESC";
            PreparedStatement pstmt = conn.prepareStatement(query);

            // 티어별 문제 목록을 저장할 ArrayList 생성
            ArrayList<ProblemRequestDTO.ProblemTierDTO> tierProblems = new ArrayList<>();

            // 티어 매개변수를 설정
            pstmt.setInt(1, whichTier);
            ResultSet rs = pstmt.executeQuery();

            // 결과 처리
            while (rs.next()) {
                // 튜플에서 pid, pTitle, link, solvednum 추출 후 ProblemTierDTO 객체 생성 후 ArrayList에 삽입
                int problemId = rs.getInt("problem_id");
                String title = rs.getString("title");
                String link = rs.getString("link");
                int solvedPeople = rs.getInt("solved_people");

                tierProblems.add(new ProblemRequestDTO.ProblemTierDTO(problemId, title, link, solvedPeople));
            }

            System.out.println(tierProblems);

            // 자원 해제
            rs.close();
            pstmt.close();
            conn.close();

            // 문제 배열 반환
            return tierProblems;
        } catch (SQLException e) {
            System.out.println(e);
        }

        return null;
    }
}


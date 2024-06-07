package ggyuel.ggyuup.db2024Problems.service;

import ggyuel.ggyuup.db2024Problems.dto.ProblemResponseDTO;
import ggyuel.ggyuup.global.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 알고리즘 태그에 따라 문제를 가져오는 서비스 클래스입니다.
 */
public class ProblemAlgo {
    /**
     * 지정된 알고리즘 태그에 따라 문제 목록을 반환합니다.
     *
     * @param request 알고리즘 태그
     * @return 문제 목록을 포함하는 ArrayList 객체
     */
    public static ArrayList<ProblemResponseDTO.ProblemAlgoDTO> getProblemsByTag(String request) {
        try {
            // DB 연결
            Connection conn = DBConnection.getDbPool().getConnection();
            System.out.println("DB 연결");

            String whichTag = request;
            System.out.println(whichTag);

            // 쿼리 작성
            String query = "SELECT * FROM DB2024_VIEW_tag_" + whichTag + " ORDER BY solvednum DESC";
            PreparedStatement pstmt = conn.prepareStatement(query);
            System.out.println(query);

            ResultSet rs = pstmt.executeQuery();
            ArrayList<ProblemResponseDTO.ProblemAlgoDTO> result = new ArrayList<>();

            // 결과 처리
            while (rs.next()) {
                // 튜플에서 pid, pTitle, link, solvednum, tier 추출
                int pid = rs.getInt("pid");
                String pTitle = rs.getString("pTitle");
                String link = rs.getString("link");
                int solvednum = rs.getInt("solvednum");
                int tier = rs.getInt("tier");

                // 추출한 데이터로 ProblemAlgoDTO 객체 생성 및 각 DTO 객체 ArrayList에 추가
                result.add(new ProblemResponseDTO.ProblemAlgoDTO(pid, pTitle, link, solvednum, tier));
            }


            // 자원 해환
            rs.close();
            pstmt.close();
            conn.close();

            // 문제 배열 반환
            return result;
        } catch (SQLException e) {
            // 예외 처리
            System.out.println(e);
        }
        return null;
    }
}

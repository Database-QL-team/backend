package ggyuel.ggyuup.Main.service;

import ggyuel.ggyuup.Main.dto.MainResponseDTO;
import ggyuel.ggyuup.global.DBConnection;

import java.sql.*;
import java.util.ArrayList;

/**
 * MainPage 클래스는 메인 페이지와 관련된 서비스 로직을 처리합니다.
 */
public class MainPage {

    /**
     * 이화여자대학교의 그룹 정보를 가져오는 메서드입니다.
     *
     * @return 이화여자대학교의 그룹 정보를 담은 GroupInfoDTO 객체
     */
    public static MainResponseDTO.GroupInfoDTO getGroupInfo() {
        try {
            // 변수 선언 및 초기화
            int ewhaRank = 0;
            int ewhaSolvedNum = 0;
            String rivalName = null;
            int rivalRank = 0;
            int rivalSolvedNum = 0;
            int solvedNumGap = 0;

            // 데이터베이스 연결
            Connection conn = DBConnection.getDbPool().getConnection();

            // 이화여자대학교의 랭킹 및 푼 문제 수 조회
            PreparedStatement pstmt1 = conn.prepareStatement(
                    "SELECT org_rank, solved_num "
                            + "FROM organizations "
                            + "WHERE name = ?");

            pstmt1.setString(1, "이화여자대학교");
            ResultSet rs1 = pstmt1.executeQuery();

            // DB에서 추출한 이화여자대학교 튜플에서 org_rank과 solved_num 칼럼 정보 각각 ewhaRank, ewhaSolvedNum 변수에 저장
            if (rs1.next()) {
                ewhaRank = rs1.getInt("org_rank");
                ewhaSolvedNum = rs1.getInt("solved_num");
            }

            // 전 순위 그룹의 랭킹, 이름, 해결된 문제 수 조회
            PreparedStatement pstmt2 = conn.prepareStatement(
                    "SELECT org_rank, name, solved_num "
                            + "FROM organizations "
                            + "WHERE org_rank = ((SELECT org_rank FROM organizations WHERE name = ?) - 1)");

            pstmt2.setString(1, "이화여자대학교");
            ResultSet rs2 = pstmt2.executeQuery();

            // DB에서 추출한 전 순위 그룹 튜플에서 groupname, ranking과 solvednum 칼럼 정보 각각 rival_group_name, rival_ranking, rival_solvednum 변수에 저장
            if (rs2.next()) {
                rivalName = rs2.getString("name");
                rivalRank = rs2.getInt("org_rank");
                rivalSolvedNum = rs2.getInt("solved_num");
            }

            // 푼 문제 수 차이 계산
            solvedNumGap = rivalSolvedNum - ewhaSolvedNum;

            // GroupInfoDTO 객체 생성
            MainResponseDTO.GroupInfoDTO groupInfoDTO = new MainResponseDTO.GroupInfoDTO(ewhaRank, rivalRank, rivalName, solvedNumGap);

            // 자원 해제
            rs1.close();
            rs2.close();
            pstmt1.close();
            pstmt2.close();
            conn.close();
            return groupInfoDTO;

        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

//    /**
//     * 오늘의 문제 리스트를 가져오는 메서드입니다.
//     *
//     * @return 오늘의 문제 리스트를 담은 ArrayList
//     */
//    public static ArrayList<MainResponseDTO.TodayPSDTO> getTodayPS() {
//        try {
//            // 데이터베이스 연결
//            Connection conn = DBConnection.getDbPool().getConnection();
//            Statement stmt = conn.createStatement();
//            ArrayList<MainResponseDTO.TodayPSDTO> TodayPSlist = new ArrayList<>();
//
//            // 오늘의 문제 조회
//            ResultSet rs = stmt.executeQuery("SELECT * "
//                    + "FROM DB2024_Problems NATURAL JOIN DB2024_TodayPS "
//                    + "ORDER BY tier");
//
//            // 결과를 TodayPSDTO 객체로 변환하여 리스트에 추가
//            while (rs.next()) {
//                int pid = rs.getInt("pid");
//                String pTitle = rs.getString("pTitle");
//                int tier = rs.getInt("tier");
//                int picked = rs.getInt("picked"); // null값일 수 있으므로 wrapper
//                String handle = rs.getString("handle");
//
//                TodayPSlist.add(new MainResponseDTO.TodayPSDTO(pid, pTitle, tier, picked, handle));
//            }
//
//            // 자원 해제
//            rs.close();
//            conn.close();
//
//            return TodayPSlist;  // 브론즈 5개, 실버 5개, 골드 5개 순서로 반환
//
//        } catch (SQLException e) {
//            System.out.println(e);
//        }
//        return null;
//    }

    /**
     * 메인 페이지의 정보를 가져오는 메서드입니다.
     *
     * @return 메인 페이지 정보를 담은 MainPageDTO 객체
     */
    public static MainResponseDTO.MainPageDTO getMainPage() {
        // 그룹 정보 및 오늘의 문제 리스트 조회
        MainResponseDTO.GroupInfoDTO groupInfoDTO = getGroupInfo();
//        ArrayList<MainResponseDTO.TodayPSDTO> todayPSDTOList = getTodayPS();

        // MainPageDTO 객체 생성 및 설정
        MainResponseDTO.MainPageDTO mainPageDTO = new MainResponseDTO.MainPageDTO();
        mainPageDTO.setGroupInfo(groupInfoDTO);
//        mainPageDTO.setTodayPSList(todayPSDTOList);

        return mainPageDTO;
    }
}


package ggyuel.ggyuup.db2024Main.service;

import ggyuel.ggyuup.db2024Main.dto.MainResponseDTO;
import ggyuel.ggyuup.global.DBConnection;

import java.sql.*;
import java.util.ArrayList;

public class MainPage {
    public static MainResponseDTO.GroupInfoDTO getGroupInfo() {
        try {
            int ewha_ranking = 0;
            int ewha_solvednum = 0;
            String rival_group_name = null;
            int rival_ranking = 0;
            int rival_solvednum = 0;
            int solved_num_gap = 0;

            Connection conn = DBConnection.getDbPool().getConnection();
            System.out.println("DB연결");
            PreparedStatement pstmt1 = conn.prepareStatement(
                    "select ranking, solvednum "
                            + "from DB2024_Organizations "
                            + "where groupname = ?");

            pstmt1.setString(1, "이화여자대학교");
            ResultSet rs1 = pstmt1.executeQuery();

            if(rs1.next()) {
                ewha_ranking = rs1.getInt("ranking");
                ewha_solvednum = rs1.getInt("solvednum");

                // Use the variables as needed
                System.out.println("Ranking: " + ewha_ranking);
                System.out.println("Solved Number: " + ewha_solvednum);
            }


// 메서드2 직전 등수 단체 구하기: groupname을 인자로 받아야 함 (이화여자대학교)
            PreparedStatement pstmt2 = conn.prepareStatement(
                    "SELECT ranking, groupname, solvednum " +
                            "FROM DB2024_Organizations " +
                            "WHERE ranking = ((SELECT ranking FROM DB2024_Organizations WHERE groupname = ?) - 1)");

            pstmt2.setString(1, "이화여자대학교");
            ResultSet rs2 = pstmt2.executeQuery();
            if(rs2.next()) {
                rival_group_name = rs2.getString("groupName");
                rival_ranking = rs2.getInt("ranking");
                rival_solvednum = rs2.getInt("solvednum");
                // Use the variables as needed
                System.out.println("GroupName: " + rival_group_name);
                System.out.println("Ranking: " + rival_ranking);
                System.out.println("Solved Number: " + rival_solvednum);
            }

            solved_num_gap = rival_solvednum - ewha_solvednum;

            MainResponseDTO.GroupInfoDTO groupInfoDTO = new MainResponseDTO.GroupInfoDTO(ewha_ranking, rival_ranking, rival_group_name, solved_num_gap);

            rs1.close();
            rs2.close();
            pstmt1.close();
            pstmt2.close();
            conn.close();
            return groupInfoDTO;

        } catch (SQLException e){
            System.out.println(e);
        }
        return null;
    }

    public static String postTodayPS() {
        try {
            Connection conn = DBConnection.getDbPool().getConnection();
            ArrayList<MainResponseDTO.TodayPSDTO> PostTodayPS = new ArrayList<>();
            conn.setAutoCommit(false);

            String query1 = "INSERT INTO DB2024_TodayPS (pid, picked, handle)" +
                    " SELECT pid, 0, NULL " +
                    " FROM ( " +
                    "     SELECT pid " +
                    "     FROM DB2024_Problems " +
                    "     WHERE tier BETWEEN 1 AND 5 " +
                    "     ORDER BY solvednum DESC " +
                    "     LIMIT 5 " +
                    " ) AS subset; ";

            String query2 = "INSERT INTO DB2024_TodayPS (pid, picked, handle)" +
                    " SELECT pid, 0, NULL " +
                    " FROM ( " +
                    "     SELECT pid " +
                    "     FROM DB2024_Problems " +
                    "     WHERE tier BETWEEN 6 AND 10 " +
                    "     ORDER BY solvednum DESC " +
                    "     LIMIT 5 " +
                    " ) AS subset; ";

            String query3 = "INSERT INTO DB2024_TodayPS (pid, picked, handle)" +
                    " SELECT pid, 0, NULL " +
                    " FROM ( " +
                    "     SELECT pid " +
                    "     FROM DB2024_Problems " +
                    "     WHERE tier BETWEEN 11 AND 15 " +
                    "     ORDER BY solvednum DESC " +
                    "     LIMIT 5 " +
                    " ) AS subset; ";

            PreparedStatement pstmt1 = conn.prepareStatement(query1);
            PreparedStatement pstmt2 = conn.prepareStatement(query2);
            PreparedStatement pstmt3 = conn.prepareStatement(query3);
            pstmt1.executeUpdate();
            pstmt2.executeUpdate();
            pstmt3.executeUpdate();

            conn.commit();
            System.out.println("저장 성공");


        } catch (SQLException e){
            System.out.println(e);
        }

        return "TodayPS 생성 성공";
    }

    public static ArrayList<MainResponseDTO.TodayPSDTO> getTodayPS() {
        try {
            Connection conn = DBConnection.getDbPool().getConnection();
            Statement stmt = conn.createStatement();
            ArrayList<MainResponseDTO.TodayPSDTO> TodayPSlist = new ArrayList<>();

            ResultSet rs = stmt.executeQuery("select * "
                    + "from DB2024_Problems natural join DB2024_TodayPS "
                    + "order by tier");

            while (rs.next()) {
                int pid = rs.getInt("pid");
                String pTitle = rs.getString("pTitle");
                int tier = rs.getInt("tier");
                int picked = rs.getInt("picked"); // null값일수 있으므로 wrapper
                String handle = rs.getString("handle");

                TodayPSlist.add(new MainResponseDTO.TodayPSDTO (pid, pTitle, tier, picked, handle));
            }
            return TodayPSlist;  // 브론즈 5개, 실버 5개, 골드 5개 순서로 반환함
        } catch (SQLException e){
            System.out.println(e);
        }
        return null;


    }

    public static MainResponseDTO.MainPageDTO getMainPage() {
        MainResponseDTO.GroupInfoDTO groupInfoDTO = getGroupInfo();
        ArrayList<MainResponseDTO.TodayPSDTO> todayPSDTOList = getTodayPS();

        MainResponseDTO.MainPageDTO mainPageDTO = new MainResponseDTO.MainPageDTO();
        mainPageDTO.setGroupInfo(groupInfoDTO);
        mainPageDTO.setTodayPSList(todayPSDTOList);

        return mainPageDTO;

    }
}

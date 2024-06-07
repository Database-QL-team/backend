package ggyuel.ggyuup.db2024Problems.service;

import ggyuel.ggyuup.db2024Problems.dto.ProblemResponseDTO;
import ggyuel.ggyuup.global.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProblemAlgo {
    public static ArrayList<ProblemResponseDTO.ProblemAlgoDTO> getProblemsByTag (String request) {

        try {
            Connection conn = DBConnection.getDbPool().getConnection();
            System.out.println("DB 연결");

            String whichTag = request;
            System.out.println(whichTag);

            String query = "SELECT * FROM DB2024_VIEW_tag_" + whichTag + " ORDER BY solvednum DESC";
            PreparedStatement pstmt = conn.prepareStatement(query);
            System.out.println(query);

            ResultSet rs = pstmt.executeQuery();
            ArrayList<ProblemResponseDTO.ProblemAlgoDTO> result = new ArrayList<>();

            while (rs.next()) {
                // Process each row and add it to the result list
                int pid = rs.getInt("pid");
                String pTitle = rs.getString("pTitle");
                String link = rs.getString("link");
                int solvednum = rs.getInt("solvednum");
                int tier = rs.getInt("tier");

                result.add(new ProblemResponseDTO.ProblemAlgoDTO(pid, pTitle, link, solvednum, tier));
            }

            System.out.println(result);

            rs.close();
            pstmt.close();
            conn.close();

            return result;
        }
        catch (SQLException e){
            System.out.println(e);
        }
        return null;
    }
}

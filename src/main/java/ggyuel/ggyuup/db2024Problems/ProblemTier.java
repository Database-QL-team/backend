package ggyuel.ggyuup.db2024Problems;


import ggyuel.ggyuup.db2024Problems.dto.ProblemRequestDTO;
import ggyuel.ggyuup.db2024Problems.dto.ProblemResponseDTO;
import ggyuel.ggyuup.global.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProblemTier {
    public static ArrayList<ProblemResponseDTO.ProblemTierDTO> getProblemsByTier(ProblemRequestDTO.ProblemTierTag request) {

        try{
            Connection conn = DBConnection.getDbPool().getConnection();

            String whichTier = request.toString();

            String query = "SELECT * FROM DB2024_Problems WHERE tier = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);

            ArrayList<ProblemResponseDTO.ProblemTierDTO> tierProblems = new ArrayList<>();

            // Set the tier parameter
            pstmt.setString(1, whichTier);

            // Execute the query
            ResultSet rs = pstmt.executeQuery();

            // Process each row and add it to the tierProblems list
            while (rs.next()) {
                int pid = rs.getInt("pid");
                String pTitle = rs.getString("pTitle");
                String link = rs.getString("link");
                int solvednum = rs.getInt("solvednum");
                String tier = rs.getString("tier");

                tierProblems.add(new ProblemResponseDTO.ProblemTierDTO (pid, pTitle, link, solvednum, tier));
            }
            return tierProblems;
        } catch (SQLException e){
            System.out.println(e);
        }
        return null;
        }

}

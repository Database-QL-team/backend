package ggyuel.ggyuup.db2024Students.service;

import ggyuel.ggyuup.db2024Students.dto.StudentRankResponseDTO;
import ggyuel.ggyuup.global.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentRank {
    public static ArrayList<StudentRankResponseDTO.StudentRankDTO> getStudentsOrderedByRank() {

        try{
            Connection conn = DBConnection.getDbPool().getConnection();

            String query = "SELECT * FROM DB2024_Students ORDER BY rank_ingroup ASC";
            PreparedStatement pstmt = conn.prepareStatement(query);

            // Result
            ResultSet rs = pstmt.executeQuery();
            ArrayList<StudentRankResponseDTO.StudentRankDTO> result = new ArrayList<>();

            while (rs.next()) {
                int rank_ingroup = rs.getInt("rank_ingroup");
                String handle = rs.getString("handle");
                String userlink = rs.getString("userlink");
                String tier = rs.getString("tier");
                int solvednum = rs.getInt("solvednum");

                // Add the row data to the result list
                result.add(new StudentRankResponseDTO.StudentRankDTO(rank_ingroup, handle, userlink, tier, solvednum));
            }
            rs.close();
            pstmt.close();
            conn.close();

            return result;

        } catch (SQLException e){
            System.out.println(e);
        }
        return null;
    }
}

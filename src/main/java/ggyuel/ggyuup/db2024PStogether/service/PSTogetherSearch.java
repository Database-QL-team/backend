package ggyuel.ggyuup.db2024PStogether.service;

import ggyuel.ggyuup.db2024PStogether.dto.PSTogetherResponseDTO;
import ggyuel.ggyuup.global.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PSTogetherSearch {
    public static ArrayList<PSTogetherResponseDTO.PSTogetherPreviewDTO> getPSTogetherSearch(int p_id) {
        try {
            Connection conn = DBConnection.getDbPool().getConnection();
            System.out.println("DB 연결");

            int whichPid = p_id;

            System.out.println(whichPid);

            String query = "SELECT * FROM DB2024_PStogether WHERE pid="+whichPid;
            System.out.println(query);

            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            ArrayList<PSTogetherResponseDTO.PSTogetherPreviewDTO> result = new ArrayList<>();

            while (rs.next()) {
                // Process each row and add it to the result list
                int pid = rs.getInt("pid");
                String handle = rs.getString("handle");
                String togethertitle = rs.getString("togethertitle");
                result.add(new PSTogetherResponseDTO.PSTogetherPreviewDTO(pid, togethertitle, handle));
            }

            rs.close();
            pstmt.close();
            conn.close();
            System.out.println(result);
            return result;

        } catch (SQLException e){
            System.out.println(e);
        }
        return null;

    }
}

package ggyuel.ggyuup.db2024PStogether.service;

import ggyuel.ggyuup.db2024PStogether.dto.PSTogetherRequestDTO;
import ggyuel.ggyuup.db2024PStogether.dto.PSTogetherResponseDTO;
import ggyuel.ggyuup.db2024Students.dto.StudentRankRequestDTO;
import ggyuel.ggyuup.global.DBConnection;
import ggyuel.ggyuup.global.Variable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PSTogetherGetDetail {
    public static PSTogetherResponseDTO.PSTogetherDetailDTO psTogetherGetDetail(int num) {

        try {

            Connection conn = DBConnection.getDbPool().getConnection();
            System.out.println("DB 연결");

            int whichtogetherid = num;

            String sql = "SELECT * FROM DB2024_PStogether WHERE togetherid ="+whichtogetherid;

            PreparedStatement pstmt = conn.prepareStatement(sql);
            conn.setAutoCommit(false);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                int togetherid = rs.getInt("togetherid");
                int pid = rs.getInt("pid");
                String title = rs.getString("togethertitle");
                String handle = rs.getString("handle");
                String link = rs.getString("link");
                String pw = rs.getString("pw");

                PSTogetherResponseDTO.PSTogetherDetailDTO result = new PSTogetherResponseDTO.PSTogetherDetailDTO(togetherid, pid, title, handle, link, pw);

                conn.commit();

                System.out.println("저장 성공");

                rs.close();
                pstmt.close();
                conn.close();
                return result;
            }


        } catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}

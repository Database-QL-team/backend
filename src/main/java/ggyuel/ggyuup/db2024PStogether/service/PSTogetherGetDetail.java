package ggyuel.ggyuup.db2024PStogether.service;

import ggyuel.ggyuup.db2024PStogether.dto.PSTogetherResponseDTO;
import ggyuel.ggyuup.global.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PSTogetherGetDetail {
    public static PSTogetherResponseDTO.PSTogetherDetailDTO psTogetherGetDetail(int num) {

        try {
            Connection conn = DBConnection.getDbPool().getConnection();
            System.out.println("DB 연결");

            int whichtogetherid = num;

            String sql = "SELECT * FROM DB2024_PStogether WHERE togetherid = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, whichtogetherid);
            conn.setAutoCommit(false);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int togetherid = rs.getInt("togetherid");
                int pid = rs.getInt("pid");
                String title = rs.getString("togethertitle");
                String handle = rs.getString("handle");
                String link = rs.getString("link");
                String pw = rs.getString("pw");

                conn.commit();
                System.out.println("저장 성공");

                rs.close();
                pstmt.close();
                conn.close();

                return new PSTogetherResponseDTO.PSTogetherDetailDTO(pid, title, handle, link, pw);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}

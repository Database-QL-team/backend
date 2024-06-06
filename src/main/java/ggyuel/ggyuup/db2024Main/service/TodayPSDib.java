package ggyuel.ggyuup.db2024Main.service;

import ggyuel.ggyuup.db2024Main.dto.MainRequestDTO;
import ggyuel.ggyuup.global.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TodayPSDib {
    public static String putTodayPSpicked(MainRequestDTO.TodayPSDibInfoDTO request) {
        try (
                Connection conn = DBConnection.getDbPool().getConnection();
                PreparedStatement pstmt1 = conn.prepareStatement("select picked from DB2024_TodayPS where pid = ?");
                PreparedStatement pstmt2 = conn.prepareStatement("select picked from DB2024_Students where handle = ?");
                PreparedStatement pstmt3 = conn.prepareStatement("update DB2024_TodayPS set picked = true, handle = ? where pid = ?");
                PreparedStatement pstmt4 = conn.prepareStatement("update DB2024_Students set picked = true where handle = ?")
        ) {
            int pid = request.getPid();
            String handle = request.getHandle();
            System.out.println(pid);
            System.out.println(handle);

            pstmt1.setInt(1, pid);
            pstmt2.setString(1, handle);

            ResultSet rs1 = pstmt1.executeQuery();
            ResultSet rs2 = pstmt2.executeQuery();
            rs1.next();
            rs2.next();
            if (rs1.getBoolean("picked") || rs2.getBoolean("picked")) {
                return "picked rejected";
            }

            pstmt3.setString(1, handle);
            pstmt3.setInt(2, pid);
            pstmt4.setString(1, handle);

            conn.setAutoCommit(false);  // 트랜잭션 시작

            pstmt3.executeUpdate();
            pstmt4.executeUpdate();

            conn.commit();  // 트랜잭션 끝
            conn.setAutoCommit(true);

            return "picked success";
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
}


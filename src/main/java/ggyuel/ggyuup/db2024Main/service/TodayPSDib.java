package ggyuel.ggyuup.db2024Main.service;

import ggyuel.ggyuup.db2024Main.dto.MainRequestDTO;
import ggyuel.ggyuup.db2024Main.dto.MainResponseDTO;
import ggyuel.ggyuup.global.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TodayPSDib {
    public static String putTodayPSpicked(MainRequestDTO.TodayPSDibInfoDTO dibInfo) {
        try {
            Connection conn = DBConnection.getDbPool().getConnection();
            conn.setAutoCommit(false);
            int pid = dibInfo.getPid();
            String handle = dibInfo.getDib_handle();

            System.out.println(pid);
            System.out.println(handle);

            PreparedStatement pstmt = conn.prepareStatement("UPDATE DB2024_TodayPS " +
                    "SET picked = 1, handle = ? " +
                    "WHERE pid = ?");
            pstmt.setString(1, handle);
            pstmt.setInt(2, pid);
            pstmt.executeUpdate();

            System.out.println("찜");

            conn.commit();
        } catch (SQLException e) {
            System.out.println(e);
        }

        return "찜하기 성공";
    }


}

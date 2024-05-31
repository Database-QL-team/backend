package ggyuel.ggyuup.global;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Variable {

    public static int getPSTogetherCount() {
        String sql = "SELECT COUNT(*) FROM DB2024_PSTogether";

        try (Connection conn = DBConnection.getDbPool().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

}

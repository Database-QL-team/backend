package ggyuel.ggyuup.db2024PStogether.service;

import ggyuel.ggyuup.db2024PStogether.dto.PSTogetherRequestDTO;
import ggyuel.ggyuup.global.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PSTogetherDelete {
    public static Boolean PSTogethercheckPW(PSTogetherRequestDTO.PSTogetherDeleteDTO delete) {

        try {
            Connection conn = DBConnection.getDbPool().getConnection();
            System.out.println("DB 연결");

            int togetherid = delete.getTogetherid();
            String pw = delete.getPw();
            Boolean PWcheck = Boolean.FALSE;

            System.out.println(togetherid);
            System.out.println(pw);

            String query = "SELECT pw FROM DB2024_PStogether WHERE togetherid="+togetherid;
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            System.out.println(query);

            if(rs.next()) {
                String realPW = rs.getString("pw");
                System.out.println(realPW);
                if (pw.equals(realPW)) {PWcheck = Boolean.TRUE;}
                else {PWcheck = Boolean.FALSE;}
                System.out.println(PWcheck);
            }

            rs.close();
            pstmt.close();
            conn.close();
            return PWcheck;


        } catch (SQLException e){
            System.out.println(e);
        }
        return null;
    }

    public static String PSTogetherDelete (PSTogetherRequestDTO.PSTogetherDeleteDTO delete) {

        String resultMessage = "삭제되었습니다";

        try {
            Connection conn = DBConnection.getDbPool().getConnection();
            System.out.println("DB 연결");

            int togetherid = delete.getTogetherid();
            System.out.println(togetherid);

            String query = "DELETE FROM DB2024_PStogether WHERE togetherid = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, togetherid);
                int rowsAffected = pstmt.executeUpdate();

                if(rowsAffected == 0) {
                    resultMessage = "삭제할 데이터가 없습니다";
                }
            }

        }catch (SQLException e){
            System.out.println(e);
        }
        return resultMessage;


    }
}

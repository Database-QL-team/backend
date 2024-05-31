package ggyuel.ggyuup.db2024PStogether;

import ggyuel.ggyuup.db2024PStogether.dto.PSTogetherRequestDTO;
import ggyuel.ggyuup.db2024PStogether.dto.PSTogetherResponseDTO;
import ggyuel.ggyuup.global.DBConnection;
import ggyuel.ggyuup.global.Variable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class PSTogetherSave {
    public static PSTogetherResponseDTO.PSTogetherDetailDTO psTogetherSave(PSTogetherRequestDTO.PSTogetherSaveDTO request) {

        try {

            Connection conn = DBConnection.getDbPool().getConnection();
            System.out.println("DB 연결");


            String sql = "INSERT INTO DB2024_PStogether (togetherid, togethertitle, pid, link, handle, pw) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            conn.setAutoCommit(false);


            //PK인 togetherid 업데이트 및 저장
            Variable.setTogetherid();
            int togetherid = Variable.getTogetherid();
            System.out.println("PK 업데이트");
            System.out.println(togetherid);

            // 사용자 입력 받아오기
            String togethertitle = request.getTitle();
            int pid = request.getPid();
            String gitlink = request.getGithub_link();
            String handle = request.getHandle();
            String pw = request.getPw();

            PSTogetherResponseDTO.PSTogetherDetailDTO result = new PSTogetherResponseDTO.PSTogetherDetailDTO(togetherid, pid, togethertitle, handle, gitlink, pw);

            System.out.println(result);

            // DB에 저장
            pstmt.setInt(1, togetherid);
            pstmt.setString(2, togethertitle);
            pstmt.setInt(3, pid);
            pstmt.setString(4, gitlink);
            pstmt.setString(5, handle);
            pstmt.setString(6, pw);
            pstmt.executeUpdate();

            conn.commit();

            System.out.println("저장 성공");

            return result;

        } catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}

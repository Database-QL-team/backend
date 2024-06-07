package ggyuel.ggyuup.db2024PStogether.service;

import ggyuel.ggyuup.db2024PStogether.dto.PSTogetherRequestDTO;
import ggyuel.ggyuup.db2024PStogether.dto.PSTogetherResponseDTO;
import ggyuel.ggyuup.global.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * PSTogetherSave 클래스는 PSTogether 게시물을 저장하는 기능을 제공합니다.
 */
public class PSTogetherSave {

    /**
     * 주어진 요청을 기준으로 게시물을 저장하고, 결과를 PSTogetherResponseDTO.PSTogetherDetailDTO 형태로 반환합니다.
     *
     * @param request PSTogether 게시물 저장을 위한 요청 객체
     * @return PSTogetherResponseDTO.PSTogetherDetailDTO 형태의 저장된 PSTogether 게시물 정보, 저장에 실패하면 null 반환
     */
    public static PSTogetherResponseDTO.PSTogetherDetailDTO psTogetherSave(PSTogetherRequestDTO.PSTogetherSaveDTO request) {

        // 요청이 유효한지 확인하고 유효하지 않으면 null 반환
        if (request == null || request.getTogethertitle() == null || request.getPid() == 0 || request.getLink() == null || request.getHandle() == null || request.getPw() == null) {
            System.out.println("입력 값이 유효하지 않습니다.");
            return null;
        }

        try {
            // DB 연결
            Connection conn = DBConnection.getDbPool().getConnection();
            System.out.println("DB 연결");

            // SQL 쿼리 준비
            String sql = "INSERT INTO DB2024_PStogether (togethertitle, pid, link, handle, pw) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, new String[]{"togetherid"}); // togetherid 반환

            // 사용자 입력 받아오기 - pid, link, handle, pw 추출
            String togethertitle = request.getTogethertitle();
            int pid = request.getPid();
            String link = request.getLink();
            String handle = request.getHandle();
            String pw = request.getPw();

            // 입력 값 로그 기록
            System.out.println("입력 값: " + togethertitle + ", " + pid + ", " + link + ", " + handle + ", " + pw);

            // DB에 저장
            pstmt.setString(1, togethertitle);
            pstmt.setInt(2, pid);
            pstmt.setString(3, link);
            pstmt.setString(4, handle);
            pstmt.setString(5, pw);
            pstmt.executeUpdate();

            // 생성된 togetherid 받아오기
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            int togetherid = 0;
            if (generatedKeys.next()) {
                togetherid = generatedKeys.getInt(1);
            } else {
                throw new SQLException("게시물을 추가하는 동안 키 생성 실패");
            }

            // 리소스 닫기
            pstmt.close();
            conn.close();

            // PSTogetherDetailDTO에 togetherid 포함하여 반환
            return new PSTogetherResponseDTO.PSTogetherDetailDTO(togetherid, pid, togethertitle, handle, link, pw);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}

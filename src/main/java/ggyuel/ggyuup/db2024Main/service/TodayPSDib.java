package ggyuel.ggyuup.db2024Main.service;

import ggyuel.ggyuup.db2024Main.dto.MainRequestDTO;
import ggyuel.ggyuup.global.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * TodayPSDib 클래스는 오늘의 문제 찜하기 기능의 서비스 로직을 처리합니다.
 */
public class TodayPSDib {

    /**
     * 사용자가 오늘의 문제를 찜하기 했을 때 처리하는 메서드입니다.
     *
     * @param request TodayPSDibInfoDTO 객체로, 선택된 문제의 번호와 사용자의 핸들을 포함합니다.
     * @return 선택 처리 결과를 나타내는 문자열 ("picked success" 또는 "picked rejected")
     */
    public static String putTodayPSpicked(MainRequestDTO.TodayPSDibInfoDTO request) {
        try (
                // 데이터베이스 연결 및 PreparedStatement 생성
                Connection conn = DBConnection.getDbPool().getConnection();
                PreparedStatement pstmt1 = conn.prepareStatement("SELECT picked FROM DB2024_TodayPS WHERE pid = ?");
                PreparedStatement pstmt2 = conn.prepareStatement("SELECT picked FROM DB2024_Students WHERE handle = ?");
                PreparedStatement pstmt3 = conn.prepareStatement("UPDATE DB2024_TodayPS SET picked = true, handle = ? WHERE pid = ?");
                PreparedStatement pstmt4 = conn.prepareStatement("UPDATE DB2024_Students SET picked = true WHERE handle = ?")
        ) {
            // request에서 문제 번호와 사용자 핸들 추출
            int pid = request.getPid();
            String handle = request.getHandle();
            System.out.println(pid);
            System.out.println(handle);

            // 문제와 사용자에 대한 picked 칼럼 조회
            pstmt1.setInt(1, pid);
            pstmt2.setString(1, handle);

            ResultSet rs1 = pstmt1.executeQuery();
            ResultSet rs2 = pstmt2.executeQuery();
            rs1.next();
            rs2.next();

            // 이미 선택된 경우 "picked rejected" 반환
            if (rs1.getBoolean("picked") || rs2.getBoolean("picked")) {
                return "picked rejected";
            }

            // 선택 상태 업데이트
            pstmt3.setString(1, handle);
            pstmt3.setInt(2, pid);
            pstmt4.setString(1, handle);

            conn.setAutoCommit(false);  // 트랜잭션 시작

            // 데이터베이스 업데이트 실행
            pstmt3.executeUpdate();
            pstmt4.executeUpdate();

            conn.commit();  // 트랜잭션 끝
            conn.setAutoCommit(true);

            return "picked success";  // 성공 메시지 반환
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;  // 실패 시 null 반환
    }
}

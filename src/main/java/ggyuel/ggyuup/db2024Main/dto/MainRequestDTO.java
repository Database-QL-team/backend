package ggyuel.ggyuup.db2024Main.dto;

/**
 * 메인페이지와 관련된 요청을 처리하기 위한 데이터 전송 객체(DTO)를 나타냅니다.
 */
public class MainRequestDTO {

    /**
     * 오늘의 문제 찜하기 정보를 위한 DTO를 나타내는 중첩된 정적 클래스입니다.
     */
    public static class TodayPSDibInfoDTO {
        int pid;        // 오늘의 문제 문제 번호.
        String handle;  // 찜하기 사용자 핸들

        /**
         * 오늘의 문제 문제번호를 가져옵니다.
         * @return pid
         */
        public int getPid() {
            return pid;
        }

        /**
         * 찜하기 사용자 핸들을 가져옵니다.
         * @return handle
         */
        public String getHandle() {
            return handle;
        }

        /**
         * 오늘의 문제 문제번호를 설정합니다.
         * @param pid 설정할 pid
         */
        public void setPid(int pid) {
            this.pid = pid;
        }

        /**
         * 찜하기 사용자 핸들을 설정합니다.
         * @param handle 설정할 handle
         */
        public void setHandle(String handle) {
            this.handle = handle;
        }
    }
}

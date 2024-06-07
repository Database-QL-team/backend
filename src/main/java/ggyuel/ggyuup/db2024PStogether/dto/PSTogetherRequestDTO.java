package ggyuel.ggyuup.db2024PStogether.dto;

/**
 * PSTogether 관련 요청 데이터를 담는 DTO 클래스입니다.
 */
public class PSTogetherRequestDTO {

    /**
     * PSTogether 저장 요청 데이터를 담는 내부 클래스입니다.
     */
    public static class PSTogetherSaveDTO {

        private String togethertitle; // 게시글 제목
        private String handle; // 게시자 핸들
        private String link; // 깃헙 링크
        private String pw; // 게시물 비밀번호
        private int pid; // 문제 번호

        /**
         * @return 저장하려는 PSTogether의 제목
         */
        public String getTogethertitle() {
            return togethertitle;
        }

        /**
         * @return 저장하려는 PSTogether의 핸들
         */
        public String getHandle() {
            return handle;
        }

        /**
         * @return 저장하려는 PSTogether의 링크
         */
        public String getLink() {
            return link;
        }

        /**
         * @return 저장하려는 PSTogether의 비밀번호
         */
        public String getPw() {
            return pw;
        }

        /**
         * @return 저장하려는 PSTogether의 문제 ID
         */
        public int getPid() {
            return pid;
        }
    }

    /**
     * PSTogether 삭제 요청 데이터를 담는 내부 클래스입니다.
     */
    public static class PSTogetherDeleteDTO {
        private int togetherid; // 게시글 Id
        private String pw; // 게시글 비밀번호

        /**
         * @return 삭제하려는 PSTogether의 ID
         */
        public int getTogetherid() {
            return togetherid;
        }

        /**
         * @return 삭제하려는 PSTogether의 비밀번호
         */
        public String getPw() {
            return pw;
        }
    }
}

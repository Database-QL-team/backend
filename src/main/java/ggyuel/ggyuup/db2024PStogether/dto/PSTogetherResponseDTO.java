package ggyuel.ggyuup.db2024PStogether.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * PSTogether 관련 응답 데이터를 담는 DTO 클래스입니다.
 */
public class PSTogetherResponseDTO {

    /**
     * PSTogether의 세부 정보를 담는 내부 클래스입니다.
     */
    public static class PSTogetherDetailDTO {

        @JsonProperty
        private int togetherid;

        @JsonProperty
        private int pid;

        @JsonProperty
        private String togethertitle;

        @JsonProperty
        private String handle;

        @JsonProperty
        private String link;

        @JsonProperty
        private String pw;

        /**
         * PSTogetherDetailDTO 생성자입니다.
         *
         * @param togetherid 게시글의 ID
         * @param pid 문제 번호
         * @param togethertitle 게시글의 제목
         * @param handle 게시자의 핸들
         * @param link 깃헙 링크
         * @param pw 게시글의 비밀번호
         */
        public PSTogetherDetailDTO(@JsonProperty("togetherid") int togetherid,
                                   @JsonProperty("pid") int pid,
                                   @JsonProperty("togethertitle") String togethertitle,
                                   @JsonProperty("handle") String handle,
                                   @JsonProperty("link") String link,
                                   @JsonProperty("pw") String pw) {
            this.togetherid = togetherid;
            this.pid = pid;
            this.togethertitle = togethertitle;
            this.handle = handle;
            this.link = link;
            this.pw = pw;
        }
    }

    /**
     * PSTogether의 미리보기를 담는 내부 클래스입니다.
     */
    public static class PSTogetherPreviewDTO {

        @JsonProperty
        private int togetherid;

        @JsonProperty
        private int pid;

        @JsonProperty
        private String togethertitle;

        @JsonProperty
        private String handle;

        /**
         * PSTogetherPreviewDTO 생성자입니다.
         *
         * @param togetherid 게시글의 ID
         * @param pid 문제 번호
         * @param togethertitle 게시글의 제목
         * @param handle 게시자의 핸들
         */
        public PSTogetherPreviewDTO(@JsonProperty("togetherid") int togetherid,
                                    @JsonProperty("pid") int pid,
                                    @JsonProperty("togethertitle") String togethertitle,
                                    @JsonProperty("handle") String handle) {
            this.togetherid = togetherid;
            this.pid = pid;
            this.togethertitle = togethertitle;
            this.handle = handle;
        }
    }
}

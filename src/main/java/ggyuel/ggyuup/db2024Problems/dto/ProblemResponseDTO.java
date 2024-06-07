package ggyuel.ggyuup.db2024Problems.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * API에서 반환하는 문제에 대한 DTO 클래스입니다.
 */
public class ProblemResponseDTO {

    /**
     * 알고리즘 문제에 대한 DTO 클래스입니다.
     */
    public static class ProblemAlgoDTO {
        @JsonProperty
        int pid; // 문제 번호
        @JsonProperty
        String p_title; // 문제 제목
        @JsonProperty
        String link; // 문제 링크
        @JsonProperty
        int solvednum; // 해결한 사람 수(이화여대 제외)
        @JsonProperty
        int tier; // 티어

        /**
         * ProblemAlgoDTO의 생성자입니다.
         *
         * @param pid       문제 번호
         * @param p_title   문제 제목
         * @param link      문제 링크
         * @param solvednum 해결한 사람 수(이화여대 제외)
         * @param tier      티어
         */
        public ProblemAlgoDTO(@JsonProperty("pid") int pid,
                              @JsonProperty("p_title") String p_title,
                              @JsonProperty("link") String link,
                              @JsonProperty("solvednum") int solvednum,
                              @JsonProperty("tier") int tier) {
            this.pid = pid;
            this.p_title = p_title;
            this.link = link;
            this.solvednum = solvednum;
            this.tier = tier;
        }
    }

    /**
     * 티어별 문제에 대한 DTO 클래스입니다.
     */
    public static class ProblemTierDTO {
        @JsonProperty
        int pid; // 문제 ID
        @JsonProperty
        String p_title; // 문제 제목
        @JsonProperty
        String link; // 문제 링크
        @JsonProperty
        int solvednum; // 해결된 횟수

        /**
         * ProblemTierDTO의 생성자입니다.
         *
         * @param pid       문제 ID
         * @param p_title   문제 제목
         * @param link      문제 링크
         * @param solvednum 해결된 횟수
         */
        public ProblemTierDTO(@JsonProperty("pid") int pid,
                              @JsonProperty("p_title") String p_title,
                              @JsonProperty("link") String link,
                              @JsonProperty("solvednum") int solvednum) {
            this.pid = pid;
            this.p_title = p_title;
            this.link = link;
            this.solvednum = solvednum;
        }
    }
}
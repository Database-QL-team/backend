package ggyuel.ggyuup.Problems.dto;

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
        String title; // 문제 제목
        @JsonProperty
        String link; // 문제 링크
        @JsonProperty
        int tier;
        @JsonProperty
        int solvedNum; // 해결한 사람 수(이화여대 제외)


        /**
         * ProblemAlgoDTO의 생성자입니다.
         *
         * @param title         문제 제목
         * @param link            문제 링크
         * @param tier            티어
         * @param solvedNum       해결한 사람 수(이화여대 제외)
         */
        public ProblemAlgoDTO(@JsonProperty("title") String title,
                              @JsonProperty("link") String link,
                              @JsonProperty("tier") int tier,
                              @JsonProperty("solvedNum") int solvedNum)
        {
            this.title = title;
            this.link = link;
            this.tier = tier;
            this.solvedNum = solvedNum;

        }
    }

    /**
     * 티어별 문제에 대한 DTO 클래스입니다.
     */
    public static class ProblemTierDTO {
        @JsonProperty
        String title; // 문제 제목
        @JsonProperty
        String link; // 문제 링크
        @JsonProperty
        int tier;
        @JsonProperty
        int solvedNum; // 해결한 사람 수(이화여대 제외)


        /**
         * ProblemTierDTO의 생성자입니다.
         * @param title         문제 제목
         * @param link            문제 링크
         * @param tier            티어
         * @param solvedNum       해결한 사람 수(이화여대 제외)
         */
        public ProblemTierDTO(@JsonProperty("title") String title,
                              @JsonProperty("link") String link,
                              @JsonProperty("tier") int tier,
                              @JsonProperty("solvedNum") int solvedNum)
        {
            this.title = title;
            this.link = link;
            this.tier = tier;
            this.solvedNum = solvedNum;

        }
    }
}
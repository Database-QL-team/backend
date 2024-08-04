package ggyuel.ggyuup.Problems.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ggyuel.ggyuup.Algorithms.domain.Algo;

/**
 * API에서 반환하는 문제에 대한 DTO 클래스입니다.
 */
public class ProblemRequestDTO {

    /**
     * 알고리즘 문제에 대한 DTO 클래스입니다.
     */
    public static class ProblemAlgoDTO {
        @JsonProperty
        int problemId; // 문제 번호
        @JsonProperty
        String title; // 문제 제목
        @JsonProperty
        String link; // 문제 링크
        @JsonProperty
        int solvedPeople; // 해결한 사람 수(이화여대 제외)
        @JsonProperty
        int tier; // 티어

//        @JsonProperty
//        Boolean todayPs;

        /**
         * ProblemAlgoDTO의 생성자입니다.
         *
         * @param problemId       문제 번호
         * @param title         문제 제목
         * @param link            문제 링크
         * @param solvedNum       해결한 사람 수(이화여대 제외)
         * @param tier            티어
         * @param todayPs         오늘의 문제 여부
         */
        public ProblemAlgoDTO(@JsonProperty("problemId") int problemId,
                              @JsonProperty("title") String title,
                              @JsonProperty("link") String link,
                              @JsonProperty("solvedPeople") int solvedNum,
                              @JsonProperty("tier") int tier) {
                              //@JsonProperty("todayPs") Boolean todayPs) {
            this.problemId = problemId;
            this.title = title;
            this.link = link;
            this.solvedPeople = solvedPeople;
            this.tier = tier;
            // this.todayPs = todayPs;
        }
    }

    /**
     * 티어별 문제에 대한 DTO 클래스입니다.
     */
    public static class ProblemTierDTO {
        @JsonProperty
        int problemId; // 문제 ID
        @JsonProperty
        String title; // 문제 제목
        @JsonProperty
        String link; // 문제 링크
        @JsonProperty
        int solvedPeople; // 해결된 횟수

//        @JsonProperty
//        Boolean todayPs;

        /**
         * ProblemTierDTO의 생성자입니다.
         *
         * @param problemId       문제 ID
         * @param title           문제 제목
         * @param link            문제 링크
         * @param solvedPeople    해결된 횟수
         * @param todayPs         오늘의 문제 여부
         */
        public ProblemTierDTO(@JsonProperty("problemId") int problemId,
                              @JsonProperty("title") String title,
                              @JsonProperty("link") String link,
                              @JsonProperty("solvedPeople") int solvedNum) {
                              //@JsonProperty("todayPs") Boolean todayPs) {
            this.problemId = problemId;
            this.title = title;
            this.link = link;
            this.solvedPeople = solvedPeople;
//        @JsonProperty
//        Boolean todayPs;
        }
    }
}
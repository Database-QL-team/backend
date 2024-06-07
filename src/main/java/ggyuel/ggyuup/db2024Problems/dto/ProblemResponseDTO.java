package ggyuel.ggyuup.db2024Problems.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProblemResponseDTO {
    public static class ProblemAlgoDTO {
        @JsonProperty
        int pid;
        @JsonProperty
        String p_title;
        @JsonProperty
        String link;
        @JsonProperty
        int solvednum;
        @JsonProperty
        int tier;

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

    public static class ProblemTierDTO {
        @JsonProperty
        int pid;
        @JsonProperty
        String p_title;
        @JsonProperty
        String link;
        @JsonProperty
        int solvednum;

        public ProblemTierDTO (@JsonProperty("pid") int pid,
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

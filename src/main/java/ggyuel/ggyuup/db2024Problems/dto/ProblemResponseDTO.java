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
        String tier;

        public ProblemAlgoDTO(@JsonProperty("pid") int pid,
                                   @JsonProperty("p_title") String p_title,
                                   @JsonProperty("link") String link,
                                   @JsonProperty("solvednum") int solvednum,
                                   @JsonProperty("tier") String tier) {
            this.pid = pid;
            this.p_title = p_title;
            this.link = link;
            this.solvednum = solvednum;
            this.tier = tier;
        }
    }

    public static class ProblemTierDTO {
        int pid;
        String p_title;
        String link;
        int solvednum;
        String algo;

        public ProblemTierDTO (int pid, String p_title, String link, int solvednum, String algo){
            this.pid = pid;
            this.p_title = p_title;
            this.link = link;
            this.solvednum = solvednum;
            this.algo = algo;
        }
    }
}

package ggyuel.ggyuup.db2024Problems.dto;

public class ProblemResponseDTO {
    public static class ProblemAlgoDTO {
        int pid;
        String p_title;
        String link;
        int solvednum;
        String tier;

        public ProblemAlgoDTO(int pid, String p_title, String link, int solvednum, String tier){
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

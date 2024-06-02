package ggyuel.ggyuup.db2024PStogether.dto;


public class PSTogetherRequestDTO {
    public static class PSTogetherSaveDTO {

        String title;
        String handle;
        String github_link;
        String pw;
        int pid;

        public String getHandle() {return handle;}
        public String getGithub_link() {return github_link;}
        public String getPw() {return pw;}
        public String getTitle() {return title;}

        public int getPid() {return pid;}

    }
    public static class PSTogetherDeleteDTO {
        int togetherid;
        String pw;

        public int getTogetherid() {return togetherid;}
        public String getPw() {return pw;}
    }

}

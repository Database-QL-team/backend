package ggyuel.ggyuup.db2024PStogether.dto;


public class PSTogetherRequestDTO {
    public static class PSTogetherSaveDTO {

        String togethertitle;
        String handle;
        String link;
        String pw;
        int pid;

        public String getHandle() {return handle;}
        public String getLink() {return link;}
        public String getPw() {return pw;}
        public String getTogethertitle() {return togethertitle;}

        public int getPid() {return pid;}

    }
    public static class PSTogetherDeleteDTO {
        int togetherid;
        String pw;

        public int getTogetherid() {return togetherid;}
        public String getPw() {return pw;}
    }

}

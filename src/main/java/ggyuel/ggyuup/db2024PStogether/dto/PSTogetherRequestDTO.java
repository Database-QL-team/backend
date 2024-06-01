package ggyuel.ggyuup.db2024PStogether.dto;

import java.util.ArrayList;

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

    public static class PSTogetherId {
        int togetherid;

        public int getTogetherid() {
            return togetherid;
        }
    }
}

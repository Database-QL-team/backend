package ggyuel.ggyuup.db2024Main.dto;

public class MainRequestDTO {
    public static class TodayPSDibInfoDTO {
        int pid;
        String handle;

        public int getPid() {return pid;}
        public String getHandle() {
            return handle;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }
        public void setHandle(String handle) {
            this.handle = handle;
        }

    }
}

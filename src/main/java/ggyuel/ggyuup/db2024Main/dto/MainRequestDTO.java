package ggyuel.ggyuup.db2024Main.dto;

public class MainRequestDTO {
    public static class TodayPSDibInfoDTO {
        int pid;
        String dib_handle;

        public int getPid() {return pid;}
        public String getDib_handle() {
            return dib_handle;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }
        public void setDib_handle(String dib_handle) {
            this.dib_handle = dib_handle;
        }

    }
}

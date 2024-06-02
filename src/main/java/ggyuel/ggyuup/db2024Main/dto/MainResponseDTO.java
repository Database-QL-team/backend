package ggyuel.ggyuup.db2024Main.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class MainResponseDTO {

    public static class GroupInfoDTO {

        @JsonProperty
        int ewha_ranking;
        @JsonProperty
        int rival_ranking;
        @JsonProperty
        String rival_group_name;
        @JsonProperty
        int solved_num_gap;

        public GroupInfoDTO (@JsonProperty("ewha_ranking") int ewha_ranking,
                             @JsonProperty("rival_ranking") int rival_ranking,
                             @JsonProperty("rival_group_name") String rival_group_name,
                             @JsonProperty("solved_num_gap") int solved_num_gap) {
            this.ewha_ranking = ewha_ranking;
            this.rival_ranking = rival_ranking;
            this.rival_group_name = rival_group_name;
            this.solved_num_gap =solved_num_gap;
        }
    }

    public static class TodayPSDTO {

        @JsonProperty
        int pid;
        @JsonProperty
        String dib_handle;
        @JsonProperty
        int dib;
        @JsonProperty
        String p_title;
        @JsonProperty
        int p_tier;

        public TodayPSDTO(@JsonProperty("pid") int pid,
                          @JsonProperty("p_title") String p_title,
                          @JsonProperty("p_tier") int p_tier,
                          @JsonProperty("dib") int dib,
                          @JsonProperty("dib_handle") String dib_handle
                          ) {
            this.pid = pid;
            this.dib_handle = dib_handle;
            this.dib = dib;
            this.p_title = p_title;
            this.p_tier = p_tier;

        }
    }

    public static class MainPageDTO {
        private GroupInfoDTO groupInfo;
        private ArrayList<TodayPSDTO> todayPSList;

        public GroupInfoDTO getGroupInfo() {
            return groupInfo;
        }

        public void setGroupInfo(GroupInfoDTO groupInfo) {
            this.groupInfo = groupInfo;
        }

        public ArrayList<TodayPSDTO> getTodayPSList() {
            return todayPSList;
        }

        public void setTodayPSList(ArrayList<TodayPSDTO> todayPSList) {
            this.todayPSList = todayPSList;
        }

    }
}

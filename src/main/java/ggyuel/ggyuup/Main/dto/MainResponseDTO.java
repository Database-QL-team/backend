package ggyuel.ggyuup.Main.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * MainResponseDTO 클래스는 메인 페이지 응답 데이터를 표현합니다.
 */
public class MainResponseDTO {

    /**
     * GroupInfoDTO 클래스는 그룹의 랭킹 정보를 나타냅니다.
     * 그룹의 랭킹 정보란 이화여대의 백준 랭킹
     * 이화여대 전 순위 그룹의 이름, 전 순위 그룹과의 푼 문제 수 차이를 의미합니다.
     */
    public static class GroupInfoDTO {
        @JsonProperty
        int ewhaRank; // 이화여대 백준 랭킹
        @JsonProperty
        int rivalRank; // 전 순위 그룹의 백준 랭킹
        @JsonProperty
        String rivalName; // 전 순위 그룹의 이름
        @JsonProperty
        int solvedNumGap; // 전 순위 그룹과 푼 문제 수 차이

        /**
         * GroupInfoDTO 생성자.
         *
         * @param ewhaRank       이화 랭킹
         * @param rivalRank      전 순위 그룹의 백준 랭킹
         * @param rivalName   전 순위 그룹의 이름
         * @param solvedNumGap     전 순위 그룹과 푼 문제 수 차이
         */
        public GroupInfoDTO (@JsonProperty("ewhaRank") int ewhaRank,
                             @JsonProperty("rivalRank") int rivalRank,
                             @JsonProperty("rivalName") String rivalName,
                             @JsonProperty("solvedNumGap") int solvedNumGap) {
            this.ewhaRank = ewhaRank;
            this.rivalRank = rivalRank;
            this.rivalName = rivalName;
            this.solvedNumGap = solvedNumGap;
        }
    }

    /**
     * TodayPSDTO 클래스는 오늘의 문제 정보를 나타냅니다.
     */
//    public static class TodayPSDTO {
//
//        @JsonProperty
//        int pid; // 문제 번호
//        @JsonProperty
//        String dib_handle; // 찜하기 한 사용자의 핸들
//        @JsonProperty
//        int dib; // 찜하기 여부
//        @JsonProperty
//        String p_title; // 문제 제목
//        @JsonProperty
//        int p_tier; // 문제 티어
//
//        /**
//         * TodayPSDTO 생성자.
//         *
//         * @param pid        문제 번호
//         * @param p_title    문제 제목
//         * @param p_tier     문제 티어
//         * @param dib        찜하기 여부
//         * @param dib_handle 찜하기 한 사용자의 핸들
//         */
//        public TodayPSDTO(@JsonProperty("pid") int pid,
//                          @JsonProperty("p_title") String p_title,
//                          @JsonProperty("p_tier") int p_tier,
//                          @JsonProperty("dib") int dib,
//                          @JsonProperty("dib_handle") String dib_handle) {
//            this.pid = pid;
//            this.dib_handle = dib_handle;
//            this.dib = dib;
//            this.p_title = p_title;
//            this.p_tier = p_tier;
//        }
//    }

    /**
     * MainPageDTO 클래스는 메인 페이지의 정보를 포함합니다.
     * 메인페이지는 그룹의 랭킹 정보와 오늘의 문제 정보를 포함합니다.
     */
    public static class MainPageDTO {
        private GroupInfoDTO groupInfo; // 그룹 정보
        //private ArrayList<TodayPSDTO> todayPSList; // 오늘의 문제 리스트

        /**
         * 그룹 정보를 반환합니다.
         *
         * @return 그룹 정보
         */
        public GroupInfoDTO getGroupInfo() {
            return groupInfo;
        }

        /**
         * 그룹 정보를 설정합니다.
         *
         * @param groupInfo 그룹 정보
         */
        public void setGroupInfo(GroupInfoDTO groupInfo) {
            this.groupInfo = groupInfo;
        }

        /**
         * 오늘의 문제 리스트를 반환합니다.
         *
         * @return 오늘의 문제 리스트
         */
        //public ArrayList<TodayPSDTO> getTodayPSList() {
//            return todayPSList;
//        }

        /**
         * 오늘의 문제 리스트를 설정합니다.
         *
         * @param todayPSList 오늘의 문제 리스트
         */
        //public void setTodayPSList(ArrayList<TodayPSDTO> todayPSList) {
//            this.todayPSList = todayPSList;
//        }
    }
}


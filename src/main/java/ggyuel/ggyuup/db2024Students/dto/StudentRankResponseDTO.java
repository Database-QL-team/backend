package ggyuel.ggyuup.db2024Students.dto;

import lombok.Getter;

/**
 * StudentRankResponseDTO 클래스는 학생 순위 관련 응답 데이터 전송 객체를 정의합니다.
 */
public class StudentRankResponseDTO {

    /**
     * 학생 순위 정보를 나타내는 DTO 클래스
     */
    @Getter
    public static class StudentRankDTO {
        int rank_ingroup; // 그룹 내 순위
        String handle; // 핸들
        String userlink; // 사용자 링크
        String tier; // 티어
        int solved_num; // 해결한 문제 수

        /**
         * StudentRankDTO 생성자
         *
         * @param rank_ingroup 학생의 그룹 내 순위
         * @param handle 학생의 핸들
         * @param userlink 학생의 사용자 링크
         * @param tier 학생의 티어
         * @param solved_num 학생이 해결한 문제 수
         */
        public StudentRankDTO(int rank_ingroup, String handle, String userlink, String tier, int solved_num) {
            this.rank_ingroup = rank_ingroup;
            this.handle = handle;
            this.userlink = userlink;
            this.tier = tier;
            this.solved_num = solved_num;
        }
    }
}


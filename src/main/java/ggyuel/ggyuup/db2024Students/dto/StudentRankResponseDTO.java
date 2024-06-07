package ggyuel.ggyuup.db2024Students.dto;

import lombok.Getter;

public class StudentRankResponseDTO {

    @Getter
    public static class StudentRankDTO{
        int rank_ingroup;
        String handle;
        String userlink;
        String tier;
        int solved_num;
        public StudentRankDTO(int rank_ingroup,String handle,String userlink,String tier,int solved_num){
            this.rank_ingroup = rank_ingroup;
            this.handle = handle;
            this.userlink = userlink;
            this.tier = tier;
            this.solved_num = solved_num;
        }
    }

}

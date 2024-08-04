package ggyuel.ggyuup.Students.domain;

import ggyuel.ggyuup.Problems.domain.Tier;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "students")
public class Students {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stu_id")
    private int stuId;

    @Column(nullable = false)
    private String handle;

    @Column(nullable = false)
    private String link;

    @Column(name = "solved_num", nullable = false)
    private int solvedNum;

    @Column(name = "ewha_rank", nullable = false)
    private int ewhaRank;

    @Column(nullable = false)
    private Tier tier;
}

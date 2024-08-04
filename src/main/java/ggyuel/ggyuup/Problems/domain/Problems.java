package ggyuel.ggyuup.Problems.domain;

import ggyuel.ggyuup.Algorithms.domain.Algorithms;
import ggyuel.ggyuup.ProAlgo.domain.ProAlgo;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "problems")
public class Problems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_id")
    private Long problemId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String link;

    @Column(name = "solved_people", nullable = false)
    private Long solvedPeople;

    @Builder.Default
    @Column(name = "today_ps", nullable = false)
    private Boolean todayPs = false;

    @Column(nullable = false)
    private Tier tier;

    @OneToMany(mappedBy = "problems")
    private List<ProAlgo> proAlgos = new ArrayList<>();
}

package ggyuel.ggyuup.ProAlgo.domain;

import ggyuel.ggyuup.Algorithms.domain.Algorithms;
import ggyuel.ggyuup.Problems.domain.Problems;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "proalgo")
public class ProAlgo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pro_algo_id")
    private Long proAlgoId;


    @ManyToOne
    @JoinColumn(name = "problem_id")
    private Problems problems;

    @ManyToOne
    @JoinColumn(name = "algo_id")
    private Algorithms algorithms;
}

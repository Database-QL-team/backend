package ggyuel.ggyuup.Algorithms.domain;
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
@Table(name = "algorithms")
public class Algorithms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "algo_id")
    private Long algoId;

    @Column(nullable = false)
    private Algo algo;

    @OneToMany(mappedBy = "algorithms")
    private List<ProAlgo> proAlgos = new ArrayList<>();
}

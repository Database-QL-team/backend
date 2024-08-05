package ggyuel.ggyuup.Organizations.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "organizations")
public class Organizations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "org_id")
    private Long orgId;

    @Column(nullable = false)
    private String name;

    @Column(name = "org_rank", nullable = false)
    private int orgRank;

    @Column(name = "solved_num", nullable = false)
    private Long solvedNum;
}

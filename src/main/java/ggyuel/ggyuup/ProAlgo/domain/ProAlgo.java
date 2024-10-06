package ggyuel.ggyuup.ProAlgo.domain;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProAlgo {
    private int proAlgoId;
    private String algoId;
    private int problemId;
}

package ggyuel.ggyuup.Algorithms.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Algo {
    DATA_STRUCTURES("자료구조"),
    GRAPH("그래프"),
    DP("동적계획법"),
    GEOMETRY("기하학"),
    MATH("수학"),
    IMPLEMENTATION("구현"),
    GREEDY("그리디"),
    STRING("문자열");

    private final String description;
}

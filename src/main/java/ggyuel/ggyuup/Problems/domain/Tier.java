package ggyuel.ggyuup.Problems.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Tier {
    RUBY5, RUBY4, RUBY3, RUBY2, RUBY1,
    DIAMOND5, DIAMOND4, DIAMOND3, DIAMOND2, DIAMOND1,
    PLATINUM5, PLATINUM4, PLATINUM3, PLATINUM2, PLATINUM1,
    GOLD5, GOLD4, GOLD3, GOLD2, GOLD1,
    SILVER5, SILVER4, SILVER3, SILVER2, SILVER1,
    BRONZE5, BRONZE4, BRONZE3, BRONZE2, BRONZE1,
    UNRATED;
}

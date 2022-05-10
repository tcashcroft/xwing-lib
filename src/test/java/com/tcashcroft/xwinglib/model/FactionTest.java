package com.tcashcroft.xwinglib.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FactionTest {

  private static Stream<Arguments> factionParsingArgs() {
    return Stream.of(
        Arguments.of(Faction.Type.FIRST_ORDER, Arrays.asList("First Order", "first-order", "firstorder")),
        Arguments.of(Faction.Type.GALACTIC_EMPIRE, Arrays.asList("Galactic Empire", "galactic-empire", "galacticempire")),
        Arguments.of(Faction.Type.GALACTIC_REPUBLIC, Arrays.asList("Galactic Republic", "galactic-republic", "galacticrepublic")),
        Arguments.of(Faction.Type.REBEL_ALLIANCE, Arrays.asList("Rebel Alliance", "rebel-alliance", "rebelalliance")),
        Arguments.of(Faction.Type.RESISTANCE, Arrays.asList("Resistance", "resistance")),
        Arguments.of(Faction.Type.CIS, Arrays.asList("Separatist Alliance", "separatist-alliance", "separatistalliance"))
    );
  }

  @ParameterizedTest
  @MethodSource("factionParsingArgs")
  public void testFactionParsing(Faction.Type expectedFaction, List<String> parsableStrings) {
    for (String s : parsableStrings) {
      Faction.Type actualFaction = Faction.Type.parse(s);
      assertEquals(expectedFaction, actualFaction);
    }
  }
}

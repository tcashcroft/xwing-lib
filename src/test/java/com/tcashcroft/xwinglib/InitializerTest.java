package com.tcashcroft.xwinglib;

import com.tcashcroft.xwinglib.model.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.net.URI;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class InitializerTest {

  private static Initializer initializedInitializer;
  private static Initializer uninitInitializer;

  private static final URI repoURI = URI.create("https://github.com/guidokessels/xwing-data2.git");

  @BeforeAll
  public static void beforeAll() throws Exception {
    initializedInitializer = new Initializer(repoURI);
    uninitInitializer = new Initializer(repoURI);
    initializedInitializer.init();
  }

  private static Stream<Arguments> factionShipCountData() {
    return Stream.of(
        Arguments.of("first-order", 10),
        Arguments.of("galactic-empire", 18),
        Arguments.of("galactic-republic", 13),
        Arguments.of("rebel-alliance", 21),
        Arguments.of("resistance", 9),
        Arguments.of("scum-and-villainy", 24),
        Arguments.of("separatist-alliance", 12)
    );
  }

  @ParameterizedTest
  @MethodSource("factionShipCountData")
  public void testProcessFactionPilots(String factionSubpath, int expectedShipCount) throws Exception {
    List<Ship> ships = uninitInitializer.processFactionPilots(Paths.get(System.getProperty("java.io.tmpdir"), "xwing-data2", "data", "pilots", factionSubpath));
    assertEquals(expectedShipCount, ships.size());
  }

  private static Stream<Arguments> dialAndManeuverParsingData() {
    List<Maneuver> tieFighter = List.of(
        new Maneuver("1TW", Maneuver.Speed.ONE, Maneuver.Direction.LEFT, Utils.Difficulty.WHITE, Maneuver.Type.TURN, false),
        new Maneuver("1YW", Maneuver.Speed.ONE, Maneuver.Direction.RIGHT, Utils.Difficulty.WHITE, Maneuver.Type.TURN, false),
        new Maneuver("2TW", Maneuver.Speed.TWO, Maneuver.Direction.LEFT, Utils.Difficulty.WHITE, Maneuver.Type.TURN, false),
        new Maneuver("2BB", Maneuver.Speed.TWO, Maneuver.Direction.LEFT, Utils.Difficulty.BLUE, Maneuver.Type.BANK, false),
        new Maneuver("2FB", Maneuver.Speed.TWO, Maneuver.Direction.STRAIGHT, Utils.Difficulty.BLUE, Maneuver.Type.STRAIGHT, false),
        new Maneuver("2NB", Maneuver.Speed.TWO, Maneuver.Direction.RIGHT, Utils.Difficulty.BLUE, Maneuver.Type.BANK, false),
        new Maneuver("2YW", Maneuver.Speed.TWO, Maneuver.Direction.RIGHT, Utils.Difficulty.WHITE, Maneuver.Type.TURN, false),
        new Maneuver("3TW", Maneuver.Speed.THREE, Maneuver.Direction.LEFT, Utils.Difficulty.WHITE, Maneuver.Type.TURN, false),
        new Maneuver("3BW", Maneuver.Speed.THREE, Maneuver.Direction.LEFT, Utils.Difficulty.WHITE, Maneuver.Type.BANK, false),
        new Maneuver("3FB", Maneuver.Speed.THREE, Maneuver.Direction.STRAIGHT, Utils.Difficulty.BLUE, Maneuver.Type.STRAIGHT, false),
        new Maneuver("3NW", Maneuver.Speed.THREE, Maneuver.Direction.RIGHT, Utils.Difficulty.WHITE, Maneuver.Type.BANK, false),
        new Maneuver("3YW", Maneuver.Speed.THREE, Maneuver.Direction.RIGHT, Utils.Difficulty.WHITE, Maneuver.Type.TURN, false),
        new Maneuver("3KR", Maneuver.Speed.THREE, Maneuver.Direction.STRAIGHT, Utils.Difficulty.RED, Maneuver.Type.KOIOGRAN_TURN, true),
        new Maneuver("4FW", Maneuver.Speed.FOUR, Maneuver.Direction.STRAIGHT, Utils.Difficulty.WHITE, Maneuver.Type.STRAIGHT, false),
        new Maneuver("4KR", Maneuver.Speed.FOUR, Maneuver.Direction.STRAIGHT, Utils.Difficulty.RED, Maneuver.Type.KOIOGRAN_TURN, true),
        new Maneuver("5FW", Maneuver.Speed.FIVE, Maneuver.Direction.STRAIGHT, Utils.Difficulty.WHITE, Maneuver.Type.STRAIGHT, false)
    );

    List<Maneuver> laat = List.of(
        new Maneuver("0OR", Maneuver.Speed.ZERO, Maneuver.Direction.STRAIGHT, Utils.Difficulty.RED, Maneuver.Type.STOP, true),
        new Maneuver("1BW", Maneuver.Speed.ONE, Maneuver.Direction.LEFT, Utils.Difficulty.WHITE, Maneuver.Type.BANK, false),
        new Maneuver("1FB", Maneuver.Speed.ONE, Maneuver.Direction.STRAIGHT, Utils.Difficulty.BLUE, Maneuver.Type.STRAIGHT, false),
        new Maneuver("1NW", Maneuver.Speed.ONE, Maneuver.Direction.RIGHT, Utils.Difficulty.WHITE, Maneuver.Type.BANK, false),
        new Maneuver("2TW", Maneuver.Speed.TWO, Maneuver.Direction.LEFT, Utils.Difficulty.WHITE, Maneuver.Type.TURN, false),
        new Maneuver("2BW", Maneuver.Speed.TWO, Maneuver.Direction.LEFT, Utils.Difficulty.WHITE, Maneuver.Type.BANK, false),
        new Maneuver("2FB", Maneuver.Speed.TWO, Maneuver.Direction.STRAIGHT, Utils.Difficulty.BLUE, Maneuver.Type.STRAIGHT, false),
        new Maneuver("2NW", Maneuver.Speed.TWO, Maneuver.Direction.RIGHT, Utils.Difficulty.WHITE, Maneuver.Type.BANK, false),
        new Maneuver("2YW", Maneuver.Speed.TWO, Maneuver.Direction.RIGHT, Utils.Difficulty.WHITE, Maneuver.Type.TURN, false),
        new Maneuver("3TR", Maneuver.Speed.THREE, Maneuver.Direction.LEFT, Utils.Difficulty.RED, Maneuver.Type.TURN, false),
        new Maneuver("3BW", Maneuver.Speed.THREE, Maneuver.Direction.LEFT, Utils.Difficulty.WHITE, Maneuver.Type.BANK, false),
        new Maneuver("3FW", Maneuver.Speed.THREE, Maneuver.Direction.STRAIGHT, Utils.Difficulty.WHITE, Maneuver.Type.STRAIGHT, false),
        new Maneuver("3NW", Maneuver.Speed.THREE, Maneuver.Direction.RIGHT, Utils.Difficulty.WHITE, Maneuver.Type.BANK, false),
        new Maneuver("3YR", Maneuver.Speed.THREE, Maneuver.Direction.RIGHT, Utils.Difficulty.RED, Maneuver.Type.TURN, false),
        new Maneuver("4FR", Maneuver.Speed.FOUR, Maneuver.Direction.STRAIGHT, Utils.Difficulty.RED, Maneuver.Type.STRAIGHT, false)
    );

    List<Maneuver> jumpmaster = List.of(
      new Maneuver("1TW", Maneuver.Speed.ONE, Maneuver.Direction.LEFT, Utils.Difficulty.WHITE, Maneuver.Type.TURN, false),
      new Maneuver("1BB", Maneuver.Speed.ONE, Maneuver.Direction.LEFT, Utils.Difficulty.BLUE, Maneuver.Type.BANK, false),
      new Maneuver("1FB", Maneuver.Speed.ONE, Maneuver.Direction.STRAIGHT, Utils.Difficulty.BLUE, Maneuver.Type.STRAIGHT, false),
      new Maneuver("1NW", Maneuver.Speed.ONE, Maneuver.Direction.RIGHT, Utils.Difficulty.WHITE, Maneuver.Type.BANK, false),
      new Maneuver("1YR", Maneuver.Speed.ONE, Maneuver.Direction.RIGHT, Utils.Difficulty.RED, Maneuver.Type.TURN, false),
      new Maneuver("2TW", Maneuver.Speed.TWO, Maneuver.Direction.LEFT, Utils.Difficulty.WHITE, Maneuver.Type.TURN, false),
      new Maneuver("2BB", Maneuver.Speed.TWO, Maneuver.Direction.LEFT, Utils.Difficulty.BLUE, Maneuver.Type.BANK, false),
      new Maneuver("2FB", Maneuver.Speed.TWO, Maneuver.Direction.STRAIGHT, Utils.Difficulty.BLUE, Maneuver.Type.STRAIGHT, false),
      new Maneuver("2NW", Maneuver.Speed.TWO, Maneuver.Direction.RIGHT, Utils.Difficulty.WHITE, Maneuver.Type.BANK, false),
      new Maneuver("2YR", Maneuver.Speed.TWO, Maneuver.Direction.RIGHT, Utils.Difficulty.RED, Maneuver.Type.TURN, false),
      new Maneuver("3LR", Maneuver.Speed.THREE, Maneuver.Direction.LEFT, Utils.Difficulty.RED, Maneuver.Type.SEGNORS_LOOP, true),
      new Maneuver("3BB", Maneuver.Speed.THREE, Maneuver.Direction.LEFT, Utils.Difficulty.BLUE, Maneuver.Type.BANK, false),
      new Maneuver("3FB", Maneuver.Speed.THREE, Maneuver.Direction.STRAIGHT, Utils.Difficulty.BLUE, Maneuver.Type.STRAIGHT, false),
      new Maneuver("3NW", Maneuver.Speed.THREE, Maneuver.Direction.RIGHT, Utils.Difficulty.WHITE, Maneuver.Type.BANK, false),
      new Maneuver("4FW", Maneuver.Speed.FOUR, Maneuver.Direction.STRAIGHT, Utils.Difficulty.WHITE, Maneuver.Type.STRAIGHT, false),
      new Maneuver("4KR", Maneuver.Speed.FOUR, Maneuver.Direction.STRAIGHT, Utils.Difficulty.RED, Maneuver.Type.KOIOGRAN_TURN, true)
    );

    return Stream.of(
        Arguments.of("galactic-empire", "tielnfighter", tieFighter),
        Arguments.of("galactic-republic", "laatigunship", laat),
        Arguments.of("scum-and-villainy", "jumpmaster5000", jumpmaster)
    );
  }

  @ParameterizedTest
  @MethodSource("dialAndManeuverParsingData")
  public void testDialAndManeuverParsing(String factionSubpath, String shipId, List<Maneuver> expectedManeuvers) throws Exception {
    List<Ship> ships = uninitInitializer.processFactionPilots(Paths.get(System.getProperty("java.io.tmpdir"), "xwing-data2", "data", "pilots", factionSubpath));

    Ship ship = ships.stream().filter(it -> it.getXws().equals(shipId)).findFirst().orElseThrow();
    List<Maneuver> maneuvers = ship.getDial().values().stream().collect(java.util.stream.Collectors.toList());
    boolean finalEquals = true;
    for (Maneuver actual : maneuvers) {
      boolean equal = false;
      for (Maneuver ex : expectedManeuvers) {
        if (ex.equals(actual)) {
          equal = true;
        }
      }

      if (!equal) {
        System.out.println(actual.toString() + " not matched");
        finalEquals = false;
      }
    }
    assertTrue(finalEquals);
    HashSet<Maneuver> expectedSet = new HashSet<>();
    expectedSet.addAll(expectedManeuvers);

    HashSet<Maneuver> actualSet = new HashSet<>();
    actualSet.addAll(ship.getDial().values());

    assertEquals(expectedSet.size(), expectedManeuvers.size());
    assertEquals(actualSet.size(), ship.getDial().values().size());
    assertEquals(expectedSet, actualSet);
    assertEquals(Set.of(ship.getDial().values().toArray()), Set.of(expectedManeuvers.toArray()));
  }

  @Test
  public void testInitializationOfSimpleObjects() {
    assertEquals(7, initializedInitializer.factions.size());
    assertEquals(13, initializedInitializer.actions.size());
    assertEquals(12, initializedInitializer.stats.size());
  }

  private static Stream<Arguments> shipStatParsingData() {
    List<ShipStat> tieAdvancedStats = List.of(
        new ShipStat("Front Arc", "attack", 2, null, "Front Arc", "frontarc", 10),
        new ShipStat("", "agility", 3, null, "Agility", "agility", 1),
        new ShipStat("", "hull", 3, null, "Hull", "hull", 2),
        new ShipStat("", "shields", 2, null, "Shields", "shields", 3)
    );

    List<ShipStat> fireballStats = List.of(
        new ShipStat("Front Arc", "attack", 2, null, "Front Arc", "frontarc", 10),
        new ShipStat("", "agility", 2, null, "Agility", "agility", 1),
        new ShipStat("", "hull", 6, null, "Hull", "hull", 2),
        new ShipStat("", "shields", 0, null, "Shields", "shields", 3)
    );

    List<ShipStat> firesprayStats = List.of(
        new ShipStat("Front Arc", "attack", 3, null, "Front Arc", "frontarc", 10),
        new ShipStat("Rear Arc", "attack", 3, null, "Rear Arc", "reararc", 14),
        new ShipStat("", "agility", 2, null, "Agility", "agility", 1),
        new ShipStat("", "hull", 6, null, "Hull", "hull", 2),
        new ShipStat("", "shields", 4, null, "Shields", "shields", 3)
    );

    return Stream.of(
        Arguments.of("galactic-empire", "tieadvancedx1", tieAdvancedStats),
        Arguments.of("resistance", "fireball", fireballStats),
        Arguments.of("scum-and-villainy", "firesprayclasspatrolcraft", firesprayStats)
    );
  }

  @ParameterizedTest
  @MethodSource("shipStatParsingData")
  public void testShipStatParsing(String factionSubpath, String shipId, List<ShipStat> expectedStats) throws Exception {
    List<Ship> ships = initializedInitializer.processFactionPilots(Paths.get(System.getProperty("java.io.tmpdir"), "xwing-data2", "data", "pilots", factionSubpath));
    initializedInitializer.ships.stream().filter(s -> s.getFaction().getXws().equals(factionSubpath)).collect(Collectors.toList());
    Ship ship = ships.stream().filter(s -> s.getXws().equals(shipId)).findFirst().orElseThrow();
    assertEquals(expectedStats, ship.getStats());
  }

  @Test
  public void testUpgradeDeserialization() throws Exception {
    List<Upgrade> upgrades = uninitInitializer.processUpgrades(Paths.get(System.getProperty("java.io.tmpdir"), "xwing-data2", "data", "upgrades"));
    assertTrue(upgrades.size() > 0);
    // TODO add more validations
  }

  private static Stream<Arguments> shipActionData(){
    List<Action> fangFighter = List.of(
        new Action(Utils.Difficulty.WHITE, Action.Type.FOCUS, null, "Focus", "focus", 2),
        new Action(Utils.Difficulty.WHITE, Action.Type.LOCK, null, "Lock", "lock", 4),
        new Action(Utils.Difficulty.WHITE, Action.Type.BARREL_ROLL, new Action(
            Utils.Difficulty.RED, Action.Type.FOCUS, null, "Focus", "focus", 2
        ), "Barrel Roll", "barrelroll", 5),
        new Action(Utils.Difficulty.WHITE, Action.Type.BOOST, new Action(
            Utils.Difficulty.RED, Action.Type.FOCUS, null, "Focus", "focus", 2
        ), "Boost", "boost", 1)
    );

    return Stream.of(
        Arguments.of("scum-and-villainy", "fangfighter", fangFighter)
    );
  }

  @ParameterizedTest
  @MethodSource("shipActionData")
  public void testShipActionParsing(String factionSubpath, String shipId, List<Action> expectedActions) throws Exception {
    List<Ship> ships = initializedInitializer.processFactionPilots(Paths.get(System.getProperty("java.io.tmpdir"), "xwing-data2", "data", "pilots", factionSubpath));
    initializedInitializer.ships.stream().filter(s -> s.getFaction().getXws().equals(factionSubpath)).collect(Collectors.toList());
    Ship ship = ships.stream().filter(s -> s.getXws().equals(shipId)).findFirst().orElseThrow();

    assertEquals(expectedActions, ship.getActions());
  }

  public void testPilotParsing() {}

}

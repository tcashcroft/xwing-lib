package com.tcashcroft.xwinglib;

import com.tcashcroft.xwinglib.model.Faction;
import com.tcashcroft.xwinglib.model.Pilot;
import com.tcashcroft.xwinglib.model.Ship;
import com.tcashcroft.xwinglib.model.Upgrade;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The public entry-point for accessing {@link Ship} and {@link Upgrade} objects.
 */
public class ComponentProducer {

  private final List<Ship> allShips;
  private final List<Upgrade> allUpgrades;

  protected ComponentProducer(List<Ship> allShips, List<Upgrade> allUpgrades) {
    this.allShips = allShips;
    this.allUpgrades = allUpgrades;
  }

  /**
   * Gets the list of all ships in the game.
   *
   * @return List of {@link Ship}
   */
  public List<Ship> getShips() {
    return allShips;
  }

  /**
   * Gets the list of all ships for a given faction.
   *
   * @param faction a {@link Faction.Type} to filter on
   * @return List of {@link Ship}
   */
  public List<Ship> getShips(Faction.Type faction) {
    return allShips.stream()
        .filter(s -> s.getFaction().getType().equals(faction))
        .collect(Collectors.toList());
  }

  /**
   * Gets the list of all ships for a given ship type (also known as chassis).
   *
   * @param chassis a {@link Ship.Type}
   * @return List of {@link Ship}
   */
  public List<Ship> getShips(Ship.Type chassis) {
    return allShips.stream().filter(s -> s.getType().equals(chassis)).collect(Collectors.toList());
  }

  /**
   * Gets the list of all pilots for a given ship type (also known as chassis).
   *
   * @param chassis a {@link Ship.Type}
   * @return List of {@link Pilot}
   */
  public List<Pilot> getPilots(Ship.Type chassis) {
    return allShips.stream().filter(s -> s.getType().equals(chassis))
            .flatMap(s -> s.getPilots().stream())
            .collect(Collectors.toList());
  }

  /**
   * Gets the list of all pilots for a given chassis in a given faction.
   *
   * @param chassis a {@link Ship.Type}
   * @param faction a {@link Faction.Type}
   * @return List of {@link Pilot}
   */
  public List<Pilot> getPilots(Ship.Type chassis, Faction.Type faction) {
    return getPilots(chassis)
            .stream()
            .filter(p -> getShips(faction)
                    .stream().flatMap(s -> s.getPilots().stream())
                    .collect(Collectors.toList()).contains(p))
            .collect(Collectors.toList());
  }

  /**
   * Gets a list of all upgrades in the game.
   *
   * @return List of {@link Upgrade}
   */
  public List<Upgrade> getUpgrades() {
    return allUpgrades;
  }

  /**
   * Gets a list of all upgrades with a given faction legality.
   *
   * @param faction {@link Faction.Type}
   * @return List of {@link Upgrade}
   */
  public List<Upgrade> getUpgrades(Faction.Type faction) {
    List<Upgrade> factionUpgrades = new ArrayList<>();
    for (Upgrade upgrade : allUpgrades) {
      for (Upgrade.Restriction restriction : upgrade.getRestrictions()) {
        Optional<String> factionOptional =
            restriction.getFactions().stream()
                .filter(fs -> fs.equals(faction.toString()))
                .findFirst();
        if (factionOptional.isPresent()) {
          factionUpgrades.add(upgrade);
        }
      }
    }
    return factionUpgrades;
  }
}

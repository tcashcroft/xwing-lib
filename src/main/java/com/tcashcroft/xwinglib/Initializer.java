package com.tcashcroft.xwinglib;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcashcroft.xwinglib.exception.XwingLibInitializationException;
import com.tcashcroft.xwinglib.model.Action;
import com.tcashcroft.xwinglib.model.Faction;
import com.tcashcroft.xwinglib.model.Ship;
import com.tcashcroft.xwinglib.model.ShipStat;
import com.tcashcroft.xwinglib.model.Upgrade;
import edu.byu.hbll.json.UncheckedObjectMapper;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

/** Initializes X-Wing Library data. */
@Slf4j
public class Initializer {

  private final URI dataRepoTarget;
  private final ObjectMapper mapper;
  protected List<Faction> factions = new ArrayList<>();
  protected List<Action> actions = new ArrayList<>();
  protected List<Ship> ships = new ArrayList<>();
  protected List<Upgrade> upgrades = new ArrayList<>();
  protected List<Stat> stats = new ArrayList<>();
  @Getter private ComponentProducer componentProducer;

  /**
   * Constructs a library initializer class when given a URI that points to the xwing-data2
   * repository. Initial deserialization of objects occurs and passed to an instantiation of the
   * {@link ComponentProducer}. While initial data cleanup will be facilitated in
   * this class, data linking and relationships will be handled by the ShipProducer.
   *
   * @param dataRepoTarget a {@link java.net.URI} that points to the xwing-data2 repository (and
   *     will be used to clone the repo)
   *
   * @throws XwingLibInitializationException if any errors occur downloading the repo and processing the components
   */
  public Initializer(URI dataRepoTarget) throws XwingLibInitializationException {
    this.dataRepoTarget = dataRepoTarget;
    this.mapper = new UncheckedObjectMapper();

    init();
  }

  /**
   * Initializes the X-Wing Library's data from the xwing-data2 repository.
   *
   * <p>The repository is cloned or pulled, the various objects are parsed out. Where
   * applicable, objects are augmented with additional data in the repository. Successful
   * initialization results in a fully instantiated {@link ComponentProducer} that is ready for use in
   * retrieving the various library objects.
   *
   * @throws XwingLibInitializationException when an error occurs during initialization
   */
  public void init() throws XwingLibInitializationException {
    try {
      // clone the repo
      final String sysTmpDirString = System.getProperty("java.io.tmpdir");
      log.info("Cloning repo to {}", sysTmpDirString);
      final String repositoryName = "xwing-data2";
      this.cloneRepoToTempDir(Paths.get(sysTmpDirString, repositoryName).toFile());

      // prep the paths
      final String dataRepoString = Paths.get(sysTmpDirString, repositoryName, "data").toString();
      final Path factionsPath = Paths.get(dataRepoString, "factions", "factions.json");
      final Path actionsPath = Paths.get(dataRepoString, "actions", "actions.json");
      final Path upgradesDirPath = Paths.get(dataRepoString, "upgrades");
      final Path statsPath = Paths.get(dataRepoString, "stats", "stats.json");

      final String pilotsDirSubpath = "pilots";
      final Path foPilotsDirPath = Paths.get(dataRepoString, pilotsDirSubpath, "first-order");
      final Path gePilotsDirPath = Paths.get(dataRepoString, pilotsDirSubpath, "galactic-empire");
      final Path grPilotsDirPath =
          Paths.get(dataRepoString, pilotsDirSubpath, "galactic-republic");
      final Path rebelPilotsDirPath =
          Paths.get(dataRepoString, pilotsDirSubpath, "rebel-alliance");
      final Path resistancePilotsDirPath =
          Paths.get(dataRepoString, pilotsDirSubpath, "resistance");
      final Path scumPilotsDirPath =
          Paths.get(dataRepoString, pilotsDirSubpath, "scum-and-villainy");
      final Path cisPilotsDirPath =
          Paths.get(dataRepoString, pilotsDirSubpath, "separatist-alliance");

      // deserialize the data
      JsonNode factionsRoot = mapper.readTree(factionsPath.toFile());
      factions = new ArrayList<>();
      for (JsonNode n : factionsRoot) {
        Faction faction = new Faction();
        faction.setName(n.get("name").asText());
        faction.setXws(n.get("xws").asText());
        faction.setFfg(n.path("ffg").asInt());
        faction.setIcon(URI.create(n.get("icon").asText()));
        factions.add(faction);
        log.info("Adding faction {} to list", faction);
      }

      JsonNode actionsRoot = mapper.readTree(actionsPath.toFile());
      actions = new ArrayList<>();
      for (JsonNode n : actionsRoot) {
        actions.add(mapper.treeToValue(n, Action.class));
      }

      JsonNode statsRoot = mapper.readTree(statsPath.toFile());
      stats = new ArrayList<>();
      for (JsonNode n : statsRoot) {
        stats.add(mapper.treeToValue(n, Stat.class));
      }

      upgrades = processUpgrades(upgradesDirPath);

      ships = new ArrayList<>();
      ships.addAll(processFactionPilots(foPilotsDirPath));
      ships.addAll(processFactionPilots(gePilotsDirPath));
      ships.addAll(processFactionPilots(grPilotsDirPath));
      ships.addAll(processFactionPilots(rebelPilotsDirPath));
      ships.addAll(processFactionPilots(resistancePilotsDirPath));
      ships.addAll(processFactionPilots(scumPilotsDirPath));
      ships.addAll(processFactionPilots(cisPilotsDirPath));

      // augment upgrades to resolve data format inconsistencies
      for (Upgrade upgrade : upgrades) {
        for (Upgrade.Side side : upgrade.getSides()) {
          if (side.getGrantsStats().size() > 0) {
            List<ShipStat> augmentedUpgradeStats = augmentShipStats(stats, side.getGrantsStats());
            side.setGrantsStats(augmentedUpgradeStats);
          }
        }
      }

      // populate the ship producer
      componentProducer = new ComponentProducer(ships, upgrades);

    } catch (IOException e) {
      throw new XwingLibInitializationException("Error parsing repository files", e);
    }
  }

  /**
   * Clones the repo to the system's temp directory, or pulls the repo if it already exists.
   *
   * @param targetFile the target file for the repo directory
   * @throws XwingLibInitializationException if an error occurs cloning or pulling the repo
   */
  protected void cloneRepoToTempDir(File targetFile) throws XwingLibInitializationException {
    try {
      if (targetFile.exists()) {
        // pull
        Git.open(targetFile).pull();
      } else {
        // clone
        Git.cloneRepository().setURI(dataRepoTarget.toString()).setDirectory(targetFile).call();
      }
    } catch (IOException e) {
      throw new XwingLibInitializationException("Unable to pull repository", e);
    } catch (GitAPIException ee) {
      throw new XwingLibInitializationException("Error cloning repository", ee);
    }
  }

  /**
   * Processes individual pilot/ship files and maps them to {@link
   * com.tcashcroft.xwinglib.model.Ship} objects.
   *
   * @param factionDir the full path to the faction dir in the X-Wing data repo
   * @return List of {@link com.tcashcroft.xwinglib.model.Ship}
   */
  protected List<Ship> processFactionPilots(Path factionDir) {
    File factionDirFile = factionDir.toFile();
    if (factionDirFile.isDirectory()) {
      List<Ship> ships = new ArrayList<>();
      for (File shipFile : factionDirFile.listFiles()) {
        log.info("Ship file name: {}", shipFile.toString());
        Optional<Ship> shipOptional = processShip(shipFile);
        shipOptional.ifPresentOrElse(ships::add, () -> log.warn("Ship file not processed successfully: {}", shipFile));
      }
      return ships;
    } else {
      return List.of();
    }
  }

  /**
   * Processes an individual ship. Suppresses any deserialization errors and emits a warning.
   * This is to prevent upstream errors or unimplemented chassis from failing initialization
   * of the rest of the module.
   *
   * @param shipFile the File containing ship and pilot data from X-Wing Data 2
   * @return {@link Optional} of {@link Ship}
   */
  protected Optional<Ship> processShip(File shipFile) {
    try {
      Ship ship = mapper.readValue(shipFile, Ship.class);

      // Augment objects
      List<ShipStat> augmentedStats = augmentShipStats(stats, ship.getStats());
      ship.setStats(augmentedStats);
      ship.setFaction(augmentFaction(factions, ship.getFaction()));
      List<Action> augmentedActions = augmentActions(actions, ship.getActions());
      ship.setActions(augmentedActions);

      return Optional.of(ship);
    } catch (IOException | RuntimeException e) {
      log.warn("Ship file could not be deserialized into a Ship object: {}", shipFile);
      log.debug(e.getMessage(), e);
      return Optional.ofNullable(null);
    }
  }

  /**
   * Augments the {@link ShipStat} object with {@link Stat} object data.
   *
   * @param stats the list of {@link Stat} objects
   * @param shipStats the list of {@link ShipStat} objects
   * @return list of augmented {@link ShipStat}
   */
  protected List<ShipStat> augmentShipStats(List<Stat> stats, List<ShipStat> shipStats) {
    List<ShipStat> augmentedShipStats = new ArrayList<>();
    for (ShipStat shipStat : shipStats) {
      ShipStat augmentedShipStat = new ShipStat();
      augmentedShipStat.setArc(shipStat.getArc());
      augmentedShipStat.setType(shipStat.getType());
      augmentedShipStat.setValue(shipStat.getValue());
      augmentedShipStat.setRecovers(shipStat.getRecovers());

      Optional<Stat> statOptional =
          stats.stream()
              .filter(s -> s.getName().equalsIgnoreCase(shipStat.getType()))
              .findFirst()
              .or(
                  () ->
                      stats.stream()
                          .filter(s -> s.getName().equalsIgnoreCase(shipStat.getArc()))
                          .findFirst());
      if (statOptional.isPresent()) {
        Stat stat = statOptional.get();
        augmentedShipStat.setName(stat.name);
        augmentedShipStat.setXws(stat.xws);
        augmentedShipStat.setFfg(stat.ffg);
      } else {
        // TODO: should this be an error instead of a warning? Hard stop?
        log.warn("Stat match not found for {}", shipStat.getType());
      }
      augmentedShipStats.add(augmentedShipStat);
    }
    return augmentedShipStats;
  }

  /**
   * Augments a {@link Faction} with data from the xwing-data2 faction objects.
   *
   * @param factions the list of faction objects from the xwing-data2 repo
   * @param faction the {@link Faction} to augment
   * @return the augmented faction
   */
  protected Faction augmentFaction(List<Faction> factions, Faction faction) {
    Faction augmentedFaction = new Faction();
    augmentedFaction.setName(faction.getName());
    augmentedFaction.setIcon(faction.getIcon());
    augmentedFaction.setType(faction.getType());
    augmentedFaction.setXws(faction.getXws());
    augmentedFaction.setFfg(faction.getFfg());

    Optional<Faction> factionOptional =
        factions.stream().filter(f -> f.getName().equals(faction.getType().toString())).findFirst();
    if (factionOptional.isPresent()) {
      Faction fullFaction = factionOptional.get();
      augmentedFaction.setName(fullFaction.getName());
      augmentedFaction.setXws(fullFaction.getXws());
      augmentedFaction.setFfg(fullFaction.getFfg());
      augmentedFaction.setIcon(fullFaction.getIcon());
    } else {
      log.warn("Faction did not match for {}", faction.getName());
    }
    return augmentedFaction;
  }

  /**
   * Augments an {@link Action} with data from the xwing-data2 action objects.
   *
   * @param actions the action objects from xwing-data2
   * @param actionToAugment the {@link Action} to augment
   * @return the augmented action
   */
  protected Action augmentAction(List<Action> actions, Action actionToAugment) {
    Action augmentedAction = new Action();
    augmentedAction.setDifficulty(actionToAugment.getDifficulty());
    augmentedAction.setType(actionToAugment.getType());

    if (actionToAugment.getLinkedAction() != null) {
      Action augmentedLinkedAction = augmentAction(actions, actionToAugment.getLinkedAction());
      augmentedAction.setLinkedAction(augmentedLinkedAction);
    }

    Optional<Action> ffgActionOptional =
        actions.stream()
            .filter(
                a ->
                    a.getName()
                        .replace("-", "_")
                        .replace(" ", "_")
                        .equalsIgnoreCase(actionToAugment.getType().toString()))
            .findFirst();

    if (ffgActionOptional.isPresent()) {
      Action ffgAction = ffgActionOptional.get();
      augmentedAction.setFfg(ffgAction.getFfg());
      augmentedAction.setXws(ffgAction.getXws());
      augmentedAction.setName(ffgAction.getName());
    } else {
      log.warn("Action did not match for {}", actionToAugment.getType());
    }
    return augmentedAction;
  }

  /**
   * Augments all of a list of {@link Action} with data from the xwing-data2 action objects.
   *
   * @param actions the list of action objects from the xwing-data2 repo
   * @param shipActions the list of {@link Action} to augmented
   * @return the augmented actions list
   */
  protected List<Action> augmentActions(List<Action> actions, List<Action> shipActions) {
    List<Action> augmentedActions = new ArrayList<>();
    for (Action shipAction : shipActions) {
      Action augmentedAction = augmentAction(actions, shipAction);
      augmentedActions.add(augmentedAction);
    }
    return augmentedActions;
  }

  /**
   * Processes individual upgrade files and maps them to Upgrade objects.
   *
   * @param upgradesDir the full path to the upgrade dir in the X-Wing data repo
   * @return List of {@link com.tcashcroft.xwinglib.model.Upgrade}
   * @throws IOException if an error throws while deserializing upgrade objects
   */
  protected List<Upgrade> processUpgrades(Path upgradesDir) throws IOException {
    File upgradeDirFile = upgradesDir.toFile();
    if (upgradeDirFile.isDirectory()) {
      List<Upgrade> upgrades = new ArrayList<>();
      for (File upgradeFile : upgradeDirFile.listFiles()) {
        List<Upgrade> upgradesByType = new ArrayList<>();
        JsonNode upgradeRoot = mapper.readTree(upgradeFile);
        for (JsonNode n : upgradeRoot) {
          upgrades.add(mapper.treeToValue(n, Upgrade.class));
        }
      }
      return upgrades;
    } else {
      return List.of();
    }
  }

  /** Represents the list of Stats in the xwing-data2 repo under data/stats/stats.json */
  @Data
  private static class Stat {
    private String name;
    private String xws;
    private int ffg;

    @JsonAlias("ffg_name")
    private String ffgName;
  }
}

package com.tcashcroft.xwinglib.bdd;

import com.tcashcroft.xwinglib.Initializer;
import com.tcashcroft.xwinglib.ShipProducer;
import com.tcashcroft.xwinglib.model.Faction;
import com.tcashcroft.xwinglib.model.Pilot;
import com.tcashcroft.xwinglib.model.Ship;
import com.tcashcroft.xwinglib.model.Upgrade;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class FactionsFeatureTest {

  private Initializer initializer;
  private ShipProducer shipProducer;
  private List<Ship> factionShips;
  private Faction.Type factionType;
  private List<Upgrade> factionUpgrades;
  private Ship.Type chassis;
  private List<Pilot> chassisPilots;

  @Given("a shipProducer")
  public void givenAShipmaker() throws Exception {
    initializer = new Initializer(URI.create("https://github.com/guidokessels/xwing-data2.git"));
    initializer.init();
    shipProducer = initializer.getShipProducer();
  }

  @When("I ask for the {string} ship list")
  public void getShipsByFactionId(String faction) {
    factionType = Faction.Type.parse(faction);
    factionShips = shipProducer.getShips(factionType);
  }

  @Then("I get the {listOfShipStrings}")
  public void validateShips(List<String> shiplist) {
    List<String> actualShipList = factionShips.stream().map(s -> s.getXws()).collect(Collectors.toList());
    Collections.sort(shiplist);
    Collections.sort(actualShipList);
    assertEquals(shiplist, actualShipList);
  }

  @When("I ask for the {string}'s upgrade list")
  public void getUpgradesByFactionId(String faction) {
    factionType = Faction.Type.parse(faction);
    factionUpgrades = shipProducer.getUpgrades(factionType);
  }

  @Then("I get {listOfUpgradesStrings}, not including faction-agnostic upgrades")
  public void validateUpgradesByFaction(List<String> upgrades) {
    Collections.sort(upgrades);
    List<String> actualUpgradeList = factionUpgrades.stream().map(u -> u.getXws()).collect(Collectors.toList());
    Collections.sort(actualUpgradeList);
    assertEquals(upgrades, actualUpgradeList);
  }

  @ParameterType("(?:[^,]*)(?:,\\s?[^,]*)*")
  // https://stackoverflow.com/questions/64366164/passing-list-of-strings-as-cucumber-parameter
  public List<String> listOfStrings(String arg){
    return Arrays.asList(arg.split(",\\s?")).stream().map(s -> s.trim()).collect(Collectors.toList());
  }

  @ParameterType("(?:[^,]*)(?:,\\s?[^,]*)*")
  public List<String> listOfShipStrings(String arg) {
    return Arrays.asList(arg.split(",\\s?")).stream().map(s -> s.trim()).collect(Collectors.toList());
  }

  @ParameterType("(?:[^,]*)(?:,\\s?[^,]*)*")
  public List<String> listOfUpgradesStrings(String arg) {
    return Arrays.asList(arg.split(",\\s?")).stream().map(s -> s.trim()).collect(Collectors.toList());
  }

  @When("I ask for all pilots of a {string}")
  public void getChassisPilots(String chassis) {
    this.chassis = Ship.Type.parse(chassis);
    this.chassisPilots = shipProducer.getPilots(this.chassis);
  }

  @Then("I get a {listOfStrings}")
  public void validateChassisPilots(List<String> expectedChassisPilots) {
    List<String> actualChassisPilots = this.chassisPilots.stream().map(p -> p.getXws()).collect(Collectors.toList());
    Collections.sort(actualChassisPilots);
    Collections.sort(expectedChassisPilots);
    assertEquals(expectedChassisPilots, actualChassisPilots);
  }

  @When("I ask for all the pilots of a {string}")
  public void getChassisPilots2(String chassis) {
    this.chassis = Ship.Type.parse(chassis);
    this.chassisPilots = shipProducer.getPilots(this.chassis);
  }

  @And("they share a {string}")
  public void getChassisPilotsByFaction(String faction) {
    this.factionType = Faction.Type.parse(faction);
    this.chassisPilots = shipProducer.getPilots(chassis, factionType);
  }

  @Then("I get faction-specific {listOfStrings}")
  public void validateChassisPilotsByFaction(List<String> expectedChassisPilots) {
    List<String> actualChassisPilots = this.chassisPilots.stream().map(p -> p.getXws()).collect(Collectors.toList());
    Collections.sort(actualChassisPilots);
    Collections.sort(expectedChassisPilots);
    assertEquals(expectedChassisPilots, actualChassisPilots);
  }
}

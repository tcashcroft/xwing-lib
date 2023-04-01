package com.tcashcroft.xwinglib.bdd;

import com.tcashcroft.xwinglib.ComponentProducer;
import com.tcashcroft.xwinglib.Initializer;
import com.tcashcroft.xwinglib.model.ShipStat;
import com.tcashcroft.xwinglib.model.Upgrade;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UpgradeFeatureTest {

  private Initializer initializer;
  private ComponentProducer componentProducer;
  private Upgrade doubleSidedUpgrade;
  private Upgrade statsUpgrade;

  @Given("a componentProducer for upgrades")
  public void givenAComponentProducer() throws Exception {
    initializer = new Initializer(URI.create("https://github.com/guidokessels/xwing-data2.git"));
    initializer.init();
    componentProducer = initializer.getComponentProducer();
  }

  @When("I ask for a double sided {string} card")
  public void getUpgradeByXws(String xws) {
    doubleSidedUpgrade = componentProducer.getUpgrades().stream().filter(u -> u.getXws().equals(xws)).findFirst().get();
  }

  @Then("both sides are valid")
  public void testSideParsing() {
    assertEquals(2, doubleSidedUpgrade.getSides().size());

    assertNotNull(doubleSidedUpgrade.getSides().get(0).getTitle());
    assertNotNull(doubleSidedUpgrade.getSides().get(0).getImage());
    assertNotNull(doubleSidedUpgrade.getSides().get(0).getArtwork());

    assertNotNull(doubleSidedUpgrade.getSides().get(1).getTitle());
    assertNotNull(doubleSidedUpgrade.getSides().get(1).getImage());
    assertNotNull(doubleSidedUpgrade.getSides().get(1).getArtwork());
  }

  @When("I ask for an upgrade {string} that grants stats")
  public void getUpgradeWithStats(String xws) {
    statsUpgrade = componentProducer.getUpgrades().stream().filter(u -> u.getXws().equals(xws)).findFirst().get();
  }

  @Then("stats array is populated")
  public void testStatsArray() {
    List<ShipStat> grantedStats = statsUpgrade.getSides().get(0).getGrantsStats();
    assertNotNull(grantedStats);
    assertTrue(grantedStats.size() > 0);
  }
}

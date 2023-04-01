package com.tcashcroft.xwinglib.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tcashcroft.xwinglib.serialization.SideDeserializer;
import com.tcashcroft.xwinglib.serialization.UpgradeDeserializer;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

/**
 * Represents an upgrade card.
 */
@Data
@JsonDeserialize(using = UpgradeDeserializer.class)
public class Upgrade {

  /**
   * The types of upgrades in the game.
   */
  public enum Type {
    ASTROMECH,
    CANNON,
    CARGO,
    COMMAND,
    CONFIGURATION,
    CREW,
    DEVICE,
    FORCE_POWER,
    GUNNER,
    HARDPOINT,
    HYPERDRIVE,
    ILLICIT,
    MISSILE,
    MODIFICATION,
    SENSOR,
    TACTICAL_RELAY,
    TALENT,
    TEAM,
    TECH,
    TITLE,
    TORPEDO,
    TURRET;

    private static final List<String> charsToReplace = Arrays.asList(" ", "-", "/");

    /**
     * Parses the Type from a string by sanitizing the string prior to calling valueOf.
     *
     * @param value a string type
     * @return the Type
     */
    public static Type parse(String value) {
      String sanitizedString = value;
      for (String toReplace : charsToReplace) {
        sanitizedString = sanitizedString.replaceAll(toReplace, "_");
      }
      return Type.valueOf(sanitizedString.toUpperCase());
    }
  }

  private String name;
  private int limited;
  private String xws;
  private Integer ffg;
  private List<Side> sides;
  private Cost cost;
  private List<Restriction> restrictions;
  private boolean standard;
  private boolean extended;
  private boolean epic;

  /**
   * Represents a restriction on the upgrade.
   *
   * <p>
   * Restrictions can take many forms. It may be a specific ship chassis, or size. This object is
   * currently represented as lists of strings for the various types of restrictions (with the
   * exceptions of {@link com.tcashcroft.xwinglib.model.Utils.Keyword}) but may be formalized in the
   * future if that facilitates usage.
   * </p>
   */
  @Data
  public static class Restriction {
    private List<String> ships = new ArrayList<>();
    private List<String> sizes = new ArrayList<>();
    private List<String> factions = new ArrayList<>();
    private List<String> equipped = new ArrayList<>();
    private List<Utils.Keyword> keywords = new ArrayList<>();
    private List<String> arcs = new ArrayList<>();

    @JsonProperty("force_side")
    private List<String> forceSide = new ArrayList<>();

    private List<String> names = new ArrayList<>();
    private List<String> shipAbility;

    @JsonProperty("non-limited")
    private boolean nonLimited;

    private boolean standardized;
    private boolean solitary;
    private Action action;

    @JsonSetter("keywords")
    public void setKeywordsFromStringList(List<String> values) {
      this.keywords = values.stream().map(v -> Utils.Keyword.parse(v)).collect(Collectors.toList());
    }
  }

  /**
   * Represents a cost for an upgrade. Upgrades that have not been assigned a point cost are not
   * always consistently represented - proceed with caution.
   */
  @Data
  public static class Cost {
    private int value;
    private String

        // ? (or similar) is the value for upgrades that haven't had points updated yet
        stringValue;

    @JsonSetter("value")
    private void setValueFromJson(String s) {
      try {
        value = Integer.parseInt(s);
      } catch (NumberFormatException e) {
        value = -1;
        stringValue = s;
      }
    }
  }

  /**
   * Represents data specific to a given side of the upgrade card.
   */
  @Data
  @JsonDeserialize(using = SideDeserializer.class)
  public static class Side {
    private String title;
    private Type type;
    private String ability;
    private URI image;
    private List<String> slots;

    // TODO deal with this
    // the JSON will mix these within the same array. They will need to be parsed object by object
    private List<Action> grantsActions;
    private List<Force> grantsForce;
    private List<ShipStat> grantsStats;
    // {"type": "slot", "value": "Device", "amount": 1}
    private List<Upgrade.Type> grantsSlots;


    private URI artwork;
    private int ffg;
  }
}

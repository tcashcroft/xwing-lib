package com.tcashcroft.xwinglib.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tcashcroft.xwinglib.serialization.UpgradeDeserializer;
import lombok.Data;

import java.net.URI;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonDeserialize(using = UpgradeDeserializer.class)
public class Upgrade {
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

  @Data
  public static class Cost {
    private int value;
    private String
        stringValue; // ? (or similar) are the value for upgrades that haven't had points updated
                     // yet

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

  @Data
  public static class Side {
    private String title;
    private Type type;
    private String ability;
    private URI image;
    private List<String> slots;
    private URI artwork;
    private int ffg;
  }
}

package com.tcashcroft.xwinglib.model;

import com.fasterxml.jackson.annotation.JsonSetter;
import edu.byu.hbll.misc.Strings;
import java.net.URI;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Represents a faction.
 */
@Data
@EqualsAndHashCode
public class Faction {

  /**
   * The faction Type.
   */
  public enum Type {
    CIS("Separatist Alliance"),
    FIRST_ORDER("First Order"),
    GALACTIC_EMPIRE("Galactic Empire"),
    GALACTIC_REPUBLIC("Galactic Republic"),
    REBEL_ALLIANCE("Rebel Alliance"),
    RESISTANCE("Resistance"),
    SCUM("Scum and Villainy");

    String value;

    Type(String value) {
      this.value = value;
    }

    /**
     * Parses a Type from a given string by sanitizing it prior to calling valueOf.
     *
     * @param value the string value
     * @return the Type
     */
    public static Type parse(String value) {
      Type type;
      switch (value.toLowerCase().replace("-", "").replace(" ", "")) {
        case "separatistalliance":
          type = CIS;
          break;
        case "firstorder":
          type = FIRST_ORDER;
          break;
        case "galacticempire":
          type = GALACTIC_EMPIRE;
          break;
        case "galacticrepublic":
          type = GALACTIC_REPUBLIC;
          break;
        case "rebelalliance":
          type = REBEL_ALLIANCE;
          break;
        case "resistance":
          type = RESISTANCE;
          break;
        case "scumandvillainy":
          type = SCUM;
          break;
        default:
          type = GALACTIC_EMPIRE;
      }
      return type;
    }

    @Override
    public String toString() {
      return this.value;
    }
  }

  private Type type;
  private String name;
  private String xws;
  private Integer ffg;
  private URI icon;

  /**
   * Sets the faction name if it isn't blank.
   *
   * @param name the string value
   */
  @JsonSetter("name")
  public void setName(String name) {
    if (!Strings.isBlank(name)) {
      this.name = name;
      this.type = Type.parse(name);
    }
  }
}

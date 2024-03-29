package com.tcashcroft.xwinglib.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a ship action.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Action {
  /**
   * The types of actions in the game.
   */
  public enum Type {
    BARREL_ROLL,
    BOOST,
    CALCULATE,
    CLOAK,
    COORDINATE,
    EVADE,
    FOCUS,
    JAM,
    LOCK,
    SLAM,
    REINFORCE,
    RELOAD,
    ROTATE_ARC;

    public static Type parse(String value) {
      return Type.valueOf(value.toUpperCase().replace("-", "_").replace(" ", "_"));
    }
  }

  private Utils.Difficulty difficulty;
  private Type type;

  @JsonAlias({"linked", "linkedAction"})
  private Action linkedAction;

  private String name;
  private String xws;
  private Integer ffg;

  @JsonSetter
  public void setType(String value) {
    this.type = Type.parse(value);
  }

  public void setType(Type type) {
    this.type = type;
  }

  @JsonSetter
  public void setDifficulty(String value) {
    this.difficulty = Utils.Difficulty.parse(value);
  }

  public void setDifficulty(Utils.Difficulty difficulty) {
    this.difficulty = difficulty;
  }
}

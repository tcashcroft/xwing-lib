package com.tcashcroft.xwinglib.model;

/**
 * Enums that are shared between more than one main model.
 */
public class Utils {

  /**
   * The sizes of ships in the game.
   */
  public enum Size {
    SMALL("Small"),
    MEDIUM("Medium"),
    LARGE("Large"),
    HUGE("Huge");

    final String value;

    Size(String value) {
      this.value = value;
    }

    public static Size parse(String value) {
      return Size.valueOf(value.toUpperCase());
    }
  }

  /**
   * The colors/difficulties for maneuvers or actions in the game.
   */
  public enum Difficulty {
    WHITE,
    BLUE,
    RED,
    PURPLE;

    public static Difficulty parse(String value) {
      return Difficulty.valueOf(value.toUpperCase());
    }
  }

  /**
   * The types of keywords supported in the game.
   */
  public enum Keyword {
    A_WING,
    B_WING,
    BOUNTY_HUNTER,
    CLONE,
    DARK_SIDE,
    DROID,
    FREIGHTER,
    JEDI,
    LIGHT_SIDE,
    MANDALORIAN,
    PARTISAN,
    SITH,
    SPECTRE,
    TIE,
    X_WING,
    Y_WING,
    YT_1300;

    public static Keyword parse(String value) {
      return Keyword.valueOf(value.replace("-", "_").replace(" ", "_").toUpperCase());
    }
  }
}

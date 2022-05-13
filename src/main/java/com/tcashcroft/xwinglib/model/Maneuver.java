package com.tcashcroft.xwinglib.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Represents a maneuver.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Maneuver {

  /**
   * An enum for the speed of the maneuver.
   */
  public enum Speed {
    ZERO("0"),
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5");

    String value;

    Speed(String value) {
      this.value = value;
    }

    /**
     * Parses a speed from a string value.
     *
     * @param value the string value
     * @return the Speed
     */
    public static Speed parse(String value) {
      switch (value) {
        case "0":
          return ZERO;
        case "1":
          return ONE;
        case "2":
          return TWO;
        case "3":
          return THREE;
        case "4":
          return FOUR;
        case "5":
          return FIVE;
        default:
          return ONE;
      }
    }
  }

  /**
   * The direction of the maneuver (left, right, or straight).
   */
  public enum Direction {
    LEFT,
    RIGHT,
    STRAIGHT
  }

  /*
    Maneuver Codes from xwing-data2
    A - Reverse left
    B - Left bank
    D - Reverse right
    E - Left talon roll
    F - Straight
    K - Koiogran turn
    L - Left sloop
    N - Right bank
    O - Stop
    P - Right sloop
    R - Right talon roll
    S - Reverse straight
    T - Left turn
    Y - Right turn
  */

  /**
   * The type of the maneuver.
   */
  public enum Type {
    STRAIGHT,
    REVERSE_STRAIGHT,
    REVERSE_BANK,
    TALLON_ROLL,
    SEGNORS_LOOP,
    KOIOGRAN_TURN,
    STOP,
    TURN,
    BANK
  }

  private String maneuverId;
  private Speed speed;
  private Direction direction;
  private Utils.Difficulty difficulty;
  private Type type;
  private boolean advanced;
}

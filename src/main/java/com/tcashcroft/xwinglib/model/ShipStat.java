package com.tcashcroft.xwinglib.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * A model of a ship stat.
 *
 * <p>Example: {type: Hull, value: 3}</p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ShipStat {
  private String arc;
  private String type;
  private int value;
  private Integer recovers;

  private String name;
  private String xws;
  private Integer ffg;
}

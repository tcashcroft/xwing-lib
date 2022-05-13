package com.tcashcroft.xwinglib.model;

import lombok.Data;

/**
 * A model of a ship ability.
 *
 * <p>Example: {name: "Autothrusters", text: "After you perform..."}</p>
 */
@Data
public class ShipAbility {
  private String name;
  private String text;
}

package com.tcashcroft.xwinglib.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tcashcroft.xwinglib.serialization.PilotDeserializer;
import lombok.Data;

import java.net.URI;
import java.util.List;

@Data
@JsonDeserialize(using = PilotDeserializer.class)
public class Pilot {
  private String name;
  private String caption;
  private int initiative;
  private int limited;
  private int cost;
  private int loadout;
  private String xws;
  private String text;
  private String ability;
  private URI image;
  private ShipAbility shipAbility;
  private List<Upgrade.Type> slots;
  private URI artwork;
  private Integer ffg;
  private boolean standard;
  private boolean extended;
  private boolean epic;
  private List<Utils.Keyword> keywords;
  private Force force;

  private Ship ship;
}

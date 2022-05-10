package com.tcashcroft.xwinglib.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.tcashcroft.xwinglib.model.Pilot;
import com.tcashcroft.xwinglib.model.ShipAbility;
import com.tcashcroft.xwinglib.model.Upgrade;
import com.tcashcroft.xwinglib.model.Utils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class PilotDeserializer extends JsonDeserializer<Pilot> {

  @Override
  public Pilot deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
    JsonNode root = jsonParser.readValueAsTree();
    Pilot pilot = new Pilot();
    pilot.setName(root.get("name").asText());
    pilot.setInitiative(root.get("initiative").asInt());
    pilot.setLimited(root.get("limited").asInt());
    pilot.setCost(root.get("cost").asInt());
    pilot.setLoadout(root.get("loadout").asInt());
    pilot.setXws(root.get("xws").asText());
    if (root.has("text")) {
      pilot.setText(root.get("text").asText());
    }
    if (root.has("image")) {
      pilot.setImage(URI.create(root.get("image").asText()));
    } else {
      log.warn("Image not found for pilot {}", pilot.getName());
    }
    ShipAbility shipAbility = new ShipAbility();
    if (root.has("shipAbility")) {
      shipAbility.setName(root.get("shipAbility").get("name").asText());
      shipAbility.setText(root.get("shipAbility").get("text").asText());
      pilot.setShipAbility(shipAbility);
    }
    if (root.has("slots")) {
      List<Upgrade.Type> slots = new ArrayList<>();
      for (JsonNode n : root.path("slots")) {
        slots.add(Upgrade.Type.parse(n.asText()));
      }
      pilot.setSlots(slots);
    }
    if (root.has("artwork")) {
      pilot.setArtwork(URI.create(root.get("artwork").asText()));
    }
    if (root.has("ffg")) {
      pilot.setFfg(root.get("ffg").asInt());
    }
    pilot.setStandard(root.get("standard").asBoolean());
    pilot.setExtended(root.get("extended").asBoolean());
    if (root.has("keywords")) {
      List<Utils.Keyword> keywords = new ArrayList<>();
      for (JsonNode n : root.path("keywords")) {
        keywords.add(Utils.Keyword.parse(n.asText()));
      }
      pilot.setKeywords(keywords);
    }
    pilot.setEpic(root.get("epic").asBoolean());

    return pilot;
  }
}

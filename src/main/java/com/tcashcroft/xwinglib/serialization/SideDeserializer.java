package com.tcashcroft.xwinglib.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcashcroft.xwinglib.model.Action;
import com.tcashcroft.xwinglib.model.Force;
import com.tcashcroft.xwinglib.model.ShipStat;
import com.tcashcroft.xwinglib.model.Upgrade;
import edu.byu.hbll.json.UncheckedObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SideDeserializer extends JsonDeserializer<Upgrade.Side> {

  private final ObjectMapper mapper;

  public SideDeserializer() {
    mapper = new UncheckedObjectMapper();
  }

  @Override
  public Upgrade.Side deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
    JsonNode root = jsonParser.readValueAsTree();
    Upgrade.Side side = new Upgrade.Side();
    side.setTitle(root.get("title").asText());
    side.setType(Upgrade.Type.valueOf(root.get("type").asText().toUpperCase().replace(" ", "_")));
    side.setAbility(root.path("ability").asText());
    List<String> slots = new ArrayList<>();
    if (root.has("slots")) {
      for (JsonNode slot : root.get("slots")) {
        slots.add(slot.asText());
      }
    }
    side.setSlots(slots);

    List<Action> grantsAction = new ArrayList<>();
    List<Force> grantsForce = new ArrayList<>();
    List<ShipStat> grantsStats = new ArrayList<>();
    List<Upgrade.Type> grantsSlots = new ArrayList<>();
    if (root.has("grants")) {
      for (JsonNode grant : root.get("grants")) {
        String type = grant.get("type").asText().toLowerCase();
        switch (type) {
          case "action":
            Action action = mapper.treeToValue(grant.get("value"), Action.class);
            grantsAction.add(action);
            break;
          case "force":
            Force force = mapper.treeToValue(grant.get("value"), Force.class);
            grantsForce.add(force);
            break;
          case "stat":
            ShipStat shipStat = new ShipStat();
            shipStat.setName(grant.get("value").asText());
            shipStat.setType(grant.get("value").asText());
            shipStat.setValue(grant.get("amount").asInt());
            shipStat.setArc(grant.path("arc").asText());
            grantsStats.add(shipStat);
            break;
          case "slot":
            String slotType = grant.get("value").asText();
            int count = grant.get("amount").asInt();
            for (int i = 0; i < count; i++) {
              grantsSlots.add(Upgrade.Type.valueOf(slotType.toUpperCase().replace(" ", "_")));
            }
            break;
          default:
            log.warn("Unexpected grants option: {}", type);
        }
      }
    }
    side.setGrantsActions(grantsAction);
    side.setGrantsForce(grantsForce);
    side.setGrantsStats(grantsStats);
    side.setGrantsSlots(grantsSlots);

    side.setImage(URI.create(root.path("image").asText()));
    side.setArtwork(URI.create(root.path("artwork").asText()));
    side.setFfg(root.path("ffg").asInt());

    return side;
  }
}

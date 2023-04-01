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
import io.cucumber.java.bs.A;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Deserializes an {@link Upgrade}.
 */
@Slf4j
public class UpgradeDeserializer extends JsonDeserializer<Upgrade> {

  private final ObjectMapper mapper;

  public UpgradeDeserializer() {
    mapper = new UncheckedObjectMapper();
  }

  @Override
  public Upgrade deserialize(JsonParser jsonParser, DeserializationContext context)
      throws IOException {
    JsonNode root = jsonParser.readValueAsTree();
    Upgrade upgrade = new Upgrade();
    log.info("Deserializing Upgrade: {}", root.get("name").asText());
    upgrade.setName(root.get("name").asText());
    upgrade.setXws(root.get("xws").asText());
    upgrade.setFfg(root.path("ffg").asInt(-1));

    List<Upgrade.Side> sides = new ArrayList<>();
    if (root.has("sides")) {
      for (JsonNode sideNode : root.get("sides")) {
        Upgrade.Side side = mapper.treeToValue(sideNode, Upgrade.Side.class);
        sides.add(side);
      }
    }
    upgrade.setSides(sides);

    Upgrade.Cost cost = mapper.convertValue(root.path("cost"), Upgrade.Cost.class);
    upgrade.setCost(cost);

    List<Upgrade.Restriction> restrictions = new ArrayList<>();
    for (JsonNode r : root.path("restrictions")) {
      restrictions.add(mapper.treeToValue(r, Upgrade.Restriction.class));
    }
    upgrade.setRestrictions(restrictions);

    upgrade.setStandard(root.get("standard").asBoolean());
    upgrade.setExtended(root.get("extended").asBoolean());
    upgrade.setEpic(root.get("epic").asBoolean());

    return upgrade;
  }
}

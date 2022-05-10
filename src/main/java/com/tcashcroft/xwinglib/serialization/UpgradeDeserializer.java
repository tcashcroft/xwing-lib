package com.tcashcroft.xwinglib.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcashcroft.xwinglib.model.Upgrade;
import edu.byu.hbll.json.UncheckedObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UpgradeDeserializer extends JsonDeserializer<Upgrade> {

  private final ObjectMapper mapper;

  public UpgradeDeserializer() {
    mapper = new UncheckedObjectMapper();
  }

  @Override
  public Upgrade deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
    JsonNode root = jsonParser.readValueAsTree();
    System.out.println(root.toString());
    Upgrade upgrade = new Upgrade();
    upgrade.setName(root.get("name").asText());
    upgrade.setXws(root.get("xws").asText());
    upgrade.setFfg(root.path("ffg").asInt(-1));

    List<Upgrade.Side> sides = new ArrayList<>();
    sides = mapper.convertValue(root.path("sides"), sides.getClass());
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

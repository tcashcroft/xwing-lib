package com.tcashcroft.xwinglib.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcashcroft.xwinglib.model.*;
import edu.byu.hbll.json.UncheckedObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShipDeserializer extends JsonDeserializer<Ship> {

  @Override
  public Ship deserialize(JsonParser jsonParser, DeserializationContext context)
      throws IOException {
    JsonNode root = jsonParser.readValueAsTree();
    Ship ship = new Ship();
    ship.setName(root.get("name").asText());
    ship.setType(Ship.Type.parse(ship.getName()));
    ship.setXws(root.get("xws").asText());
    ship.setFfg(root.path("ffg").asInt(-1));
    ship.setSize(Utils.Size.parse(root.get("size").asText()));

    ship.setDial(parseDial(root));
    List<String> dialCodes = new ArrayList<>();
    for (JsonNode n : root.path("dialCodes")) {
      dialCodes.add(n.textValue());
    }
    ship.setDialCodes(dialCodes);

    Faction faction = new Faction();
    faction.setType(Faction.Type.parse(root.get("faction").asText()));
    ship.setFaction(faction);
    String iconUriString = root.path("icon").asText();
    ship.setIcon(iconUriString.isBlank() ? null : URI.create(iconUriString));
    List<ShipStat> stats = new ArrayList<>();
    for (JsonNode statNode : root.get("stats")) {
      ShipStat stat = new ShipStat();
      stat.setType(statNode.get("type").asText());
      stat.setValue(statNode.get("value").asInt());
      stat.setArc(statNode.path("arc").asText(""));

      try {
        Integer i = Integer.parseInt(statNode.path("recovers").asText());
        stat.setRecovers(i);
      } catch (NumberFormatException e) {
        stat.setRecovers(null);
      }

      stats.add(stat);
    }
    ship.setStats(stats);

    List<Action> shipActions = new ArrayList<>();
    for (JsonNode actionNode : root.get("actions")) {
      Action action = new Action();
      action.setDifficulty(Utils.Difficulty.parse(actionNode.get("difficulty").asText()));
      action.setType(Action.Type.parse(actionNode.get("type").asText()));
      if (actionNode.has("linked")) {
        JsonNode linkedNode = actionNode.get("linked");
        Action linkedAction = new Action();
        linkedAction.setDifficulty(Utils.Difficulty.parse(linkedNode.get("difficulty").asText()));
        linkedAction.setType(Action.Type.parse(linkedNode.get("type").asText()));
        action.setLinkedAction(linkedAction);
      }
      shipActions.add(action);
    }
    ship.setActions(shipActions);

    List<Pilot> pilots = new ArrayList<>();
    ObjectMapper mapper = new UncheckedObjectMapper();
    for (JsonNode shipNode : root.get("pilots")) {
      Pilot pilot = mapper.treeToValue(shipNode, Pilot.class);
      pilot.setShip(ship);
      pilots.add(pilot);
    }
    ship.setPilots(pilots);

    return ship;
  }

  private Map<String, Maneuver> parseDial(JsonNode root) {
    Map<String, Maneuver> maneuverMap = new HashMap<>();
    for (JsonNode n : root.path("dial")) {
      String maneuverCode = n.asText();
      String maneuverSpeed = maneuverCode.substring(0, 1);
      String maneuverChar = maneuverCode.substring(1, 2);
      String maneuverColor = maneuverCode.substring(2, 3);
      Maneuver maneuver = new Maneuver();

      maneuver.setManeuverId(maneuverCode);
      Maneuver.Speed speed = Maneuver.Speed.parse(maneuverSpeed);
      maneuver.setSpeed(speed);

      Utils.Difficulty difficulty;
      switch (maneuverColor) {
        case "R":
          difficulty = Utils.Difficulty.RED;
          break;
        case "W":
          difficulty = Utils.Difficulty.WHITE;
          break;
        case "B":
          difficulty = Utils.Difficulty.BLUE;
          break;
        case "P":
          difficulty = Utils.Difficulty.PURPLE;
          break;
        default:
          difficulty = Utils.Difficulty.WHITE;
      }
      maneuver.setDifficulty(difficulty);

      Maneuver.Direction maneuverDirection;
      Maneuver.Type maneuverType;
      boolean isAdvanced = false;

      switch (maneuverChar) {
        case "A":
          maneuverType = Maneuver.Type.REVERSE_BANK;
          maneuverDirection = Maneuver.Direction.LEFT;
          isAdvanced = true;
          break;
        case "B":
          maneuverType = Maneuver.Type.BANK;
          maneuverDirection = Maneuver.Direction.LEFT;
          break;
        case "D":
          maneuverType = Maneuver.Type.REVERSE_BANK;
          maneuverDirection = Maneuver.Direction.RIGHT;
          isAdvanced = true;
          break;
        case "E":
          maneuverType = Maneuver.Type.TALLON_ROLL;
          maneuverDirection = Maneuver.Direction.LEFT;
          isAdvanced = true;
          break;
        case "F":
          maneuverType = Maneuver.Type.STRAIGHT;
          maneuverDirection = Maneuver.Direction.STRAIGHT;
          break;
        case "K":
          maneuverType = Maneuver.Type.KOIOGRAN_TURN;
          maneuverDirection = Maneuver.Direction.STRAIGHT;
          isAdvanced = true;
          break;
        case "L":
          maneuverType = Maneuver.Type.SEGNORS_LOOP;
          maneuverDirection = Maneuver.Direction.LEFT;
          isAdvanced = true;
          break;
        case "N":
          maneuverType = Maneuver.Type.BANK;
          maneuverDirection = Maneuver.Direction.RIGHT;
          break;
        case "O":
          maneuverType = Maneuver.Type.STOP;
          maneuverDirection = Maneuver.Direction.STRAIGHT;
          isAdvanced = true;
          break;
        case "P":
          maneuverType = Maneuver.Type.SEGNORS_LOOP;
          maneuverDirection = Maneuver.Direction.RIGHT;
          isAdvanced = true;
          break;
        case "R":
          maneuverType = Maneuver.Type.TALLON_ROLL;
          maneuverDirection = Maneuver.Direction.RIGHT;
          isAdvanced = false;
          break;
        case "S":
          maneuverType = Maneuver.Type.REVERSE_STRAIGHT;
          maneuverDirection = Maneuver.Direction.STRAIGHT;
          isAdvanced = false;
          break;
        case "T":
          maneuverType = Maneuver.Type.TURN;
          maneuverDirection = Maneuver.Direction.LEFT;
          break;
        case "Y":
          maneuverType = Maneuver.Type.TURN;
          maneuverDirection = Maneuver.Direction.RIGHT;
          break;
        default:
          maneuverType = Maneuver.Type.STRAIGHT;
          maneuverDirection = Maneuver.Direction.STRAIGHT;
      }
      maneuver.setType(maneuverType);
      maneuver.setDirection(maneuverDirection);
      maneuver.setAdvanced(isAdvanced);
      maneuverMap.put(maneuverCode, maneuver);
    }
    return maneuverMap;
  }
}

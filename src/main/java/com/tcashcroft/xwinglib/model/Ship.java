package com.tcashcroft.xwinglib.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tcashcroft.xwinglib.serialization.ShipDeserializer;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Models a ship card.
 */
@Data
@JsonDeserialize(using = ShipDeserializer.class)
@EqualsAndHashCode
public class Ship {
  private String name;
  private Type type;
  private String xws;
  private Integer ffg;
  private Utils.Size size;
  private Map<String, Maneuver> dial;
  private List<String> dialCodes;
  private Faction faction;
  private List<ShipStat> stats;
  private List<Action> actions;
  private URI icon;
  private List<Pilot> pilots;

  /**
   * The ship type (aka chassis).
   */
  public enum Type {
    GOZANTI_CLASS_CRUISER("Gozanti-class Cruiser"),
    RAIDER_CLASS_CORVETTE("Raider-class Corvette"),
    TIE_BA_INTERCEPTOR("TIE/ba Interceptor"),
    TIE_FO_FIGHTER("TIE/fo Fighter"),
    TIE_SE_BOMBER("TIE/se Bomber"),
    TIE_SF_FIGHTER("TIE/sf Fighter"),
    TIE_VN_SILENCER("TIE/vn Silencer"),
    TIE_WI_WHISPER_MODIFIED_INTERCEPTOR("TIE/wi Whisper"),
    UPSILON_CLASS_COMMAND_SHUTTLE("Upsilon-class Command Shuttle"),
    XI_CLASS_LIGHT_SHUTTLE("Xi-class Light Shuttle"),
    ALPHA_CLASS_STAR_WING("Alpha-class Star Wing"),
    GAUNTLET_FIGHTER("Gauntlet Fighter"),
    LAMBDA_CLASS_T_4A_SHUTTLE("Lambda-class T-4a Shuttle"),
    TIE_ADVANCED_V1("TIE Advanced v1"),
    TIE_ADVANCED_X1("TIE Advanced x1"),
    TIE_AG_AGGRESSOR("TIE/ag Aggressor"),
    TIE_CA_PUNISHER("TIE/ca Punisher"),
    TIE_D_DEFENDER("TIE/D Defender"),
    TIE_IN_INTERCEPTOR("TIE/in Interceptor"),
    TIE_LN_FIGHTER("TIE/ln Fighter"),
    TIE_PH_PHANTOM("TIE/ph Phantom"),
    TIE_RB_HEAVY("TIE/rb Heavy"),
    TIE_REAPER("TIE Reaper"),
    TIE_SA_BOMBER("TIE/sa Bomber"),
    TIE_SK_STRIKER("TIE/sk Striker"),
    VT_49_DECIMATOR("VT-49 Decimator"),
    ARC_170_STARFIGHTER("ARC-170 Starfighter"),
    BTL_B_Y_WING("BTL-B Y-Wing"),
    CR90_CORELLIAN_CORVETTE("CR90 Corellian Corvette"),
    DELTA_7_AETHERSPRITE("Delta-7 Aethersprite"),
    DELTA_7B_AETHERSPRITE("Delta-7b Aethersprite"),
    ETA_2_ACTIS("ETA-2 Actis"),
    LAAT_I_GUNSHIP("LAAT/i Gunship"),
    NABOO_ROYAL_N_1_STARFIGHTER("Naboo Royal N-1 Starfighter"),
    NIMBUS_CLASS_V_WING("Numbus-class V-Wing"),
    V_19_TORRENT_STARFIGHTER("V-19 Torrent Starfighter"),
    A_SF_01_B_WING("A/SF-01 B-Wing"),
    ATTACK_SHUTTLE("Attack Shuttle"),
    AUZITUCK_GUNSHIP("Auzituck Gunship"),
    BTL_A4_Y_WING("BTL-A4 Y-Wing"),
    BTL_S8_K_WING("BTL-S8 K-Wing"),
    E_WING("E-Wing"),
    FANG_FIGHTER("Fang Fighter"),
    GR_75_MEDIUM_TRANSPORT("GR-75 Medium Transport"),
    HWK_290_LIGHT_FREIGHTER("HWK-290 Light Freighter"),
    MODIFIED_YT_1300_LIGHT_FREIGHTER("Modified YT-1300 Light Freighter"),
    RZ_1_A_WING("RZ-1 A-Wing"),
    SHEATHIPEDE_CLASS_SHUTTLE("Sheathipede-class Shuttle"),
    T_65_X_WING("T-65 X-Wing"),
    UT_60D_U_WING("UT-60D U-Wing"),
    VCX_100_LIGHT_FREIGHTER("VCX-100 Light Freighter"),
    YT_2400_LIGHT_FREIGHTER("YT-2400 Light Freighter"),
    Z_95_AF4_HEADHUNTER("Z-95-AF4 Headhunter"),
    BTA_NR2_Y_WING("BTA-NR2 Y-Wing"),
    FIREBALL("Fireball"),
    MG_100_STARFORTRESS("MG-100 StarFortress"),
    RESISTANCE_TRANSPORT("Resistance Transport"),
    RESISTANCE_TRANSPORT_POD("Resistance Transport Pod"),
    RZ_2_A_WING("RZ-2 A-Wing"),
    SCAVENGED_YT_1300("Scavenged YT-1300"),
    T_70_X_WING("T-70 X-Wing"),
    AGGRESSOR_ASSAULT_FIGHTER("Aggressor Assault Fighter"),
    C_ROC_CRUISER("C-ROC Cruiser"),
    CUSTOMIZED_YT_1300_LIGHT_FREIGHTER("Customized YT-1300 Light Freighter"),
    ESCAPE_CRAFT("Escape Craft"),
    FIRESPRAY_CLASS_PATROL_CRAFT("Firespray-class Patrol Craft"),
    G_1A_STARFIGHTER("G-1A Starfighter"),
    JUMPMASTER_5000("JumpMaster 5000"),
    KIHRAXZ_FIGHTER("Kihraxz Fighter"),
    LANCER_CLASS_PURSUIT_CRAFT("Lancer-class Pursuit Craft"),
    M3_A_INTERCEPTOR("M3-A Interceptor"),
    M12_L_KIMOGILA_FIGHTER("M12-L Kimogila Fighter"),
    MODIFIED_TIE_LN_FIGHTER("Modified TIE/ln Fighter"),
    QUADRIJET_TRANSFER_SPACETUG("Quadrijet Transfer Spacetug"),
    SCURRG_H_6_BOMBER("Scurrg H-6 Bomber"),
    ST_70_ASSAULT_SHIP("ST-70 Assault Ship"),
    STARVIPER_CLASS_ATTACK_PLATFORM("StarViper-class Attack Platform"),
    TRIDENT_CLASS_ASSAULT_SHIP("Trident-class Assault Ship"),
    YV_666_LIGHT_FREIGHTER("YV-666 Light Freighter"),
    BELBULLAB_22_STARFIGHTER("Belbullab-22 Starfighter"),
    DROID_TRI_FIGHTER("Droid Tri-Fighter"),
    HMP_DROID_GUNSHIP("HMP Droid Gunship"),
    HYENA_CLASS_DROID_BOMBER("Hyena-class Droid Bomber"),
    NANTEX_CLASS_STARFIGHTER("Nantex-class Starfighter"),
    SITH_INFILTRATOR("Sith Infiltrator"),
    VULTURE_CLASS_DROID_FIGHTER("Vulture-class Droid Fighter"),
    ROGUE_CLASS_STARFIGHTER("Rogue-class Starfighter"),
    SYLIURE_CLASS_HYPERSPACE_RING("Syliure-class Hyperspace Ring"),
    CLONE_Z_95_HEADHUNTER("Clone Z-95 Headhunter");

    private final String value;

    public String getValue() {
      return this.value;
    }

    Type(String value) {
      this.value = value;
    }

    private static final List<String> charsToReplace = Arrays.asList(" ", "-", "/");

    /**
     * Parses a ship type from a string by sanitizing an input string prior to calling valueOf.
     *
     * @param value the string value of the ship type
     * @return a Type
     */
    public static Type parse(String value) {
      String sanitizedString = value;
      for (String toReplace : charsToReplace) {
        sanitizedString = sanitizedString.replaceAll(toReplace, "_");
      }
      return Type.valueOf(sanitizedString.toUpperCase());
    }
  }
}

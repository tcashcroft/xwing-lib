package com.tcashcroft.xwinglib.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tcashcroft.xwinglib.serialization.ShipDeserializer;
import lombok.Data;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Data
@JsonDeserialize(using = ShipDeserializer.class)
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

  public enum Type {
    GOZANTI_CLASS_CRUISER,
    RAIDER_CLASS_CORVETTE,
    TIE_BA_INTERCEPTOR,
    TIE_FO_FIGHTER,
    TIE_SE_BOMBER,
    TIE_SF_FIGHTER,
    TIE_VN_SILENCER,
    TIE_WI_WHISPER_MODIFIED_INTERCEPTOR,
    UPSILON_CLASS_COMMAND_SHUTTLE,
    XI_CLASS_LIGHT_SHUTTLE,
    ALPHA_CLASS_STAR_WING,
    GAUNTLET_FIGHTER,
    LAMBDA_CLASS_T_4A_SHUTTLE,
    TIE_ADVANCED_V1,
    TIE_ADVANCED_X1,
    TIE_AG_AGGRESSOR,
    TIE_CA_PUNISHER,
    TIE_D_DEFENDER,
    TIE_IN_INTERCEPTOR,
    TIE_LN_FIGHTER,
    TIE_PH_PHANTOM,
    TIE_RB_HEAVY,
    TIE_REAPER,
    TIE_SA_BOMBER,
    TIE_SK_STRIKER,
    VT_49_DECIMATOR,
    ARC_170_STARFIGHTER,
    BTL_B_Y_WING,
    CR90_CORELLIAN_CORVETTE,
    DELTA_7_AETHERSPRITE,
    DELTA_7B_AETHERSPRITE,
    ETA_2_ACTIS,
    LAAT_I_GUNSHIP,
    NABOO_ROYAL_N_1_STARFIGHTER,
    NIMBUS_CLASS_V_WING,
    V_19_TORRENT_STARFIGHTER,
    A_SF_01_B_WING,
    ATTACK_SHUTTLE,
    AUZITUCK_GUNSHIP,
    BTL_A4_Y_WING,
    BTL_S8_K_WING,
    E_WING,
    FANG_FIGHTER,
    GR_75_MEDIUM_TRANSPORT,
    HWK_290_LIGHT_FREIGHTER,
    MODIFIED_YT_1300_LIGHT_FREIGHTER,
    RZ_1_A_WING,
    SHEATHIPEDE_CLASS_SHUTTLE,
    T_65_X_WING,
    UT_60D_U_WING,
    VCX_100_LIGHT_FREIGHTER,
    YT_2400_LIGHT_FREIGHTER,
    Z_95_AF4_HEADHUNTER,
    BTA_NR2_Y_WING,
    FIREBALL,
    MG_100_STARFORTRESS,
    RESISTANCE_TRANSPORT,
    RESISTANCE_TRANSPORT_POD,
    RZ_2_A_WING,
    SCAVENGED_YT_1300,
    T_70_X_WING,
    AGGRESSOR_ASSAULT_FIGHTER,
    C_ROC_CRUISER,
    CUSTOMIZED_YT_1300_LIGHT_FREIGHTER,
    ESCAPE_CRAFT,
    FIRESPRAY_CLASS_PATROL_CRAFT,
    G_1A_STARFIGHTER,
    JUMPMASTER_5000,
    KIHRAXZ_FIGHTER,
    LANCER_CLASS_PURSUIT_CRAFT,
    M3_A_INTERCEPTOR,
    M12_L_KIMOGILA_FIGHTER,
    MODIFIED_TIE_LN_FIGHTER,
    QUADRIJET_TRANSFER_SPACETUG,
    SCURRG_H_6_BOMBER,
    ST_70_ASSAULT_SHIP,
    STARVIPER_CLASS_ATTACK_PLATFORM,
    TRIDENT_CLASS_ASSAULT_SHIP,
    YV_666_LIGHT_FREIGHTER,
    BELBULLAB_22_STARFIGHTER,
    DROID_TRI_FIGHTER,
    HMP_DROID_GUNSHIP,
    HYENA_CLASS_DROID_BOMBER,
    NANTEX_CLASS_STARFIGHTER,
    SITH_INFILTRATOR,
    VULTURE_CLASS_DROID_FIGHTER;

    private static final List<String> charsToReplace = Arrays.asList(" ", "-", "/");

    public static Type parse(String value) {
      String sanitizedString = value;
      for (String toReplace : charsToReplace) {
        sanitizedString = sanitizedString.replaceAll(toReplace, "_");
      }
      return Type.valueOf(sanitizedString.toUpperCase());
    }
  }
}

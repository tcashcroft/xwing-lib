
Feature: Faction-level features of the X-Wing library

  Scenario Outline: Get a list of ships in the faction
    Given a shipProducer
    When I ask for the <faction> ship list
    Then I get the <shiplist>
    Examples:
    | faction         | shiplist |
    | "First Order"   | gozanticlasscruiser, raiderclasscorvette, tiebainterceptor, tiefofighter, tiesebomber, tiesffighter, tievnsilencer, tiewiwhispermodifiedinterceptor, upsilonclassshuttle, xiclasslightshuttle |
    | "Galactic Empire" | alphaclassstarwing,  gauntletfighter,  gozanticlasscruiser,  lambdaclasst4ashuttle,  raiderclasscorvette,  tieadvancedv1,  tieadvancedx1,  tieagaggressor,  tiecapunisher,  tieddefender,  tieininterceptor,  tielnfighter,  tiephphantom,  tierbheavy,  tiereaper,  tiesabomber,  tieskstriker,  vt49decimator |
    | "Galactic Republic" | arc170starfighter, btlbywing, clonez95headhunter, cr90corelliancorvette, delta7aethersprite, delta7baethersprite, eta2actis, gauntletfighter, laatigunship, nabooroyaln1starfighter, nimbusclassvwing, v19torrentstarfighter |
    | "Rebel Alliance" | asf01bwing, arc170starfighter, modifiedyt1300lightfreighter, attackshuttle, auzituckgunship, btla4ywing, btls8kwing, cr90corelliancorvette, ewing, fangfighter, gauntletfighter, gr75mediumtransport, hwk290lightfreighter, rz1awing, sheathipedeclassshuttle, t65xwing, tielnfighter, ut60duwing, vcx100lightfreighter, yt2400lightfreighter, z95af4headhunter |
    | "Resistance" | btanr2ywing, fireball, gr75mediumtransport, mg100starfortress, resistancetransport, resistancetransportpod, rz2awing, scavengedyt1300, t70xwing |
    | "Scum and Villainy" | aggressorassaultfighter, btla4ywing, croccruiser, customizedyt1300lightfreighter, escapecraft, fangfighter, firesprayclasspatrolcraft, g1astarfighter, gauntletfighter, hwk290lightfreighter, jumpmaster5000, kihraxzfighter, lancerclasspursuitcraft, m3ainterceptor, m12lkimogilafighter, modifiedtielnfighter, quadrijettransferspacetug, rogueclassstarfighter, scurrgh6bomber, st70assaultship, starviperclassattackplatform, tridentclassassaultship, yv666lightfreighter, z95af4headhunter |
    | "Separatist Alliance" | belbullab22starfighter, croccruiser, droidtrifighter, firesprayclasspatrolcraft, gauntletfighter, hmpdroidgunship, hyenaclassdroidbomber, nantexclassstarfighter, rogueclassstarfighter, sithinfiltrator, tridentclassassaultship, vultureclassdroidfighter |


  Scenario Outline: Get a list of upgrades in the faction
    Given a shipProducer
    When I ask for the <faction>'s upgrade list
    Then I get <upgrades>, not including faction-agnostic upgrades
    Examples:
    | faction | upgrades |
    | "First Order" | biohexacryptcodes, captainphasma, dt798, fanatical, firstorderordnancetech, generalhux, hyperspacetrackingdata, kyloren, pettyofficerthanisson, protectorategleb, specialforcesgunner, supremeleadersnoke |
    | "Galactic Empire" | admiralsloane, agentkallus, assailer, bloodcrow, captainhark, cienaree, corvus, darthvader, dauntless, deathtroopers, directorkrennic, emperorpalpatine, fifthbrother, garsaxon, grandinquisitor, grandmofftarkin, imperialsupercommandos, impetuous, instigator, isbslicer, migsmayfeld, ministertua, moffjerjerrod, protectorategleb, requiem, ruthless, seventhsister, st321, suppressor, thechild, tibersaxon, tiedefenderelite, vector |
    | "Galactic Republic" | ahsokatano-crew, battlemeditation, bokatankryze, c110p, chancellorpalpatine, clonecommandercody, dedicated, gauntlet, jedicommander, korkiekryze, niteowlcommandos, obiwankenobi, primeministeralmec, r2a6, r2c4, r2d2-republic, r4p17, r4p44, r4pastromech, r7a7, satinekryze, seventhfleetgunner, synchronizedconsole, ursawren-gunner |
    | "Rebel Alliance" | b6bladewingprototype, b6bladewingprototype-command, bazemalbus, bistan, bokatankryze-rebel-scum, brighthope, c3po, cassianandor, chewbacca, chopper, chopper-crew, clanwrencommandos, dodonnaspride, ezrabridger, fennrau, ghost, hansolo, herasyndulla, jainaslight, jynerso, kananjarrus, landocalrissian, leiaorgana, liberator, lukeskywalker, luminous, magvayarro, millenniumfalcon, moldycrow, niennunb, nightbrother, outrider, phantom, quantumstorm, r2d2, r2d2-crew, r5d8, sabinewren, sawgerrera, selfless, tantiveiv, thechild, thunderstrike, tristanwren, ursawren, zeborrelios |
    | "Resistance" | amilynholdo, babufrik, bb8, bbastromech, blackone, c3po-crew, chewbacca-crew-swz19, ferrospherepaint, finn, ga97, hansolo-crew, heroic, kaydelconnix, kazsfireball, korrsella, l4er5, larmadacy, leiaorgana-resistance, m9g8, paigetico, pz4co, r1j5, r2d2-resistance, r2ha, r5x3, r6d8, rey-gunner, reysmillenniumfalcon, rosetico |
    | "Scum and Villainy" | 000, 4lom, ahsokatano-crew, andrasta, asajjventresscommand, babufrik, bobafett, bokatankryze-rebel-scum, bossk, brokenhorn, bt1, burnoutthrusters, cadbane, chewbacca-crew, cikatrovizago, corsaircrew, dengar, fearless, fennrau, gamutkey, garsaxon-gunner, genius, greedo, greefkarga, hansolo-gunner, havoc, houndstooth, ig11, ig2000, ig88d, initforthemoneyrebellion, insatiableworrt, jabbathehutt, jangofett, ketsuonyo, kuiil, l337, landocalrissian-crew, landosmillenniumfalcon, lattsrazzi, mandaloriansupercommandos, marauder, maul, maul-crew, merchantone, migsmayfeld, misthunter, moldycrow, nautolansrevenge, nightbrother, pelimotto, previzsla, primeministeralmec, protectorategleb, punishingone, qira, r4b11, r5p8, r5tk, rookkast, savageopress, shadowcaster, slavei, slavei-swz82, thechild, themandalorian, tobiasbeckett, unkarplutt, zamwesell, zuckuss |
    | "Separatist Alliance" | asajjventresscommand, bokatankryze, chancellorpalpatine, countdooku, deathwatchcommandos, discordmissiles, drk1probedroids, droidcrew, energyshellcharges, gauntlet, generalgrievous, generalgrievouscommand, jangofett, k2b4, kraken, martuuk, neimoidiangrasp, previzsla, rifftamson, savageopress, scimitar, slavei-swz82, soullessone, ta175, talmerrik, treacherous, trident, tv94, ursawren-gunner, zamwesell |

  Scenario Outline: Get a list of pilots with the same chassis
    Given a shipProducer
    When I ask for all pilots of a <chassis>
    Then I get a <pilotList>
    Examples:
    | chassis | pilotList |
    | "ARC_170_STARFIGHTER" | sinker, jag, oddball-arc170starfighter, wolffe, 104thbattalionpilot, squadsevenveteran, garvendreis, ibtisam, norrawexley, sharabey |

  Scenario Outline: Get a list of pilots with the same chassis and faction
    Given a shipProducer
    When I ask for all the pilots of a <chassis>
    And they share a <faction>
    Then I get faction-specific <factionPilotList>
    Examples:
    | chassis | faction | factionPilotList |
    | "ARC_170_STARFIGHTER" | "rebelalliance" | garvendreis, ibtisam, norrawexley, sharabey |
    | "ARC_170_STARFIGHTER" | "galacticrepublic" | sinker, jag, oddball-arc170starfighter, wolffe, 104thbattalionpilot, squadsevenveteran |

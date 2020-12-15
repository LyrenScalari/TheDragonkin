package theDragonkin;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theDragonkin.characters.TheDefault;
import theDragonkin.characters.TheGremory;
import theDragonkin.characters.TheGroveKeeper;
import theDragonkin.potions.Dragonkin.DragonkinCommonPotion;
import theDragonkin.potions.Dragonkin.DragonkinRarePotion;
import theDragonkin.potions.Dragonkin.DragonkinUncommonPotion;
import theDragonkin.potions.Gremory.MagicHerbTea;
import theDragonkin.potions.Gremory.ShadowofZahras;
import theDragonkin.potions.Gremory.SpringWater;
import theDragonkin.powers.Dragonkin.Scorchpower;
import theDragonkin.powers.Gremory.FlowersAmbition;
import theDragonkin.powers.Gremory.ImmaculateSnow;
import theDragonkin.powers.Gremory.MoonsMarch;
import theDragonkin.powers.Gremory.WindsSong;
import theDragonkin.relics.Dragonkin.*;
import theDragonkin.relics.Gremory.HeartofFlames;
import theDragonkin.util.IDCheckDontTouchPls;
import theDragonkin.util.TextureLoader;
import theDragonkin.variables.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import static theDragonkin.characters.TheDefault.Enums.Dragonkin_Red_COLOR;
import static theDragonkin.characters.TheGremory.Enums.Gremory_Purple_Color;
import static theDragonkin.characters.TheGroveKeeper.Enums.GroveKeeper_Forest_Color;

//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
// Please don't just mass replace "theDefault" with "yourMod" everywhere.
// It'll be a bigger pain for you. You only need to replace it in 3 places.
// I comment those places below, under the place where you set your ID.

//TODO: FIRST THINGS FIRST: RENAME YOUR PACKAGE AND ID NAMES FIRST-THING!!!
// Right click the package (Open the project pane on the left. Folder with black dot on it. The name's at the very top) -> Refactor -> Rename, and name it whatever you wanna call your mod.
// Scroll down in this file. Change the ID from "theDefault:" to "yourModName:" or whatever your heart desires (don't use spaces). Dw, you'll see it.
// In the JSON strings (resources>localization>eng>[all them files] make sure they all go "yourModName:" rather than "theDefault". You can ctrl+R to replace in 1 file, or ctrl+shift+r to mass replace in specific files/directories (Be careful.).
// Start with the DefaultCommon cards - they are the most commented cards since I don't feel it's necessary to put identical comments on every card.
// After you sorta get the hang of how to make cards, check out the card template which will make your life easier

/*
 * With that out of the way:
 * Welcome to this super over-commented Slay the Spire modding base.
 * Use it to make your own mod of any type. - If you want to add any standard in-game content (character,
 * cards, relics), this is a good starting point.
 * It features 1 character with a minimal set of things: 1 card of each type, 1 debuff, couple of relics, etc.
 * If you're new to modding, you basically *need* the BaseMod wiki for whatever you wish to add
 * https://github.com/daviscook477/BaseMod/wiki - work your way through with this base.
 * Feel free to use this in any way you like, of course. MIT licence applies. Happy modding!
 *
 * And pls. Read the comments.
 */

@SpireInitializer
public class DefaultMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber {
    // Make sure to implement the subscribers *you* are using (read basemod wiki). Editing cards? EditCardsSubscriber.
    // Making relics? EditRelicsSubscriber. etc., etc., for a full list and how to make your own, visit the basemod wiki.
    public static final Logger logger = LogManager.getLogger(DefaultMod.class.getName());
    private static String modID;

    // Mod-settings settings. This is if you want an on/off savable button
    public static Properties theDefaultDefaultSettings = new Properties();
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    public static boolean enablePlaceholder = true; // The boolean we'll be setting on/off (true/false)
    public static boolean ObsidianMight = false;

    //This is for the in-game mod settings panel.
    private static final String MODNAME = "The Dragonkin";
    private static final String AUTHOR = "Lyren"; // And pretty soon - You!
    private static final String DESCRIPTION = "A Warrior of Fire and Faith, Determined to purge the evil of the Spire.";
    
    // =============== INPUT TEXTURE LOCATION =================
    
    // Colors (RGB)
    // Character Color
    public static final Color DEFAULT_GRAY = CardHelper.getColor(209.0f, 53.0f, 18.0f);
    public static final Color GREMORY_PURPLE = CardHelper.getColor(150.0f, 19.0f, 186.0f);
    public static final Color GROVEKEEPER_FOREST = CardHelper.getColor(00.0f, 173.0f, 17.0f);
    
    // Potion Colors in RGB
    public static final Color PLACEHOLDER_POTION_LIQUID = CardHelper.getColor(209.0f, 53.0f, 18.0f); // Orange-ish Red
    public static final Color PLACEHOLDER_POTION_HYBRID = CardHelper.getColor(255.0f, 230.0f, 230.0f); // Near White
    public static final Color PLACEHOLDER_POTION_SPOTS = CardHelper.getColor(100.0f, 25.0f, 10.0f); // Super Dark Red/Brown
    
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
  
    // Card backgrounds - The actual rectangular card

    //Grovekeeper
    public static final String Neutral_LARGE_ORB = "theDragonkinResources/images/1024/GroveKeeperOrb_N.png";
    public static final String Neutral_SMALL_ORB = "theDragonkinResources/images/512/GroveKeeperOrb_N.png";
    public static final String Solar_LARGE_ORB = "theDragonkinResources/images/1024/GroveKeeperOrb_S.png";
    public static final String Solar_SMALL_ORB = "theDragonkinResources/images/512/GroveKeeperOrb_S.png";
    public static final String Lunar_LARGE_ORB = "theDragonkinResources/images/1024/GroveKeeperOrb_M.png";
    public static final String Lunar_SMALL_ORB = "theDragonkinResources/images/512/GroveKeeperOrb_M.png";
    private static final String ATTACK_FOREST = "theDragonkinResources/images/512/GKBG_Attack.png";
    private static final String SKILL_FOREST = "theDragonkinResources/images/512/GKBG_Skill.png";
    private static final String POWER_FOREST = "theDragonkinResources/images/512/GKBG_Power.png";

    private static final String ENERGY_ORB_FOREST = "theDragonkinResources/images/512/GroveKeeperOrb_N.png";
    private static final String FOREST_ENERGY_ORB = "theDragonkinResources/images/512/forest_small_orb.png";

    private static final String FOREST_ATTACK_PORTRAIT = "theDragonkinResources/images/1024/GKBG_Attack.png";
    private static final String FOREST_SKILL_PORTRAIT = "theDragonkinResources/images/1024/GKBG_Skill.png";
    private static final String FOREST_POWER_GRAY_PORTRAIT = "theDragonkinResources/images/1024/GKBG_Power.png";
    private static final String ENERGY_ORB_FOREST_PORTRAIT = "theDragonkinResources/images/1024/GroveKeeperOrb_N.png";

    // Dragonkin

    public static final String HOLY_LARGE_ORB = "theDragonkinResources/images/1024/card_Dragonkin_Holy_orb.png";
    public static final String HOLY_SMALL_ORB = "theDragonkinResources/images/512/card_Dragonkin_Holy_orb.png";
    private static final String ATTACK_DEFAULT_GRAY = "theDragonkinResources/images/512/bg_attack_default_gray.png";
    private static final String SKILL_DEFAULT_GRAY = "theDragonkinResources/images/512/bg_skill_default_gray.png";
    private static final String POWER_DEFAULT_GRAY = "theDragonkinResources/images/512/bg_power_default_gray.png";

    private static final String ENERGY_ORB_DEFAULT_GRAY = "theDragonkinResources/images/512/card_default_gray_orb.png";
    private static final String CARD_ENERGY_ORB = "theDragonkinResources/images/512/card_small_orb.png";
    
    private static final String ATTACK_DEFAULT_GRAY_PORTRAIT = "theDragonkinResources/images/1024/bg_attack_default_gray.png";
    private static final String SKILL_DEFAULT_GRAY_PORTRAIT = "theDragonkinResources/images/1024/bg_skill_default_gray.png";
    private static final String POWER_DEFAULT_GRAY_PORTRAIT = "theDragonkinResources/images/1024/bg_power_default_gray.png";
    private static final String ENERGY_ORB_DEFAULT_GRAY_PORTRAIT = "theDragonkinResources/images/1024/card_default_gray_orb.png";


    // Gremory
    private static final String ATTACK_GREMORY_PURPLE = "theDragonkinResources/images/512/bg_attack_gremory.png";
    private static final String SKILL_GREMORY_PURPLE = "theDragonkinResources/images/512/bg_skill_gremory.png";
    private static final String POWER_GREMORY_PURPLE = "theDragonkinResources/images/512/bg_power_gremory.png";

    private static final String ENERGY_ORB_GREMORY_PURPLE = "theDragonkinResources/images/512/card_gremory_purple_orb.png";
    private static final String GREMORY_CARD_ENERGY_ORB = "theDragonkinResources/images/512/card_gremory_orb.png";

    private static final String ATTACK_GREMORY_PURPLE_PORTRAIT = "theDragonkinResources/images/1024/bg_attack_gremory.png";
    private static final String SKILL_GREMORY_PURPLE_PORTRAIT = "theDragonkinResources/images/1024/bg_skill_gremory.png";
    private static final String POWER_GREMORY_PURPLE_PORTRAIT = "theDragonkinResources/images/1024/bg_power_gremory.png";
    private static final String ENERGY_ORB_GREMORY_PURPLE_PORTRAIT = "theDragonkinResources/images/1024/gremory_purple_orb.png";
    public static final String Fire_LARGE_ORB = "theDragonkinResources/images/1024/card_Fire_orb.png";
    public static final String Fire_SMALL_ORB = "theDragonkinResources/images/512/card_Fire_orb.png";
    public static final String Ice_LARGE_ORB = "theDragonkinResources/images/1024/card_Ice_orb.png";
    public static final String Ice_SMALL_ORB = "theDragonkinResources/images/512/card_Ice_orb.png";
    public static final String Thunder_LARGE_ORB = "theDragonkinResources/images/1024/card_Thunder_orb.png";
    public static final String Thunder_SMALL_ORB = "theDragonkinResources/images/512/card_Thunder_orb.png";
    public static final String Wind_LARGE_ORB = "theDragonkinResources/images/1024/card_Wind_orb.png";
    public static final String Wind_SMALL_ORB = "theDragonkinResources/images/512/card_Wind_orb.png";
    public static final String Dark_LARGE_ORB = "theDragonkinResources/images/1024/card_Dark_orb.png";
    public static final String Dark_SMALL_ORB = "theDragonkinResources/images/512/card_Dark_orb.png";
    public static final String Light_LARGE_ORB = "theDragonkinResources/images/1024/card_Light_orb.png";
    public static final String Light_SMALL_ORB = "theDragonkinResources/images/512/card_Light_orb.png";

    public static final String SPRING_LARGE_ORB = "theDragonkinResources/images/1024/card_special_flower.png";
    public static final String SPRING_SMALL_ORB = "theDragonkinResources/images/512/card_special_flower.png";
    public static final String SPRING_LARGE_ATTACK_FRAME = "theDragonkinResources/images/1024/bg_attack_special_flower.png";
    public static final String SPRING_LARGE_SKILL_FRAME = "theDragonkinResources/images/1024/bg_skill_special_flower.png";
    public static final String SPRING_LARGE_POWER_FRAME ="theDragonkinResources/images/1024/bg_power_special_flower.png";
    public static final String SPRING_SMALL_ATTACK_FRAME = "theDragonkinResources/images/512/bg_attack_special_flower.png";
    public static final String SPRING_SMALL_SKILL_FRAME = "theDragonkinResources/images/512/bg_skill_special_flower.png";
    public static final String SPRING_SMALL_POWER_FRAME = "theDragonkinResources/images/512/bg_power_special_flower.png";

    public static final String SUMMER_LARGE_ORB = "theDragonkinResources/images/1024/card_Summer_orb.png";
    public static final String SUMMER_SMALL_ORB = "theDragonkinResources/images/512/card_Summer_orb.png";
    public static final String SUMMER_LARGE_ATTACK_FRAME = "theDragonkinResources/images/1024/bg_attack_special_wind.png";
    public static final String SUMMER_LARGE_SKILL_FRAME = "theDragonkinResources/images/1024/bg_skill_special_wind.png";
    public static final String SUMMER_LARGE_POWER_FRAME ="theDragonkinResources/images/1024/bg_power_special_wind.png";
    public static final String SUMMER_SMALL_ATTACK_FRAME = "theDragonkinResources/images/512/bg_attack_special_wind.png";
    public static final String SUMMER_SMALL_SKILL_FRAME = "theDragonkinResources/images/512/bg_skill_special_wind.png";
    public static final String SUMMER_SMALL_POWER_FRAME = "theDragonkinResources/images/512/bg_power_special_wind.png";

    public static final String AUTUMN_LARGE_ORB = "theDragonkinResources/images/1024/card_Autumn_orb.png";
    public static final String AUTUMN_SMALL_ORB = "theDragonkinResources/images/512/card_Autumn_orb.png";
    public static final String AUTUMN_LARGE_ATTACK_FRAME = "theDragonkinResources/images/1024/bg_attack_special_moon.png";
    public static final String AUTUMN_LARGE_SKILL_FRAME = "theDragonkinResources/images/1024/bg_skill_special_moon.png";
    public static final String AUTUMN_LARGE_POWER_FRAME ="theDragonkinResources/images/1024/bg_power_special_moon.png";
    public static final String AUTUMN_SMALL_ATTACK_FRAME = "theDragonkinResources/images/512/bg_attack_special_moon.png";
    public static final String AUTUMN_SMALL_SKILL_FRAME = "theDragonkinResources/images/512/bg_skill_special_moon.png";
    public static final String AUTUMN_SMALL_POWER_FRAME = "theDragonkinResources/images/512/bg_power_special_moon.png";

    public static final String WINTER_LARGE_ORB = "theDragonkinResources/images/1024/card_Winter_orb.png";
    public static final String WINTER_SMALL_ORB = "theDragonkinResources/images/512/card_Winter_orb.png";
    public static final String WINTER_LARGE_ATTACK_FRAME = "theDragonkinResources/images/1024/bg_attack_special_snow.png";
    public static final String WINTER_LARGE_SKILL_FRAME = "theDragonkinResources/images/1024/bg_skill_special_snow.png";
    public static final String WINTER_LARGE_POWER_FRAME ="theDragonkinResources/images/1024/bg_power_special_snow.png";
    public static final String WINTER_SMALL_ATTACK_FRAME = "theDragonkinResources/images/512/bg_attack_special_snow.png";
    public static final String WINTER_SMALL_SKILL_FRAME = "theDragonkinResources/images/512/bg_skill_special_snow.png";
    public static final String WINTER_SMALL_POWER_FRAME = "theDragonkinResources/images/512/bg_power_special_snow.png";

    // Character assets
    public static final String THE_DRAGONKIN_SHOULDER_1 = "theDragonkinResources/images/char/defaultCharacter/Dragonkinshoulder.png";
    public static final String THE_DRAGONKIN_SHOULDER_2 = "theDragonkinResources/images/char/defaultCharacter/Dragonkinshoulder2.png";
    public static final String THE_DRAGONKIN_CORPSE = "theDragonkinResources/images/char/defaultCharacter/DragonkinCorpse.png";
    private static final String THE_DEFAULT_BUTTON = "theDragonkinResources/images/charSelect/DefaultCharacterButton.png";
    private static final String THE_GREMORY_BUTTON = "theDragonkinResources/images/charSelect/GremoryButton.png";
    private static final String THE_GROVEKEEPER_BUTTON = "theDragonkinResources/images/charSelect/ButtonGrove.png";
    private static final String THE_DEFAULT_PORTRAIT = "theDragonkinResources/images/charSelect/DefaultCharacterPortraitBG.png";
    public static final String THE_DEFAULT_SHOULDER_1 = "theDragonkinResources/images/char/defaultCharacter/shoulder.png";
    public static final String THE_DEFAULT_SHOULDER_2 = "theDragonkinResources/images/char/defaultCharacter/shoulder2.png";
    public static final String THE_DEFAULT_CORPSE = "theDragonkinResources/images/char/defaultCharacter/corpse.png";
    
    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = "theDragonkinResources/images/Badge.png";
    
    // Atlas and JSON files for the Animations
    public static final String THE_DEFAULT_SKELETON_ATLAS = "theDragonkinResources/images/char/defaultCharacter/skeleton.atlas";
    public static final String THE_DEFAULT_SKELETON_JSON = "theDragonkinResources/images/char/defaultCharacter/skeleton.json";
    
    // =============== MAKE IMAGE PATHS =================
    
    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }
    
    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }
    
    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }
    
    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/orbs/" + resourcePath;
    }
    
    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }
    
    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }
    
    // =============== /MAKE IMAGE PATHS/ =================
    
    // =============== /INPUT TEXTURE LOCATION/ =================
    
    
    // =============== SUBSCRIBE, CREATE THE Dragonkin_Red_COLOR, INITIALIZE =================
    
    public DefaultMod() {
        logger.info("Subscribe to BaseMod hooks");
        
        BaseMod.subscribe(this);
        
      /*
           (   ( /(  (     ( /( (            (  `   ( /( )\ )    )\ ))\ )
           )\  )\()) )\    )\()))\ )   (     )\))(  )\()|()/(   (()/(()/(
         (((_)((_)((((_)( ((_)\(()/(   )\   ((_)()\((_)\ /(_))   /(_))(_))
         )\___ _((_)\ _ )\ _((_)/(_))_((_)  (_()((_) ((_|_))_  _(_))(_))_
        ((/ __| || (_)_\(_) \| |/ __| __| |  \/  |/ _ \|   \  |_ _||   (_)
         | (__| __ |/ _ \ | .` | (_ | _|  | |\/| | (_) | |) |  | | | |) |
          \___|_||_/_/ \_\|_|\_|\___|___| |_|  |_|\___/|___/  |___||___(_)
      */
      
        setModID("theDragonkin");
        // cool
        // TODO: NOW READ THIS!!!!!!!!!!!!!!!:
        
        // 1. Go to your resources folder in the project panel, and refactor> rename theDefaultResources to
        // yourModIDResources.
        
        // 2. Click on the localization > eng folder and press ctrl+shift+r, then select "Directory" (rather than in Project)
        // replace all instances of theDefault with yourModID.
        // Because your mod ID isn't the default. Your cards (and everything else) should have Your mod id. Not mine.
        
        // 3. FINALLY and most importantly: Scroll up a bit. You may have noticed the image locations above don't use getModID()
        // Change their locations to reflect your actual ID rather than theDefault. They get loaded before getID is a thing.
        
        logger.info("Done subscribing");
        
        logger.info("Creating the color " + Dragonkin_Red_COLOR.toString());
        
        BaseMod.addColor(Dragonkin_Red_COLOR, DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY,
                DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY,
                ATTACK_DEFAULT_GRAY, SKILL_DEFAULT_GRAY, POWER_DEFAULT_GRAY, ENERGY_ORB_DEFAULT_GRAY,
                ATTACK_DEFAULT_GRAY_PORTRAIT, SKILL_DEFAULT_GRAY_PORTRAIT, POWER_DEFAULT_GRAY_PORTRAIT,
                ENERGY_ORB_DEFAULT_GRAY_PORTRAIT, CARD_ENERGY_ORB);

        BaseMod.addColor(Gremory_Purple_Color, GREMORY_PURPLE, GREMORY_PURPLE, GREMORY_PURPLE,
                GREMORY_PURPLE, GREMORY_PURPLE, GREMORY_PURPLE, GREMORY_PURPLE,
                ATTACK_GREMORY_PURPLE, SKILL_GREMORY_PURPLE, POWER_GREMORY_PURPLE, ENERGY_ORB_GREMORY_PURPLE,
                ATTACK_GREMORY_PURPLE_PORTRAIT, SKILL_GREMORY_PURPLE_PORTRAIT, POWER_GREMORY_PURPLE_PORTRAIT,
                ENERGY_ORB_GREMORY_PURPLE_PORTRAIT, GREMORY_CARD_ENERGY_ORB);

        BaseMod.addColor(GroveKeeper_Forest_Color, GROVEKEEPER_FOREST, GROVEKEEPER_FOREST, GROVEKEEPER_FOREST,
                GROVEKEEPER_FOREST, GROVEKEEPER_FOREST, GROVEKEEPER_FOREST, GROVEKEEPER_FOREST,
                ATTACK_FOREST, SKILL_FOREST, POWER_FOREST, ENERGY_ORB_FOREST,
                FOREST_ATTACK_PORTRAIT, FOREST_SKILL_PORTRAIT,FOREST_POWER_GRAY_PORTRAIT,
                ENERGY_ORB_FOREST_PORTRAIT, FOREST_ENERGY_ORB);
        
        logger.info("Done creating the color");
        
        
        logger.info("Adding mod settings");
        // This loads the mod settings.
        // The actual mod Button is added below in receivePostInitialize()
        theDefaultDefaultSettings.setProperty(ENABLE_PLACEHOLDER_SETTINGS, "FALSE"); // This is the default setting. It's actually set...
        try {
            SpireConfig config = new SpireConfig("defaultMod", "theDefaultConfig", theDefaultDefaultSettings); // ...right here
            // the "fileName" parameter is the name of the file MTS will create where it will save our setting.
            config.load(); // Load the setting and set the boolean to equal it
            enablePlaceholder = config.getBool(ENABLE_PLACEHOLDER_SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Done adding mod settings");
        
    }
    
    // ====== NO EDIT AREA ======
    // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO ENSURE GOOD CODE PRACTICES.
    // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP
    
    public static void setModID(String ID) { // DON'T EDIT
        Gson coolG = new Gson(); // EY DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i hate u Gdx.files
        InputStream in = DefaultMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THIS ETHER
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // OR THIS, DON'T EDIT IT
        logger.info("You are attempting to set your mod ID as: " + ID); // NO WHY
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION); // THIS ALSO DON'T EDIT
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) { // NO
            modID = EXCEPTION_STRINGS.DEFAULTID; // DON'T
        } else { // NO EDIT AREA
            modID = ID; // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
        } // NO
        logger.info("Success! ID is " + modID); // WHY WOULD U WANT IT NOT TO LOG?? DON'T EDIT THIS.
    } // NO
    
    public static String getModID() { // NO
        return modID; // DOUBLE NO
    } // NU-UH
    
    private static void pathCheck() { // ALSO NO
        Gson coolG = new Gson(); // NOPE DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i still hate u btw Gdx.files
        InputStream in = DefaultMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THISSSSS
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // NAH, NO EDIT
        String packageName = DefaultMod.class.getPackage().getName(); // STILL NO EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources"); // PLEASE DON'T EDIT THINGS HERE, THANKS
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(getModID())) { // NOT HERE ETHER
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID()); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources"); // NOT THIS
            }// NO
        }// NO
    }// NO
    
    // ====== YOU CAN EDIT AGAIN ======
    
    
    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= Initializing Default Mod. Hi. =========================");
        DefaultMod defaultmod = new DefaultMod();
        logger.info("========================= /Default Mod Initialized. Hello World./ =========================");
    }

    public static void onGenerateCardMidcombat(AbstractCard card) {
        if (AbstractDungeon.player.hasRelic(ObsidianScale.ID) && card.type == AbstractCard.CardType.STATUS && !ObsidianMight){
            AbstractDungeon.player.getRelic(ObsidianScale.ID).flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,
                    new StrengthPower(AbstractDungeon.player,2),2));
            ObsidianMight = true;
        }
        if (AbstractDungeon.player.hasRelic(FernosBellows.ID) && card.cardID.equals(Burn.ID) && !AbstractDungeon.actionManager.turnHasEnded){
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, AbstractDungeon.player,
                        new Scorchpower(mo,AbstractDungeon.player, 2), 2));
            }
        }
        if (AbstractDungeon.player.hasPower(WindsSong.POWER_ID)){
            if (card.hasTag(CustomTags.Wind) && card.cost > 0){
                card.cost -= 1;
            }
        }
        if (AbstractDungeon.player.hasPower(ImmaculateSnow.POWER_ID)){
            if (card.hasTag(CustomTags.Ice) && card.cost > 0){
                card.cost -= 1;
            }
        }
        if (AbstractDungeon.player.hasPower(FlowersAmbition.POWER_ID)){
            if (card.hasTag(CustomTags.Fire) && card.cost > 0){
                card.cost -= 1;
            }
        }
        if (AbstractDungeon.player.hasPower(MoonsMarch.POWER_ID)){
            if (card.hasTag(CustomTags.Physical) && card.cost > 0){
                card.cost -= 1;
            }
        }
    }

    // ============== /SUBSCRIBE, CREATE THE Dragonkin_Red_COLOR, INITIALIZE/ =================
    
    
    // =============== LOAD THE CHARACTER =================
    
    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + TheDefault.Enums.THE_DRAGONKIN.toString());
        
        BaseMod.addCharacter(new TheDefault("the Dragonkin", TheDefault.Enums.THE_DRAGONKIN),
                THE_DEFAULT_BUTTON, THE_DEFAULT_PORTRAIT, TheDefault.Enums.THE_DRAGONKIN);

        BaseMod.addCharacter(new TheGremory("the Gremory", TheGremory.Enums.THE_GREMORY),
                THE_GREMORY_BUTTON, THE_DEFAULT_PORTRAIT, TheGremory.Enums.THE_GREMORY);

        BaseMod.addCharacter(new TheGroveKeeper("the Grovekeeper", TheGroveKeeper.Enums.THE_GROVEKEEPER),
                THE_GROVEKEEPER_BUTTON, THE_DEFAULT_PORTRAIT, TheGroveKeeper.Enums.THE_GROVEKEEPER);

        receiveEditPotions();

        logger.info("Added " + TheDefault.Enums.THE_DRAGONKIN.toString());
        logger.info("Added " + TheGremory.Enums.THE_GREMORY.toString());
        logger.info("Added " + TheGroveKeeper.Enums.THE_GROVEKEEPER.toString());
    }
    
    // =============== /LOAD THE CHARACTER/ =================
    
    
    // =============== POST-INITIALIZE =================
    
    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");
        
        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);
        
        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();
        
        // Create the on/off button:
        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton("This is the text which goes next to the checkbox.",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position (trial and error it), color, font
                enablePlaceholder, // Boolean it uses
                settingsPanel, // The mod panel in which this button will be in
                (label) -> {}, // thing??????? idk
                (button) -> { // The actual button:
            
            enablePlaceholder = button.enabled; // The boolean true/false will be whether the button is enabled or not
            try {
                // And based on that boolean, set the settings and save them
                SpireConfig config = new SpireConfig("defaultMod", "theDefaultConfig", theDefaultDefaultSettings);
                config.setBool(ENABLE_PLACEHOLDER_SETTINGS, enablePlaceholder);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        settingsPanel.addUIElement(enableNormalsButton); // Add the button to the settings panel. Button is a go.
        
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        
        // =============== EVENTS =================
        
        // This event will be exclusive to the City (act 2). If you want an event that's present at any
        // part of the game, simply don't include the dungeon ID
        // If you want to have a character-specific event, look at slimebound (CityRemoveEventPatch).
        // Essentially, you need to patch the game and say "if a player is not playing my character class, remove the event from the pool"
        
        // =============== /EVENTS/ =================
        logger.info("Done loading badge Image and mod options");
    }
    
    // =============== / POST-INITIALIZE/ =================
    
    
    // ================ ADD POTIONS ===================
    
    public void receiveEditPotions() {
        logger.info("Beginning to edit potions");
        
        // Class Specific Potion. If you want your potion to not be class-specific,
        // just remove the player class at the end (in this case the "TheDefaultEnum.THE_DEFAULT".
        // Remember, you can press ctrl+P inside parentheses like addPotions)
        BaseMod.addPotion(DragonkinCommonPotion.class, Color.FIREBRICK.cpy() , null, Color.LIGHT_GRAY.cpy(),DragonkinCommonPotion.POTION_ID, TheDefault.Enums.THE_DRAGONKIN);
        BaseMod.addPotion(DragonkinUncommonPotion.class, Color.YELLOW.cpy() ,Color.SKY.cpy() , null, DragonkinUncommonPotion.POTION_ID, TheDefault.Enums.THE_DRAGONKIN);
        BaseMod.addPotion(DragonkinRarePotion.class, Color.GOLDENROD.cpy() ,Color.RED.cpy() , null, DragonkinRarePotion.POTION_ID, TheDefault.Enums.THE_DRAGONKIN);

        BaseMod.addPotion(MagicHerbTea.class, Color.CHARTREUSE.cpy() , Color.GREEN.cpy(), null,MagicHerbTea.POTION_ID, TheGremory.Enums.THE_GREMORY);
        BaseMod.addPotion(SpringWater.class, Color.SKY.cpy() ,Color.BLUE.cpy() , Color.TEAL, SpringWater.POTION_ID, TheGremory.Enums.THE_GREMORY);
        BaseMod.addPotion(ShadowofZahras.class, Color.BLACK.cpy() ,Color.DARK_GRAY.cpy() , null, ShadowofZahras.POTION_ID, TheGremory.Enums.THE_GREMORY);
        logger.info("Done editing potions");
    }
    
    // ================ /ADD POTIONS/ ===================
    
    
    // ================ ADD RELICS ===================
    
    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");
        
        // This adds a character specific relic. Only when you play with the mentioned color, will you get this relic.
        BaseMod.addRelicToCustomPool(new GarnetScale(), Dragonkin_Red_COLOR);
        BaseMod.addRelicToCustomPool(new ObsidianScale(), Dragonkin_Red_COLOR);
        BaseMod.addRelicToCustomPool(new EmberCore(), Dragonkin_Red_COLOR);
        BaseMod.addRelicToCustomPool(new MukySludge(), Dragonkin_Red_COLOR);
        BaseMod.addRelicToCustomPool(new FernosBellows(), Dragonkin_Red_COLOR);
        BaseMod.addRelicToCustomPool(new BookOfHymns(), Dragonkin_Red_COLOR);
        BaseMod.addRelicToCustomPool(new RotnestWings(), Dragonkin_Red_COLOR);
        BaseMod.addRelicToCustomPool(new Sulfurian(), Dragonkin_Red_COLOR);
        BaseMod.addRelicToCustomPool(new TilerasShield(), Dragonkin_Red_COLOR);
        BaseMod.addRelicToCustomPool(new UthersMaul(), Dragonkin_Red_COLOR);

        BaseMod.addRelicToCustomPool(new HeartofFlames(), Gremory_Purple_Color);

        // This adds a relic to the Shared pool. Every character can find this relic.
        
        // Mark relics as seen (the others are all starters so they're marked as seen in the character file;
        UnlockTracker.markRelicAsSeen(ObsidianScale.ID);
        UnlockTracker.markRelicAsSeen(EmberCore.ID);
        UnlockTracker.markRelicAsSeen(MukySludge.ID);
        UnlockTracker.markRelicAsSeen(FernosBellows.ID);
        UnlockTracker.markRelicAsSeen(BookOfHymns.ID);
        UnlockTracker.markRelicAsSeen(Sulfurian.ID);
        UnlockTracker.markRelicAsSeen(TilerasShield.ID);
        UnlockTracker.markRelicAsSeen(UthersMaul.ID);
        logger.info("Done adding relics!");
    }
    
    // ================ /ADD RELICS/ ===================
    
    
    // ================ ADD CARDS ===================
    
    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        //Ignore this
        pathCheck();
        // Add the Custom Dynamic Variables
        BaseMod.addDynamicVariable(new HealDynVar());
        BaseMod.addDynamicVariable(new GrovekeeperSecondDamage());
        BaseMod.addDynamicVariable(new MagicDamageDynVar());
        BaseMod.addDynamicVariable(new SpellUses());
        logger.info("Add variables");
        // Add the Custom Dynamic variables
        BaseMod.addDynamicVariable(new DefaultCustomVariable());
        BaseMod.addDynamicVariable(new DefaultSecondMagicNumber());
        
        logger.info("Adding cards");
        new AutoAdd("DragonkinMod").packageFilter("theDragonkin.cards.Dragonkin").setDefaultSeen(true).cards();
        new AutoAdd("DragonkinMod").packageFilter("theDragonkin.cards.Gremory").setDefaultSeen(true).cards();
        new AutoAdd("DragonkinMod").packageFilter("theDragonkin.cards.GroveKeeper").setDefaultSeen(true).cards();

        logger.info("Done adding cards!");
        logger.info("Added: " + BaseMod.getCardCount(Dragonkin_Red_COLOR) + " Cards");
    }
    
    // There are better ways to do this than listing every single individual card, but I do not want to complicate things
    // in a "tutorial" mod. This will do and it's completely ok to use. If you ever want to clean up and
    // shorten all the imports, go look take a look at other mods, such as Hubris.
    
    // ================ /ADD CARDS/ ===================
    
    
    // ================ LOAD THE TEXT ===================
    
    @Override
    public void receiveEditStrings() {
        logger.info("You seeing this?");
        logger.info("Beginning to edit strings for mod with ID: " + getModID());
        
        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Card-Strings.json");
        
        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Power-Strings.json");
        
        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Relic-Strings.json");
        
        // Event Strings
        BaseMod.loadCustomStringsFile(EventStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Event-Strings.json");
        
        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Potion-Strings.json");
        
        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Character-Strings.json");
        
        // OrbStrings
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Orb-Strings.json");

        BaseMod.loadCustomStringsFile(UIStrings.class, getModID() + "Resources/localization/eng/DefaultMod-UI-Strings.json");
        
        logger.info("Done edittting strings");
    }
    
    // ================ /LOAD THE TEXT/ ===================
    
    // ================ LOAD THE KEYWORDS ===================
    
    @Override
    public void receiveEditKeywords() {
        // Keywords on cards are supposed to be Capitalized, while in Keyword-String.json they're lowercase
        //
        // Multiword keywords on cards are done With_Underscores
        //
        // If you're using multiword keywords, the first element in your NAMES array in your keywords-strings.json has to be the same as the PROPER_NAME.
        // That is, in Card-Strings.json you would have #yA_Long_Keyword (#y highlights the keyword in yellow).
        // In Keyword-Strings.json you would have PROPER_NAME as A Long Keyword and the first element in NAMES be a long keyword, and the second element be a_long_keyword
        
        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/DefaultMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);
        
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                //  getModID().toLowerCase() makes your keyword mod specific (it won't show up in other cards that use that word)
            }
        }
    }
    
    // ================ /LOAD THE KEYWORDS/ ===================    
    
    // this adds "ModName:" before the ID of any card/relic/power etc.
    // in order to avoid conflicts if any other mod uses the same ID.
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }
}

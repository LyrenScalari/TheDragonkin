package theDragonknight;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theDragonknight.cards.Dragonknight.AbstractDragonknightCard;
import theDragonknight.characters.TheDragonknight;
import theDragonknight.relics.TaintedSoul;
import theDragonknight.util.*;
import theDragonknight.variables.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Properties;

import static theDragonknight.characters.TheDragonknight.Enums.Dragonknight_Crimson_Color;
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
public class DragonknightMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber,
        OnPlayerTurnStartSubscriber,
        OnStartBattleSubscriber,
        PreMonsterTurnSubscriber{
    // Make sure to implement the subscribers *you* are using (read basemod wiki). Editing cards? EditCardsSubscriber.
    // Making relics? EditRelicsSubscriber. etc., etc., for a full list and how to make your own, visit the basemod wiki.
    public static final Logger logger = LogManager.getLogger(DragonknightMod.class.getName());
    public static String modID = "theDragonknight";

    // Mod-settings settings. This is if you want an on/off savable button
    public static Properties theDefaultDefaultSettings = new Properties();
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    public static boolean enablePlaceholder = true; // The boolean we'll be setting on/off (true/false)
    public static boolean ObsidianMight = false;
    public static boolean WasDrained = false;
    public static boolean Done = false;
    public static boolean WasDivineLost = false;
    public static int StatusesCycledThisTurn = 0;
    public static int CardsCycledThisTurn = 0;
    public static boolean damagetaken = false;
    //This is for the in-game mod settings panel.
    private static final String MODNAME = "The Dragonknight";
    private static final String AUTHOR = "Lyren"; // And pretty soon - You!
    private static final String DESCRIPTION = "";
    public static Texture DIVINE_ARMOR_ICON;
    public static int Alignment;
    public static ArrayList<AbstractCard> Stars = new ArrayList<>();
    public static ArrayList<AbstractNotOrb> Seals = new ArrayList<>();
    // =============== INPUT TEXTURE LOCATION =================
    // Colors (RGB)
    // Character Color
    public static final Color DEFAULT_GRAY = CardHelper.getColor(209.0f, 53.0f, 18.0f);
    public static final Color DRAGONKNIGHT_CRIMSON = CardHelper.getColor(109.0f, 03.0f, 58.0f);
    public static final Color Cursed_Purple = CardHelper.getColor(115.0f, 28.0f, 153.0f);
    public static final Color WindWalker_Jade = CardHelper.getColor(0.0f, 168.0f, 107.0f);
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


    // Dragonknight stuff

    private static final String ATTACK_DEFAULT_GRAY = "theDragonknightResources/images/512/dragonkin/bg_attack_scales.png";
    private static final String SKILL_DEFAULT_GRAY = "theDragonknightResources/images/512/dragonkin/bg_skill_scales.png";
    private static final String POWER_DEFAULT_GRAY = "theDragonknightResources/images/512/dragonkin/bg_power_scales.png";

    private static final String ENERGY_ORB_DEFAULT_GRAY = "theDragonknightResources/images/512/card_default_gray_orb.png";
    private static final String CARD_ENERGY_ORB = "theDragonknightResources/images/512/card_small_orb.png";
    
    private static final String ATTACK_DEFAULT_GRAY_PORTRAIT = "theDragonknightResources/images/1024/dragonkin/bg_attack_scales.png";
    private static final String SKILL_DEFAULT_GRAY_PORTRAIT = "theDragonknightResources/images/1024/dragonkin/bg_skill_scales.png";
    private static final String POWER_DEFAULT_GRAY_PORTRAIT = "theDragonknightResources/images/1024/dragonkin/bg_power_scales.png";
    private static final String ENERGY_ORB_DEFAULT_GRAY_PORTRAIT = "theDragonknightResources/images/1024/card_default_gray_orb.png";
    public static final String LIGHTNINGBOLT = "theDragonknightResources/images/VFX/lightningspear.png";
    public static final String DOVAH_FONT = "theDragonknightResources/Font/DovahkiinItalic-2BDv.ttf";
    public static BitmapFont DovahFont;
    // Character assets
    public static final String THE_DRAGONKIN_SHOULDER_1 = "theDragonknightResources/images/char/defaultCharacter/Dragonkinshoulder.png";
    public static final String THE_DRAGONKIN_SHOULDER_2 = "theDragonknightResources/images/char/defaultCharacter/Dragonkinshoulder2.png";
    public static final String THE_DRAGONKIN_CORPSE = "theDragonknightResources/images/char/defaultCharacter/DragonkinCorpse.png";
    private static final String THE_DEFAULT_BUTTON = "theDragonknightResources/images/charSelect/DefaultCharacterButton.png";
    private static final String THE_DEFAULT_PORTRAIT = "theDragonknightResources/images/charSelect/DefaultCharacterPortraitBG.png";
    public static final String THE_DEFAULT_SHOULDER_1 = "theDragonknightResources/images/char/defaultCharacter/shoulder.png";
    public static final String THE_DEFAULT_SHOULDER_2 = "theDragonknightResources/images/char/defaultCharacter/shoulder2.png";
    public static final String THE_DEFAULT_CORPSE = "theDragonknightResources/images/char/defaultCharacter/corpse.png";
    
    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = "theDragonknightResources/images/Badge.png";

    public static TextureAtlas TypeEnergyAtlas = new TextureAtlas();
    // Atlas and JSON files for the Animations
    public static final String THE_DEFAULT_SKELETON_ATLAS = "theDragonknightResources/images/char/defaultCharacter/TheDragonkin.atlas";
    public static final String THE_DEFAULT_SKELETON_JSON = "theDragonknightResources/images/char/defaultCharacter/TheDragonkin.json";

    // =============== MAKE IMAGE PATHS =================
    
    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }
    public static String makeImagePath(String resourcePath) {
        return getModID() + "Resources/images/" + resourcePath;
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
    
    
    // =============== SUBSCRIBE, CREATE THE Justicar_Red_COLOR, INITIALIZE =================
    
    public DragonknightMod() {
        logger.info("Subscribe to BaseMod hooks");
        
        BaseMod.subscribe(this);
      
        setModID("theDragonknight");
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
        
        logger.info("Creating the color " + Dragonknight_Crimson_Color.toString());

        BaseMod.addColor(Dragonknight_Crimson_Color, DRAGONKNIGHT_CRIMSON.cpy(), DRAGONKNIGHT_CRIMSON.cpy(), DRAGONKNIGHT_CRIMSON.cpy(),
                DRAGONKNIGHT_CRIMSON.cpy(), DRAGONKNIGHT_CRIMSON.cpy(), DRAGONKNIGHT_CRIMSON.cpy(), DRAGONKNIGHT_CRIMSON.cpy(),
                ATTACK_DEFAULT_GRAY, SKILL_DEFAULT_GRAY, POWER_DEFAULT_GRAY, ENERGY_ORB_DEFAULT_GRAY,
                ATTACK_DEFAULT_GRAY_PORTRAIT, SKILL_DEFAULT_GRAY_PORTRAIT, POWER_DEFAULT_GRAY_PORTRAIT,
                ENERGY_ORB_DEFAULT_GRAY_PORTRAIT, CARD_ENERGY_ORB);
        /*BaseMod.addColor(WindWalker_Jade_COLOR, WindWalker_Jade.cpy(), WindWalker_Jade.cpy(), WindWalker_Jade.cpy(),
                WindWalker_Jade.cpy(), WindWalker_Jade.cpy(), WindWalker_Jade.cpy(), WindWalker_Jade.cpy(),
                ATTACK_WindWalker, SKILL_WindWalker, Power_WindWalker, ENERGY_ORB_DEFAULT_GRAY,
                ATTACK_WindWalker_PORTRAIT, SKILL_WindWalker_PORTRAIT, Power_WindWalker_PORTRAIT,
                ENERGY_ORB_DEFAULT_GRAY_PORTRAIT, CARD_ENERGY_ORB);*/

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
        InputStream in = DragonknightMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THIS ETHER
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
        InputStream in = DragonknightMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THISSSSS
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // NAH, NO EDIT
        String packageName = DragonknightMod.class.getPackage().getName(); // STILL NO EDIT ZONE
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
        DragonknightMod dragonknightMod = new DragonknightMod();
        logger.info("========================= /Default Mod Initialized. Hello World./ =========================");
    }
    public static void onGenerateCardMidcombat(AbstractCard card) {
    }

    // ============== /SUBSCRIBE, CREATE THE Justicar_Red_COLOR, INITIALIZE/ =================
    
    
    // =============== LOAD THE CHARACTER =================
    
    @Override
    public void receiveEditCharacters() {
        
        BaseMod.addCharacter(new TheDragonknight("the Dragonknight", TheDragonknight.Enums.THE_DRAGONKNIGHT),
                THE_DEFAULT_BUTTON, THE_DEFAULT_PORTRAIT, TheDragonknight.Enums.THE_DRAGONKNIGHT);

        receiveEditPotions();

        //logger.info("Added " + TheDefault.Enums.THE_JUSTICAR.toString());
    }
    
    // =============== /LOAD THE CHARACTER/ =================
    
    
    // =============== POST-INITIALIZE =================
    
    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");
        DIVINE_ARMOR_ICON = ImageMaster.loadImage("theDragonknightResources/images/ui/DivineArmor.png");
        DovahFont = prepFont(new FreeTypeFontGenerator(Gdx.files.internal(DOVAH_FONT)), 46f, false);
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
        // Add the event
        logger.info("Done loading badge Image and mod options");
    }
    private static BitmapFont prepFont(FreeTypeFontGenerator g, float size, boolean isLinearFiltering) {
        FreeTypeFontGenerator.FreeTypeFontParameter p = new FreeTypeFontGenerator.FreeTypeFontParameter();
        p.characters = "";
        p.incremental = true;
        p.size =(int) size/2;
        p.gamma = 0.9F;
        p.spaceX = (int)(-0.9F * Settings.scale);
        p.spaceY = p.size;
        p.borderColor = new Color(0.4F, 0.1F, 0.1F, 1.0F);
        p.borderStraight = false;
        p.borderWidth =  1.25F * Settings.scale;
        p.borderGamma = 0.9F;
        p.shadowColor = Settings.QUARTER_TRANSPARENT_BLACK_COLOR;
        p.shadowOffsetX = (int)(1.0F * Settings.scale);
        p.shadowOffsetY = Math.round(1.0F * Settings.scale);
        if (isLinearFiltering) {
            p.minFilter = TextureFilter.Linear;
            p.magFilter = TextureFilter.Linear;
        } else {
            p.minFilter = TextureFilter.Nearest;
            p.magFilter = TextureFilter.MipMapLinearNearest;
        }

        g.scaleForPixelHeight(p.size);
        BitmapFont font = g.generateFont(p);
        font.setUseIntegerPositions(!isLinearFiltering);
        font.getData().markupEnabled = true;
        if (LocalizedStrings.break_chars != null) {
            font.getData().breakChars = LocalizedStrings.break_chars.toCharArray();
        }
        return font;
    }
    // =============== / POST-INITIALIZE/ =================
    
    
    // ================ ADD POTIONS ===================
    
    public void receiveEditPotions() {
        logger.info("Beginning to edit potions");
        
        // Class Specific Potion. If you want your potion to not be class-specific,
        // just remove the player class at the end (in this case the "TheDefaultEnum.THE_DEFAULT".
        // Remember, you can press ctrl+P inside parentheses like addPotions)
        //BaseMod.addPotion(DragonknightCommonPotion.class, Color.FIREBRICK.cpy() , null, Color.LIGHT_GRAY.cpy(),DragonknightCommonPotion.POTION_ID, TheDefault.Enums.THE_JUSTICAR);
        logger.info("Done editing potions");
    }
    
    // ================ /ADD POTIONS/ ===================
    
    
    // ================ ADD RELICS ===================
    
    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");
        BaseMod.addRelicToCustomPool(new TaintedSoul(),Dragonknight_Crimson_Color);
        // This adds a character specific relic. Only when you play with the mentioned color, will you get this relic.
        //BaseMod.addRelicToCustomPool(new GarnetScale(), Justicar_Red_COLOR);

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
       // BaseMod.addDynamicVariable(new HealDynVar());
        logger.info("Add variables");
        // Add the Custom Dynamic variables
        BaseMod.addDynamicVariable(new DefaultCustomVariable());
        BaseMod.addDynamicVariable(new DefaultSecondMagicNumber());
        BaseMod.addDynamicVariable(new SecondDamage());
        BaseMod.addDynamicVariable(new ThirdDamage());
        logger.info("Adding cards");
        //new AutoAdd("DragonknightMod").packageFilter(AbstractDragonknightCard.class).setDefaultSeen(true).cards();
        //new AutoAdd("DragonknightMod").packageFilter(AbstractWindWalkerCard.class).setDefaultSeen(true).cards();
        //new AutoAdd("DragonknightMod").packageFilter(AbstractDefaultCard.class).setDefaultSeen(true).cards();
        new AutoAdd("DragonknightMod").packageFilter(AbstractDragonknightCard.class).setDefaultSeen(true).cards();
        logger.info("Done adding cards!");
        logger.info("Added: " + BaseMod.getCardCount(Dragonknight_Crimson_Color) + " Cards");
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
                getModID() + "Resources/localization/eng/CardStrings.json");
        
        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/eng/PowerStrings.json");
        
        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/eng/RelicStrings.json");
        
        // Event Strings
        BaseMod.loadCustomStringsFile(EventStrings.class,
                getModID() + "Resources/localization/eng/EventStrings.json");
        
        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                getModID() + "Resources/localization/eng/PotionStrings.json");
        
        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                getModID() + "Resources/localization/eng/CharacterStrings.json");
        
        // OrbStrings
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                getModID() + "Resources/localization/eng/OrbStrings.json");

        BaseMod.loadCustomStringsFile(UIStrings.class, getModID() + "Resources/localization/eng/UIStrings.json");

        BaseMod.loadCustomStringsFile(MonsterStrings.class, getModID() + "Resources/localization/eng/MonsterStrings.json");

        BaseMod.loadCustomStringsFile(StanceStrings.class, getModID() + "Resources/localization/eng/Stancestrings.json");
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
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/KeywordStrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
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
    @Override
    public boolean receivePreMonsterTurn(AbstractMonster abstractMonster) {
        for (AbstractNotOrb seal : Seals){
            seal.onEndOfTurn();
        }
        return true;
    }


    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        Seals.clear();
    }

    @Override
    public void receiveOnPlayerTurnStart() {
        damagetaken = false;
        for (AbstractNotOrb seal : Seals){
            seal.onStartOfTurn();
        }
    }
}
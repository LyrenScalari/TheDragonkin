package theDragonkin.characters;

import basemod.abstracts.CustomEnergyOrb;
import basemod.abstracts.CustomPlayer;
import basemod.animations.SpineAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theDragonkin.DragonkinMod;
import theDragonkin.cards.Deathspeaker.DeathspeakerDefend;
import theDragonkin.cards.Deathspeaker.DeathspeakerStrike;
import theDragonkin.cards.Deathspeaker.Shadowblast;
import theDragonkin.cards.Deathspeaker.Souldrain;
import theDragonkin.relics.Deathspeaker.GuiderLamp;
import theDragonkin.ui.EnergyOrbDragonkin;

import java.util.ArrayList;

import static theDragonkin.DragonkinMod.*;
import static theDragonkin.DragonkinMod.THE_DEFAULT_SKELETON_JSON;

public class TheDeathspeaker extends CustomPlayer {
    public static final Logger logger = LogManager.getLogger(DragonkinMod.class.getName());

    // =============== CHARACTER ENUMERATORS =================
    // These are enums for your Characters color (both general color and for the card library) as well as
    // an enum for the name of the player class - IRONCLAD, THE_SILENT, DEFECT, YOUR_CLASS ...
    // These are all necessary for creating a character. If you want to find out where and how exactly they are used
    // in the basegame (for fun and education) Ctrl+click on the PlayerClass, CardColor and/or LibraryType below and go down the
    // Ctrl+click rabbit hole

    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass THE_DEATHSPEAKER;
        @SpireEnum(name = "Deathspeaker_Purple") // These two HAVE to have the same absolutely identical name.
        public static AbstractCard.CardColor Deathspeaker_Purple;
        @SpireEnum(name = "Deathspeaker_Purple") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }
    private static final String[] orbTextures = {
            modID + "Resources/images/char/TheDeathspeaker/Orb/layer1.png",
            modID + "Resources/images/char/TheDeathspeaker/Orb/layer2.png",
            modID + "Resources/images/char/TheDeathspeaker/Orb/layer3.png",
            modID + "Resources/images/char/TheDeathspeaker/Orb/layer4.png",
            modID + "Resources/images/char/TheDeathspeaker/Orb/layer5.png",
            modID + "Resources/images/char/TheDeathspeaker/Orb/layer6.png",
            modID + "Resources/images/char/TheDeathspeaker/Orb/layer1d.png",
            modID + "Resources/images/char/TheDeathspeaker/Orb/layer2d.png",
            modID + "Resources/images/char/TheDeathspeaker/Orb/layer3d.png",
            modID + "Resources/images/char/TheDeathspeaker/Orb/layer4d.png",
            modID + "Resources/images/char/TheDeathspeaker/Orb/layer5d.png",};
    // =============== CHARACTER ENUMERATORS  =================


    // =============== BASE STATS =================

    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 70;
    public static final int MAX_HP = 50;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 0;

    // =============== /BASE STATS/ =================


    // =============== STRINGS =================

    private static final String ID = makeID("TheDeathspeaker");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;
    public static float[] layerSpeeds = new float[]{-20.0F, 20.0F, -40.0F, 40.0F, 0.0F};
    // =============== /STRINGS/ =================


    // =============== TEXTURES OF BIG ENERGY ORB ===============


    // =============== /TEXTURES OF BIG ENERGY ORB/ ===============

    // =============== CHARACTER CLASS START =================

    public TheDeathspeaker(String name, PlayerClass setClass) {
        super(name, setClass, new CustomEnergyOrb(orbTextures, modID + "Resources/images/char/TheDeathspeaker/Orb/vfx.png", null),
                new SpineAnimation("theDragonkinResources/images/char/defaultCharacter/TheDragonkin.atlas","theDragonkinResources/images/char/defaultCharacter/TheDragonkin.json",1.0f));


        // =============== TEXTURES, ENERGY, LOADOUT =================

        initializeClass(null, // required call to load textures and setup energy/loadout.
                // I left these in DragonkinMod.java (Ctrl+click them to see where they are, Ctrl+hover to see what they read.)
                THE_DRAGONKIN_SHOULDER_2, // campfire pose
                THE_DRAGONKIN_SHOULDER_1, // another campfire pose
                THE_DRAGONKIN_CORPSE, // dead corpse
                getLoadout(), 20.0F, -10.0F, 317.0F, 520.0F, new EnergyManager(ENERGY_PER_TURN)); // energy manager

        // =============== /TEXTURES, ENERGY, LOADOUT/ =================


        // =============== ANIMATIONS =================

        loadAnimation(
                THE_DEFAULT_SKELETON_ATLAS,
                THE_DEFAULT_SKELETON_JSON,
                1.0f);
        AnimationState.TrackEntry e = state.setAnimation(0, "animation", true);
        AnimationState.TrackEntry e1 = state.setAnimation(1, "WingFlap", true);
        AnimationState.TrackEntry e2 = state.setAnimation(2, "TailFlick", true);
        e.setTime(e.getEndTime() * MathUtils.random());

        // =============== /ANIMATIONS/ =================


        // =============== TEXT BUBBLE LOCATION =================

        dialogX = (drawX + 80.0F * Settings.scale); // set location for text bubbles
        dialogY = (drawY + 300.0F * Settings.scale); // you can just copy these values

        // =============== /TEXT BUBBLE LOCATION/ =================

    }

    // =============== /CHARACTER CLASS END/ =================

    // Starting description and loadout
    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    // Starting Deck
    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();

        logger.info("Begin loading starter Deck Strings");

        retVal.add(DeathspeakerStrike.ID);
        retVal.add(DeathspeakerStrike.ID);
        retVal.add(DeathspeakerStrike.ID);
        retVal.add(DeathspeakerStrike.ID);
        retVal.add(DeathspeakerDefend.ID);
        retVal.add(DeathspeakerDefend.ID);
        retVal.add(DeathspeakerDefend.ID);
        retVal.add(DeathspeakerDefend.ID);
        retVal.add(Shadowblast.ID);
        retVal.add(Souldrain.ID);
        return retVal;
    }

    // Starting Relics
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(GuiderLamp.ID);
        return retVal;
    }

    // character Select screen effect
    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("ATTACK_FLAME_BARRIER", 1.25f); // Sound Effect
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT,
                false); // Screen Effect
    }

    // character Select on-button-press sound effect
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_FLAME_BARRIER";
    }

    // Should return how much HP your maximum HP reduces by when starting a run at
    // Ascension 14 or higher. (ironclad loses 5, defect and silent lose 4 hp respectively)
    @Override
    public int getAscensionMaxHPLoss() {
        return 5;
    }

    // Should return the card color enum to be associated with your character.
    @Override
    public AbstractCard.CardColor getCardColor() {
        return Enums.Deathspeaker_Purple;
    }

    // Should return a color object to be used to color the trail of moving cards
    @Override
    public Color getCardTrailColor() {
        return Cursed_Purple;
    }

    // Should return a BitmapFont object that you can use to customize how your
    // energy is displayed from within the energy orb.
    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontPurple;
    }

    // Should return class name as it appears in run history screen.
    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    //Which card should be obtainable from the Match and Keep event?
    @Override
    public AbstractCard getStartCardForEvent() {
        return new Souldrain();
    }

    // The class name as it appears next to your player name in-game
    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    // Should return a new instance of your character, sending name as its name parameter.
    @Override
    public AbstractPlayer newInstance() {
        return new TheDeathspeaker(name, chosenClass);
    }

    // Should return a Color object to be used to color the miniature card images in run history.
    @Override
    public Color getCardRenderColor() {
        return Cursed_Purple;
    }

    // Should return a Color object to be used as screen tint effect when your
    // character attacks the heart.
    @Override
    public Color getSlashAttackColor() {
        return Cursed_Purple;
    }

    // Should return an AttackEffect array of any size greater than 0. These effects
    // will be played in sequence as your character's finishing combo on the heart.
    // Attack effects are the same as used in DamageAction and the like.
    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.FIRE,
                AbstractGameAction.AttackEffect.POISON,
                AbstractGameAction.AttackEffect.POISON,
                AbstractGameAction.AttackEffect.FIRE};
    }

    // Should return a string containing what text is shown when your character is
    // about to attack the heart. For example, the defect is "NL You charge your
    // core to its maximum..."
    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    // The vampire events refer to the base game characters as "brother", "sister",
    // and "broken one" respectively.This method should return a String containing
    // the full text that will be displayed as the first screen of the vampires event.
    @Override
    public String getVampireText() {
        return TEXT[2];
    }
    public String getSensoryStoneText()
    {
        return TEXT[3];
    }

}
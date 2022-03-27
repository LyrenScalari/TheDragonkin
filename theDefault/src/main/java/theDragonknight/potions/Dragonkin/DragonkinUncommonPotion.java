package theDragonknight.potions.Dragonkin;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theDragonknight.DragonkinMod;
import theDragonknight.actions.GainDivineArmorAction;

public class DragonkinUncommonPotion extends AbstractPotion {


    public static final String POTION_ID = DragonkinMod.makeID("DragonkinUncommonPotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public DragonkinUncommonPotion() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DragonkinMod.java
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.HEART, PotionColor.BLUE);

        // Potency is the damage/magic number equivalent of potions.
        potency = getPotency();
        this.labOutlineColor = DragonkinMod.DEFAULT_GRAY;
        // Initialize the Description
        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];

        // Do you throw this potion at an enemy or do you just consume it.
        isThrown = false;

        // Initialize the on-hover name + description
        tips.add(new PowerTip(name, description));

    }
    // See that description? It has DESCRIPTIONS[1] instead of just hard-coding the "text " + potency + " more text" inside.
    // DO NOT HARDCODE YOUR STRINGS ANYWHERE, it's really bad practice to have "Strings" in your code:

    /*
     * 1. It's bad for if somebody likes your mod enough (or if you decide) to translate it.
     * Having only the JSON files for translation rather than 15 different instances of "Dexterity" in some random cards is A LOT easier.
     *
     * 2. You don't have a centralised file for all strings for easy proof-reading. If you ever want to change a string
     * you don't have to go through all your files individually/pray that a mass-replace doesn't screw something up.
     *
     * 3. Without hardcoded strings, editing a string doesn't require a compile, saving you time (unless you clean+package).
     *
     */

    @Override
    public void use(AbstractCreature target) {
        target = AbstractDungeon.player;
        // If you are in combat, gain strength and the "lose strength at the end of your turn" power, equal to the potency of this potion.
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            addToBot(new GainDivineArmorAction(AbstractDungeon.player,AbstractDungeon.player,getPotency()));
        }
    }

    @Override
    public AbstractPotion makeCopy() {
        return new DragonkinUncommonPotion();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
        return 23;
    }

    public void upgradePotion()
    {
        potency += 1;
        tips.clear();
        tips.add(new PowerTip(name, description));
    }
}

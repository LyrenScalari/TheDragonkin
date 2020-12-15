package theDragonkin.potions.Gremory;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.HeartBuffEffect;
import theDragonkin.DefaultMod;
import theDragonkin.powers.Gremory.ArcanaPower;

public class MagicHerbTea extends AbstractPotion {


    public static final String POTION_ID = theDragonkin.DefaultMod.makeID("MagicHerbTea");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public MagicHerbTea() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.HEART, PotionColor.ANCIENT);
        this.labOutlineColor = DefaultMod.GREMORY_PURPLE;
        // Potency is the damage/magic number equivalent of potions.
        potency = getPotency();

        // Initialize the Description
        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];

        // Do you throw this potion at an enemy or do you just consume it.
        isThrown = false;

        // Initialize the on-hover name + description
        tips.add(new PowerTip(name, description));

    }

    @Override
    public void use(AbstractCreature target) {
        target = AbstractDungeon.player;
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            addToBot(new VFXAction(new HeartBuffEffect(target.drawX,target.drawY)));
            addToBot(new ApplyPowerAction(target,target,new ArcanaPower(target,potency)));
        }
    }

    @Override
    public AbstractPotion makeCopy() {
        return new MagicHerbTea();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
        return 3;
    }

    public void upgradePotion()
    {
        potency += 1;
        tips.clear();
        tips.add(new PowerTip(name, description));
    }
}

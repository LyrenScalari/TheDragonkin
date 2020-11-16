package theDragonkin.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.cards.Gremory.AbstractMagicGremoryCard;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DefaultMod.makePowerPath;

public class ResistancePower extends TwoAmountPower implements modifyMagicPower, NonStackablePower {
    public static final String POWER_ID = "Resistance";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("Ashfall.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("Ashfall32.png"));
    public ResistancePower(AbstractCreature owner, int amount, int duration) {
        this.name = NAME;
        this.amount2 = duration;
        this.ID = "Resistance";
        this.owner = owner;
        this.amount = amount;
        if (this.amount >= 999) {
            this.amount = 999;
        }

        if (this.amount <= -999) {
            this.amount = -999;
        }
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        this.updateDescription();
        this.canGoNegative = true;
    }

    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("ATTACK_MAGIC_SLOW_3", 0.05F);
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount == 0) {
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, ArcanaPower.POWER_ID));
        }

        if (this.amount >= 999) {
            this.amount = 999;
        }

        if (this.amount <= -999) {
            this.amount = -999;
        }

    }

    public void reducePower(int reduceAmount) {
        this.fontScale = 8.0F;
        this.amount -= reduceAmount;
        if (this.amount == 0) {
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, NAME));
        }

        if (this.amount >= 999) {
            this.amount = 999;
        }

        if (this.amount <= -999) {
            this.amount = -999;
        }

    }

    public void atEndOfTurn (){
        if (amount2 == 1) {
            addToBot(new ReducePowerAction(owner, owner, this.ID, amount));
        }
        amount2 -= 1;
    }
    public void updateDescription() {
        if (this.amount > 0) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[3];
            this.type = PowerType.BUFF;
        } else {
            int tmp = -this.amount;
            this.description = DESCRIPTIONS[0] + tmp + DESCRIPTIONS[1] + tmp + DESCRIPTIONS[3];
            this.type = PowerType.DEBUFF;
        }

    }
    @Override
    public float modifyMagicCard(AbstractMagicGremoryCard c, float magicpower) {
        return magicpower + -(this.amount);
    }


    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Resistance");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}

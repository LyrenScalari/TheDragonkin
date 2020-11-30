package theDragonkin.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import theDragonkin.util.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.cards.Gremory.AbstractMagicGremoryCard;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DefaultMod.makePowerPath;

public class ChargedUpDuration extends TwoAmountPower implements modifyMagicPower , NonStackablePower {
    public static final String POWER_ID =  DefaultMod.makeID("Charged-UpDuration");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("ChargedUp.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("ChargedUp32.png"));

    public ChargedUpDuration(AbstractCreature owner, int amount, int duration) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        amount2 = duration;

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
        CardCrawlGame.sound.play("ORB_LIGHTNING_PASSIVE", 0.05F);
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount == 0) {
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }

        if (this.amount >= 999) {
            this.amount = 999;
        }

        if (this.amount <= -999) {
            this.amount = -999;
        }

    }
    @Override
    public void atEndOfTurn(final boolean isPlayer){
        if (this.amount == 1){
            addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player,AbstractDungeon.player,this.ID));
        }
        amount2 =- 1;
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


    public void updateDescription() {
        if (this.amount > 0) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
            this.type = PowerType.BUFF;
        } else {
            int tmp = -this.amount;
            this.description = DESCRIPTIONS[0] + tmp + DESCRIPTIONS[3] + tmp + DESCRIPTIONS[4];
            this.type = PowerType.DEBUFF;
        }
        this.description += DESCRIPTIONS[5] + amount2 + DESCRIPTIONS[6];

    }

    public float modifyBlock(float blockAmount, AbstractCard c) {
        if (c.hasTag(CustomTags.Thunder)) {
            return (blockAmount += (float) this.amount) < 0.0F ? 0.0F : blockAmount;
        } else return blockAmount;
    }


    @Override
    public float modifyMagicCard(AbstractMagicGremoryCard c, float magicpower) {
        if (c.hasTag(CustomTags.Thunder)) {
            return magicpower + this.amount;
        } else return magicpower;
    }
}

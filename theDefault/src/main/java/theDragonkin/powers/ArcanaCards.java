package theDragonkin.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.cards.Gremory.AbstractMagicGremoryCard;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DefaultMod.makePowerPath;

public class ArcanaCards extends TwoAmountPower implements modifyMagicPower {
    public static final String POWER_ID =  DefaultMod.makeID("ArcanaCards");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("Arcana.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("Arcana32.png"));

    public ArcanaCards(AbstractCreature owner, int amount, int cards) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        amount2 = cards;

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
    public void onUseCard(AbstractCard c, UseCardAction action){
        if (c instanceof AbstractMagicGremoryCard) {
            amount2 -= 1;
            if (this.amount2 < 1) {
                addToTop(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, this.ID));
            }
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


    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount2;
        if (amount2 > 1){
            this.description += DESCRIPTIONS[1];
        } else {
            this.description += DESCRIPTIONS[2];
        }
        if (this.amount > 0) {
            this.description +=  this.amount + DESCRIPTIONS[3] + this.amount + DESCRIPTIONS[4];
            this.type = PowerType.BUFF;
        } else {
            int tmp = -this.amount;
            this.description +=  tmp + DESCRIPTIONS[5] + tmp + DESCRIPTIONS[6];
            this.type = PowerType.DEBUFF;
        }

    }

    public float modifyBlock(float blockAmount, AbstractCard c) {
        if (c instanceof AbstractMagicGremoryCard) {
            return (blockAmount += (float) this.amount) < 0.0F ? 0.0F : blockAmount;
        } else return blockAmount;
    }


    @Override
    public float modifyMagicCard(AbstractMagicGremoryCard c, float magicpower) {
        if (c instanceof AbstractMagicGremoryCard) {
            return magicpower + this.amount;
        } else return magicpower;
    }
}

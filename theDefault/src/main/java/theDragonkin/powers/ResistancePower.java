package theDragonkin.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import theDragonkin.util.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.cards.Gremory.AbstractMagicGremoryCard;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DefaultMod.makePowerPath;

public class ResistancePower extends TwoAmountPower implements modifyMagicPower, NonStackablePower {
    public static final String POWER_ID = DefaultMod.makeID("resistance");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("LoptyrianShadow.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("LoptyrianShadow32.png"));
    public ResistancePower(AbstractCreature owner, int amount, int duration) {
        this.amount = duration;
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount2 = amount;
        if (this.amount2 >= 999) {
            this.amount2 = 999;
        }
        isTurnBased = true;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        this.updateDescription();
        this.type = PowerType.DEBUFF;
    }

    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("ATTACK_MAGIC_SLOW_3", 0.05F);
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount2 += stackAmount;
        if (this.amount2 == 0) {
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }

        if (this.amount2 >= 999) {
            this.amount2 = 999;
        }

    }

    public void reducePower(int reduceAmount) {
        this.fontScale = 8.0F;
        this.amount2 -= reduceAmount;
        if (this.amount2 == 0) {
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }

        if (this.amount2 >= 999) {
            this.amount2 = 999;
        }
    }
    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType type, AbstractCard c) {
        if (c.hasTag(CustomTags.Enchanted)) {
            return damage + amount;
        }else return damage;
    }

    public void atEndOfTurn (final boolean isplayer){
        if (amount == 1) {
            addToBot(new ReducePowerAction(owner, owner, this.ID, amount));
        }
        addToBot(new ReducePowerAction(AbstractDungeon.player,AbstractDungeon.player,this,1));
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
    @Override
    public float modifyMagicCard(AbstractMagicGremoryCard c, float magicpower) {
        return magicpower + this.amount;
    }
}

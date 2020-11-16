package theDragonkin.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.CustomTags;
import theDragonkin.cards.Gremory.AbstractMagicGremoryCard;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DefaultMod.makePowerPath;

public class ChargedUp extends AbstractPower {
    public static final String POWER_ID = "Charged-Up";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public boolean Remove;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("Ashfall.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("Ashfall32.png"));
    public ChargedUp(AbstractCreature owner, int amount, boolean Temporary) {
        this.name = NAME;
        this.ID = "Charged-Up";
        this.owner = owner;
        this.amount = amount;
        this.Remove = Temporary;

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
    @Override
    public void atEndOfTurn(final boolean isPlayer) {
        if (Remove){
            addToBot(new ReducePowerAction(AbstractDungeon.player,AbstractDungeon.player,this,this.amount));
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

    }

    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard c) {
        if (c.hasTag(CustomTags.Thunder)) {
            return type == DamageInfo.DamageType.NORMAL ? damage + (float) this.amount : ((AbstractMagicGremoryCard) c).MagDamage;
        }
        else return damage;
    }
    public float modifyBlock(float blockAmount, AbstractCard c) {
        if (c.hasTag(CustomTags.Thunder)) {
            return (blockAmount += (float) this.amount) < 0.0F ? 0.0F : blockAmount;
        }
        else return blockAmount;
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Charged Up");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}

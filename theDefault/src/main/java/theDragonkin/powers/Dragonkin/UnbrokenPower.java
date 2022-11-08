package theDragonkin.powers.Dragonkin;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theDragonkin.DragonkinMod;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DragonkinMod.makePowerPath;

public class UnbrokenPower extends TwoAmountPower implements CloneablePowerInterface, OnReceivePowerPower {
    public AbstractCreature source;

    public static final String POWER_ID = DragonkinMod.makeID("Unbroken");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("AcidArmor.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("AcidArmor32.png"));

    public UnbrokenPower(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.amount2 = amount2;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL && owner.hasPower(WeakPower.POWER_ID)) {
            return damage * (1.0F + (amount/100F));
        } else {
            return damage;
        }
    }
    public float modifyBlock(float blockAmount) {
        if (owner.hasPower(FrailPower.POWER_ID)) {
            return blockAmount * (1.0F + (amount/100F));
        } else {
            return blockAmount;
        }
    }
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[3]+ DESCRIPTIONS[4];
    }
    @Override
    public AbstractPower makeCopy() {
        return new AcidArmorpower(owner, source, amount);
    }

    @Override
    public boolean onReceivePower(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        if (abstractPower instanceof UnbrokenPower){
            amount2 += ((UnbrokenPower) abstractPower).amount2;
        }
        return true;
    }
}

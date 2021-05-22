package theDragonkin.powers.Dragonkin;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.FlyingOrbEffect;
import com.megacrit.cardcrawl.vfx.combat.HemokinesisEffect;
import theDragonkin.DragonkinMod;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DragonkinMod.makePowerPath;

public class VioletHazePower extends TwoAmountPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DragonkinMod.makeID("VioletHaze");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("AcidArmor.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("AcidArmor32.png"));

    public VioletHazePower(final AbstractCreature owner, final AbstractCreature source, final int amount,final  int amount2) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.amount2 += amount2;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }
    @Override
    public void onApplyPower(AbstractPower p, AbstractCreature t, AbstractCreature s){
        if (p.type == PowerType.DEBUFF && !p.ID.equals("Shackled") && source == this.owner && t!= this.owner && !t.hasPower("Artifact") && p instanceof CloneablePowerInterface) {
            this.flash();
            addToBot(new DamageAction(s,new DamageInfo(s,amount, DamageInfo.DamageType.NORMAL)));
            addToBot(new VFXAction(new HemokinesisEffect(s.drawX,s.drawY,t.drawX,t.drawY)));
            p.amount += amount2;
        }
    }
    @Override
    public void atEndOfTurn(final boolean isPlayer){
        addToBot(new RemoveSpecificPowerAction(owner,owner,this));
    }
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount2 + DESCRIPTIONS[2];
    }
    @Override
    public AbstractPower makeCopy() {
        return new VioletHazePower(owner, source, amount,amount2);
    }
}

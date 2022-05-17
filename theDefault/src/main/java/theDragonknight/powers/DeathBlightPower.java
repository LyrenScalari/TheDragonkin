package theDragonknight.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import theDragonknight.DragonknightMod;
import theDragonknight.actions.SmiteAction;

public class DeathBlightPower extends TwoAmountPower implements CloneablePowerInterface, HealthBarRenderPower {
    public AbstractCreature source;
    public static final String POWER_ID = DragonknightMod.makeID("DeathBlight");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    public DeathBlightPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.DEBUFF;
        isTurnBased = false;

        // We load those textures here.
        this.loadRegion("end_turn_death");

        updateDescription();
    }
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + 8 + DESCRIPTIONS[1] + DESCRIPTIONS[2] + DESCRIPTIONS[3];
    }

    @Override
    public AbstractPower makeCopy() {
        return new DeathBlightPower(owner, source, amount);
    }

    @Override
    public int getHealthBarAmount() {
        updateDescription();
        return amount;
    }
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(this.amount), x, y, this.fontScale, c);
    }
    @Override
    public int onAttacked(DamageInfo di, int d){
        if (this.amount >= owner.currentHealth-d && owner instanceof AbstractMonster){
            CardCrawlGame.sound.play("POWER_MANTRA", 0.05F);
            addToBot(new SmiteAction(owner,new DamageInfo(owner,999999, DamageInfo.DamageType.HP_LOSS)));
            addToBot(new InstantKillAction(owner));
        } else if (this.amount >= owner.currentHealth-d ){
            CardCrawlGame.sound.play("POWER_MANTRA", 0.05F);
            addToBot(new SmiteAction(owner,new DamageInfo(owner,999999, DamageInfo.DamageType.HP_LOSS)));
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
        return d;
    }
    public void onInitialApplication() {
        if (this.amount >= owner.currentHealth && owner instanceof AbstractMonster){
            CardCrawlGame.sound.play("POWER_MANTRA", 0.05F);
            addToBot(new SmiteAction(owner,new DamageInfo(owner,999999, DamageInfo.DamageType.HP_LOSS)));
            addToBot(new InstantKillAction(owner));
        } else if (this.amount >= owner.currentHealth){
            CardCrawlGame.sound.play("POWER_MANTRA", 0.05F);
            addToBot(new SmiteAction(owner,new DamageInfo(owner,999999, DamageInfo.DamageType.HP_LOSS)));
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    @Override
    public int onLoseHp(int damageAmount) {
        if (this.amount >= owner.currentHealth && owner instanceof AbstractMonster){
            CardCrawlGame.sound.play("POWER_MANTRA", 0.05F);
            addToBot(new SmiteAction(owner,new DamageInfo(owner,999999, DamageInfo.DamageType.HP_LOSS)));
            addToBot(new InstantKillAction(owner));
        } else if (this.amount >= owner.currentHealth){
            CardCrawlGame.sound.play("POWER_MANTRA", 0.05F);
            addToBot(new SmiteAction(owner,new DamageInfo(owner,999999, DamageInfo.DamageType.HP_LOSS)));
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
        return damageAmount;
    }
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount >= owner.currentHealth && owner instanceof AbstractMonster){
            CardCrawlGame.sound.play("POWER_MANTRA", 0.05F);
            addToBot(new SmiteAction(owner,new DamageInfo(owner,999999, DamageInfo.DamageType.HP_LOSS)));
            addToBot(new InstantKillAction(owner));
        } else if (this.amount >= owner.currentHealth){
            CardCrawlGame.sound.play("POWER_MANTRA", 0.05F);
            addToBot(new SmiteAction(owner,new DamageInfo(owner,999999, DamageInfo.DamageType.HP_LOSS)));
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    @Override
    public Color getColor() {
        return Color.BLACK;
    }
}
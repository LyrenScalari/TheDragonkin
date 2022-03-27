package theDragonkin.powers.WindWalker;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import jdk.nashorn.internal.ir.annotations.Ignore;
import theDragonkin.DragonkinMod;
import theDragonkin.Stances.Tempest;

public class SpiritWound extends AbstractPower implements HealthBarRenderPower {
    public AbstractCreature source;

    public static final String POWER_ID = DragonkinMod.makeID("SpiritWound");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.

    public SpiritWound(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;
        this.loadRegion("pressure_points");
        type = PowerType.DEBUFF;
        isTurnBased = false;

        // We load those txtures here.

        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = powerStrings.DESCRIPTIONS[0];
    }
    @Override
    public int onAttacked(DamageInfo di, int d){
        if (this.amount >= owner.currentHealth-d && owner instanceof AbstractMonster){
            addToBot(new VFXAction(new LightningEffect(owner.drawX,owner.drawY)));
            addToBot(new VFXAction(new LightningEffect(owner.drawX,owner.drawY)));
            addToBot(new VFXAction(new ExplosionSmallEffect(owner.drawX,owner.drawY)));
            addToBot(new InstantKillAction(owner));
        } else if (this.amount >= owner.currentHealth-d ){
            addToBot(new VFXAction(new LightningEffect(owner.drawX,owner.drawY)));
            addToBot(new VFXAction(new LightningEffect(owner.drawX,owner.drawY)));
            addToBot(new VFXAction(new ExplosionSmallEffect(owner.drawX,owner.drawY)));
            this.addToBot(new LoseHPAction(this.owner, this.owner, 99999));
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
        return d;
    }
    public void onInitialApplication() {
        if (this.amount >= owner.currentHealth && owner instanceof AbstractMonster){
            addToBot(new VFXAction(new LightningEffect(owner.drawX,owner.drawY)));
            addToBot(new VFXAction(new LightningEffect(owner.drawX,owner.drawY)));
            addToBot(new VFXAction(new ExplosionSmallEffect(owner.drawX,owner.drawY)));
            addToBot(new InstantKillAction(owner));
        } else if (this.amount >= owner.currentHealth){
            addToBot(new VFXAction(new LightningEffect(owner.drawX,owner.drawY)));
            addToBot(new VFXAction(new LightningEffect(owner.drawX,owner.drawY)));
            addToBot(new VFXAction(new ExplosionSmallEffect(owner.drawX,owner.drawY)));
            this.addToBot(new LoseHPAction(this.owner, this.owner, 99999));
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    @Override
    public int onLoseHp(int damageAmount) {
        if (this.amount >= owner.currentHealth && owner instanceof AbstractMonster){
            addToBot(new VFXAction(new LightningEffect(owner.drawX,owner.drawY)));
            addToBot(new VFXAction(new LightningEffect(owner.drawX,owner.drawY)));
            addToBot(new VFXAction(new ExplosionSmallEffect(owner.drawX,owner.drawY)));
            addToBot(new InstantKillAction(owner));
        } else if (this.amount >= owner.currentHealth){
            addToBot(new VFXAction(new LightningEffect(owner.drawX,owner.drawY)));
            addToBot(new VFXAction(new LightningEffect(owner.drawX,owner.drawY)));
            addToBot(new VFXAction(new ExplosionSmallEffect(owner.drawX,owner.drawY)));
            this.addToBot(new LoseHPAction(this.owner, this.owner, 99999));
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
        return damageAmount;
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount >= owner.currentHealth && owner instanceof AbstractMonster){
            addToBot(new VFXAction(new LightningEffect(owner.drawX,owner.drawY)));
            addToBot(new VFXAction(new LightningEffect(owner.drawX,owner.drawY)));
            addToBot(new VFXAction(new ExplosionSmallEffect(owner.drawX,owner.drawY)));
            addToBot(new InstantKillAction(owner));
        } else if (this.amount >= owner.currentHealth){
            addToBot(new VFXAction(new LightningEffect(owner.drawX,owner.drawY)));
            addToBot(new VFXAction(new LightningEffect(owner.drawX,owner.drawY)));
            addToBot(new VFXAction(new ExplosionSmallEffect(owner.drawX,owner.drawY)));
            this.addToBot(new LoseHPAction(this.owner, this.owner, 99999));
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }
    @Override
    public int getHealthBarAmount() {
        return this.amount;
    }

    @Override
    public Color getColor() {
        return CardHelper.getColor(135,156,149);
    }
}

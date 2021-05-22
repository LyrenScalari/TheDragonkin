package theDragonkin.powers.Dragonkin;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.DragonkinMod;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DragonkinMod.makePowerPath;

public class PenancePower extends TwoAmountPower implements CloneablePowerInterface, HealthBarRenderPower {
    public AbstractCreature source;
    public static final String POWER_ID = DragonkinMod.makeID("Penance");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public int delay = 2;


    public PenancePower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.DEBUFF;
        isTurnBased = false;

        // We load those textures here.
        this.loadRegion("heartDef");

        updateDescription();
    }
    @Override
    public void onInitialApplication() {
        amount2 = 8;
        updateDescription();
    }
    @Override
    public void atEndOfTurn(final boolean isplayer){
        if (!isplayer && amount >= amount2) {
            AbstractPower bonus = AbstractDungeon.player.getPower(SinnersBurdenPower.POWER_ID);
            if ((bonus != null)){
                addToBot(new LoseHPAction(owner,owner,20 + bonus.amount, AbstractGameAction.AttackEffect.SMASH));
            } else addToBot(new LoseHPAction(owner,owner,20, AbstractGameAction.AttackEffect.SMASH));
            amount -= amount2;
            addToBot(new ApplyPowerAction(owner,AbstractDungeon.player,new SinnersBurdenPower(owner,AbstractDungeon.player,amount2),6));
            if (amount < 0){
                amount = 0;
            }
            updateDescription();
        }
    }
    @Override
    public void updateDescription() {
        AbstractPower bonus = AbstractDungeon.player.getPower(SinnersBurdenPower.POWER_ID);
        if ((bonus != null)){
            description = DESCRIPTIONS[0] + amount2 + DESCRIPTIONS[1] + (20+bonus.amount) + DESCRIPTIONS[2] + DESCRIPTIONS[3];
        } else description = DESCRIPTIONS[0] + amount2 + DESCRIPTIONS[1] + 20 + DESCRIPTIONS[2] + DESCRIPTIONS[3];
    }

    @Override
    public AbstractPower makeCopy() {
        return new PenancePower(owner, source, amount);
    }

    @Override
    public int getHealthBarAmount() {
        if (amount >= amount2){
            AbstractPower bonus = AbstractDungeon.player.getPower(SinnersBurdenPower.POWER_ID);
            if ((bonus != null)){
                return 33 + bonus.amount;
            } else return 33;
        }
        return 0;
    }

    @Override
    public Color getColor() {
        return Color.GOLD;
    }
}

package theDragonkin.powers.Dragonkin;

import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.DragonkinMod;


public class EndlessFlamePower extends TwoAmountPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DragonkinMod.makeID("EndlessFlame");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    public EndlessFlamePower(final AbstractCreature owner, final AbstractCreature source, final int power, int duration) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = duration;
        this.amount2 = power;
        this.source = source;
        this.loadRegion("flameBarrier");
        type = PowerType.DEBUFF;
        isTurnBased = false;

        // We load those txtures here.

        updateDescription();
    }
    @Override
    public void atStartOfTurn() {
        addToBot(new ApplyPowerAction(owner, AbstractDungeon.player,new Scorchpower(owner,AbstractDungeon.player,amount2)));
        addToBot(new MakeTempCardInDiscardAction(new Burn(),1));
        addToBot(new ReducePowerAction(owner,owner,this,1));
    }
    @Override
    public void updateDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(DESCRIPTIONS[0]);
        sb.append(amount);
        if (amount > 1){
            sb.append(DESCRIPTIONS[2]);
        } else sb.append(DESCRIPTIONS[1]);
        sb.append(DESCRIPTIONS[3]);
        sb.append(amount2);
        sb.append(DESCRIPTIONS[4]);
        this.description = sb.toString();
    }

    @Override
    public AbstractPower makeCopy() {
        return new EndlessFlamePower(owner, source, amount2,amount);
    }
}
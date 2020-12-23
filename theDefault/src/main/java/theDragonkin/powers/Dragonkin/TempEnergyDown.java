package theDragonkin.powers.Dragonkin;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.DefaultMod;

public class TempEnergyDown extends TwoAmountPower implements OnReceivePowerPower {
    public static final String POWER_ID = DefaultMod.makeID("TempEnergyDown");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static int EnergyAdjust = 0;
    public TempEnergyDown(AbstractCreature owner, int Energy, int duration, boolean isFasting) {
        this.name = powerStrings.NAME;
        this.ID = "EnergyDownPower";
        this.owner = owner;
        this.amount =  Energy;
        this.amount2 = duration;
        this.updateDescription();
        if (isFasting) {
            this.loadRegion("fasting");
        } else {
            this.loadRegion("energized_blue");
        }

        this.type = PowerType.DEBUFF;
    }

    public void updateDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(powerStrings.DESCRIPTIONS[0]);

        for(int i = 0; i < this.amount; ++i) {
            sb.append("[E] ");
        }

        this.description = sb.toString();
    }

    public void atStartOfTurn() {
        this.addToBot(new LoseEnergyAction(this.amount));
        addToBot(new ReducePowerAction(owner,owner,this,EnergyAdjust));
        EnergyAdjust = 0;
        this.amount2 -= 1;
        if (amount2 <= 0){
            addToBot(new RemoveSpecificPowerAction(owner,owner,this));
        }
        this.flash();
    }

    @Override
    public boolean onReceivePower(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        if (abstractPower.ID.equals(this.ID)){
            EnergyAdjust = amount;
            int amount2add = ((TempEnergyDown)abstractPower).amount2;
            if (amount2add > amount2){
                amount2 += amount2add;
            }
        }

        return true;
    }
}

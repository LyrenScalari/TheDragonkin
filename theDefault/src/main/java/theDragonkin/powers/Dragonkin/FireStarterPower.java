package theDragonkin.powers.Dragonkin;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import theDragonkin.DefaultMod;

public class FireStarterPower extends TwoAmountPower implements NonStackablePower {
    public static final String POWER_ID = DefaultMod.makeID("FireStarterPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static int EnergyAdjust = 0;
    public FireStarterPower(AbstractCreature owner, int Energy, int duration, boolean isFasting) {
        this.name = powerStrings.NAME;
        this.ID = "FireStarterPower";
        this.owner = owner;
        this.amount =  Energy;
        this.amount2 = duration;
        this.updateDescription();
        if (isFasting) {
            this.loadRegion("fasting");
        } else {
            this.loadRegion("firebreathing");
        }

        this.type = PowerType.BUFF;
    }

    public void updateDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(powerStrings.DESCRIPTIONS[0]);
        sb.append(amount2);
        sb.append(powerStrings.DESCRIPTIONS[1]);
        for(int i = 0; i < this.amount; ++i) {
            sb.append("[E] ");
        }

        this.description = sb.toString();
    }

    public void atStartOfTurn() {

        this.amount2 -= 1;
        if (amount2 <= 0){
            addToBot(new RemoveSpecificPowerAction(owner,owner,this));
        }
        this.flash();
    }
}
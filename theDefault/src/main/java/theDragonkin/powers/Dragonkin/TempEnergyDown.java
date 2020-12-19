package theDragonkin.powers.Dragonkin;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import theDragonkin.DefaultMod;

public class TempEnergyDown extends TwoAmountPower {
    public static final String POWER_ID = DefaultMod.makeID("TempEnergyDown");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public TempEnergyDown(AbstractCreature owner, int amount1, int amount2, boolean isFasting) {
        this.name = powerStrings.NAME;
        this.ID = "EnergyDownPower";
        this.owner = owner;
        this.amount = amount1;
        this.amount2 = amount2;
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
        addToBot(new ReducePowerAction(owner,owner,this,1));
        this.flash();
    }
}

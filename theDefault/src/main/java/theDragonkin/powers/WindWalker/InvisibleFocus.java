package theDragonkin.powers.WindWalker;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

public class InvisibleFocus extends FocusPower implements InvisiblePower, NonStackablePower {
    public InvisibleFocus(AbstractCreature owner) {
        super(owner, 0);
        this.ID = FocusPower.POWER_ID;
        priority = 0;
        amount = 0;
    }
    @Override
    public void stackPower(int stackAmount) {
    }

}

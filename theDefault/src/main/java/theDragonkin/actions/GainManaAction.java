package theDragonkin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theDragonkin.patches.ChiField;
import theDragonkin.patches.ManaField;
import theDragonkin.powers.WindWalker.GainChiPower;
import theDragonkin.util.Wiz;

public class GainManaAction extends AbstractGameAction {
    public GainManaAction(AbstractCreature source,int amount) {
        this.setValues(target, source, amount);
        this.actionType = ActionType.ENERGY;
        this.amount = amount;
    }

    public void update() {
        if (this.duration == 0.5F) {
            ManaField.Mana.set(AbstractDungeon.player,ManaField.Mana.get(AbstractDungeon.player)+amount);
        }
        this.tickDuration();
    }
}
package theDragonknight.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theDragonknight.powers.WindWalker.GainChiPower;
import theDragonknight.util.Wiz;

public class GainChiAction extends AbstractGameAction {
    public GainChiAction(AbstractCreature source, int amount) {
        this.setValues(target, source, amount);
        this.actionType = ActionType.ENERGY;
        this.amount = amount;
    }

    public void update() {
        if (this.duration == 0.5F) {
            for (AbstractPower p : AbstractDungeon.player.powers){
                if (p instanceof GainChiPower){
                    ((GainChiPower) p).onGainChi(amount);
                }
            }
            if (ChiField.Chi.get(source)+amount > 5){
                Wiz.applyToSelfTemp(new FocusPower(source,(ChiField.Chi.get(source)+amount)-5));
                Wiz.applyToSelfTemp(new StrengthPower(source,(ChiField.Chi.get(source)+amount)-5));
                ChiField.Chi.set(source,5);
            }else{
                ChiField.Chi.set(source,ChiField.Chi.get(source)+amount);
            }

        }
        this.tickDuration();
    }
}
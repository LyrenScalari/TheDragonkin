package theDragonkin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theDragonkin.CustomTags;
import theDragonkin.patches.ChiField;

import java.util.function.Supplier;

public class ChiBurstAction extends AbstractGameAction {
    int amt;
    Supplier<AbstractGameAction> FluxEffect;

    public ChiBurstAction(int Amt, Supplier<AbstractGameAction> ChiBurstEffect) {
        amt = Amt;
        this.FluxEffect = ChiBurstEffect;
    }

    @Override
    public void update() {
        amt--;
        if (ChiField.Chi.get(AbstractDungeon.player) > 0) {
            if (amt > 0) {
                addToTop(new ChiBurstAction(amt, FluxEffect));
            }
            addToTop(FluxEffect.get());
            addToTop(new GainChiAction(AbstractDungeon.player, -1));
        }
        isDone =true;
    }
}
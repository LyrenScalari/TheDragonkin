package theDragonkin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theDragonkin.powers.Dragonkin.Scorchpower;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class FluxAction extends AbstractGameAction {
    int amt;
    Supplier<AbstractGameAction> FluxEffect;

    public FluxAction(int Amt, Supplier<AbstractGameAction> FluxEffect) {
        amt = Amt;
        this.FluxEffect = FluxEffect;
    }

    @Override
    public void update() {
        AbstractCard ca = null;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.type == AbstractCard.CardType.STATUS) {
                ca = c;
                break;
            }
        }
        if (ca != null) {
            amt--;
            if (amt > 0) {
                addToTop(new FluxAction(amt, FluxEffect));
            }
            addToTop(new CycleAction(ca, 1));
            addToTop(FluxEffect.get());
        }
        isDone = true;
    }
}
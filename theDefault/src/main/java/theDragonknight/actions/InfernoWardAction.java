package theDragonknight.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theDragonknight.CustomTags;

import java.util.function.Supplier;

public class InfernoWardAction extends AbstractGameAction {
    int amt;
    Supplier<AbstractGameAction> FluxEffect;

    public InfernoWardAction(int Amt, Supplier<AbstractGameAction> FluxEffect) {
        amt = Amt;
        this.FluxEffect = FluxEffect;
    }
    public InfernoWardAction(int Amt) {
        super();
        amt = Amt;
    }
    @Override
    public void update() {
        AbstractCard ca = null;
        System.out.println(amt);
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.type == AbstractCard.CardType.STATUS || c.hasTag(CustomTags.Rune)) {
                ca = c;
                break;
            }
        }
        if (ca != null) {
            amt--;
            if (amt > 0) {
                addToTop(new InfernoWardAction(amt, FluxEffect));
            }
            if (AbstractDungeon.player.drawPile.size() < 1){
                addToTop(new ExhaustSpecificCardAction(ca,AbstractDungeon.player.drawPile));
            } else {
                addToTop(new ExhaustSpecificCardAction(ca,AbstractDungeon.player.discardPile));
            }
            addToTop(new CycleAction(ca, 1));
            if (FluxEffect != null) {
                addToTop(FluxEffect.get());
            }
        }
        isDone = true;
    }
}
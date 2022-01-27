package theDragonkin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.lwjgl.Sys;
import theDragonkin.CustomTags;

import java.util.function.Supplier;

public class LightspeedFluxAction extends AbstractGameAction {
    int amt;
    Supplier<AbstractGameAction> FluxEffect;
    AbstractCard card;
    public LightspeedFluxAction(int Amt, Supplier<AbstractGameAction> FluxEffect,AbstractCard callingcard) {
        amt = Amt;
        this.FluxEffect = FluxEffect;
        card = callingcard;
    }

    @Override
    public void update() {
        AbstractCard ca = null;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.type == AbstractCard.CardType.STATUS) {
                if (c == card){
                    ca = c;
                } else {
                    ca = c;
                    break;
                }
            }
        }
        if (ca != null) {
            amt--;
            if (amt > 0) {
                addToTop(new LightspeedFluxAction(amt, FluxEffect,card));
            }
            addToTop(new CycleAction(ca, 1));
            addToTop(FluxEffect.get());
            if (amt == 0){
                if (AbstractDungeon.player.hand.contains(card)){
                    addToBot(new DiscardSpecificCardAction(card));
                }
            }
        }
        isDone = true;
    }
}
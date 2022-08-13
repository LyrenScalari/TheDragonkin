package theDragonkin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theDragonkin.DragonkinMod;

public class CycleAction extends AbstractGameAction {
    private AbstractCard targetCard;
    private int drawamt;
    private AbstractCard TransformCard;
    public CycleAction(AbstractCard c,int DrawAmt) {
        targetCard = c;
        drawamt = DrawAmt;
    }
    public CycleAction(AbstractCard c,int DrawAmt, AbstractCard Transforminto) {
        targetCard = c;
        drawamt = DrawAmt;
        TransformCard = Transforminto;
    }
    @Override
    public void update() {
        if (!AbstractDungeon.player.hand.contains(targetCard)){
            isDone = true;
        }
        if (TransformCard != null){
            AbstractDungeon.actionManager.addToTop(new AbstractGameAction() {
                @Override
                public void update() {
                    AbstractDungeon.player.drawPile.removeCard(targetCard);
                    AbstractDungeon.player.discardPile.removeCard(targetCard);
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(TransformCard.makeStatEquivalentCopy(),1));
                    isDone = true;
                }
            });
        }
        AbstractDungeon.actionManager.addToTop(new DrawCardAction(drawamt));
        AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(targetCard));
        isDone = true;
    }
}

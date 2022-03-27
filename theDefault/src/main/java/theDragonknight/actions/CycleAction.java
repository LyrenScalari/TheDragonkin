package theDragonknight.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theDragonknight.DragonkinMod;

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
        AbstractDungeon.actionManager.addToTop(new DrawCardAction(drawamt));
        DragonkinMod.TriggerOnCycle(targetCard);
        AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(targetCard));
        if (TransformCard != null){
            AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                @Override
                public void update() {
                    if (AbstractDungeon.player.drawPile.size() < 1){
                        AbstractDungeon.player.drawPile.removeCard(targetCard);
                    } else {
                        AbstractDungeon.player.discardPile.removeCard(targetCard);
                    }
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(TransformCard.makeStatEquivalentCopy(),1));
                    isDone = true;
                }
            });
        }
        isDone = true;
    }
}

package theDragonkin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theDragonkin.DragonkinMod;

public class CycleAction extends AbstractGameAction {
    private AbstractCard targetCard;
    private int drawamt;
    public CycleAction(AbstractCard c,int DrawAmt) {
        targetCard = c;
        drawamt = DrawAmt;
    }
    @Override
    public void update() {
        AbstractDungeon.actionManager.addToTop(new DrawCardAction(drawamt));
        DragonkinMod.TriggerOnCycle(targetCard);
        AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(targetCard));
        isDone = true;
    }
}

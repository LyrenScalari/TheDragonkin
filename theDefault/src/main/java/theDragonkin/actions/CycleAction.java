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
        AbstractDungeon.actionManager.addToBottom(new DiscardSpecificCardAction(targetCard));
        DragonkinMod.TriggerOnCycle(targetCard);
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(drawamt));
        isDone = true;
    }
}

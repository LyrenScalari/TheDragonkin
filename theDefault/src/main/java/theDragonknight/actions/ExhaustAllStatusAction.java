package theDragonknight.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


public class ExhaustAllStatusAction extends AbstractGameAction {
    public ExhaustAllStatusAction() {
        this.actionType = ActionType.WAIT;
    }

    public void update() {

        for (AbstractCard card : AbstractDungeon.player.hand.group) {
            if (card.type == AbstractCard.CardType.STATUS) {
                this.addToTop(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));

            }
        }
        this.isDone = true;
    }
}

package theDragonknight.actions;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theDragonknight.CardMods.BurnAfterReadingCardMod;

import java.util.Iterator;

public class BurnAfterReadingFollowUpAction extends AbstractGameAction {
    public BurnAfterReadingFollowUpAction() {
        this.duration = 0.001F;
    }

    public void update() {
        AbstractDungeon.actionManager.addToTop(new WaitAction(0.4F));
        this.tickDuration();
        if (this.isDone) {
            Iterator var1 = DrawCardAction.drawnCards.iterator();
            for (AbstractCard c : DrawCardAction.drawnCards) {
                if (!c.exhaust) {
                    addToBot(new AbstractGameAction() {
                        public void update() {
                            CardModifierManager.addModifier(c, new BurnAfterReadingCardMod(1));
                            isDone = true;
                        }
                    });
                }
            }
        }
    }
}

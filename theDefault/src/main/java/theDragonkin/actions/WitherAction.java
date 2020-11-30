package theDragonkin.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theDragonkin.cards.Gremory.AbstractMagicGremoryCard;


public class WitherAction extends AbstractGameAction {
    private AbstractCard card;
    private int miscIncrease;

    public WitherAction(AbstractMagicGremoryCard card) {
        this.miscIncrease = -card.witherAmount;
        this.card = card;
    }

    public void update() {

        this.card.superFlash(Color.RED);

        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.uuid.equals(card.uuid)) {
                c.misc += this.miscIncrease;
                c.applyPowers();
                break;
            }
        }

        card.misc += this.miscIncrease;
        card.applyPowers();
        this.isDone = true;
    }
}

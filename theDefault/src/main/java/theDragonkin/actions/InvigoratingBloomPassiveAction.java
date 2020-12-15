package theDragonkin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class InvigoratingBloomPassiveAction extends AbstractGameAction {
    public InvigoratingBloomPassiveAction() {
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {

        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            c.setCostForTurn(-1);
        }

        this.isDone = true;
    }
}

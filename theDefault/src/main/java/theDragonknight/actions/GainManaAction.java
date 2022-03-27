package theDragonknight.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theDragonknight.patches.ManaField;

public class GainManaAction extends AbstractGameAction {
    public GainManaAction(AbstractCreature source,int amount) {
        this.setValues(target, source, amount);
        this.actionType = ActionType.ENERGY;
        this.amount = amount;
    }

    public void update() {
        if (this.duration == 0.5F) {
            ManaField.Mana.set(AbstractDungeon.player,ManaField.Mana.get(AbstractDungeon.player)+amount);
        }
        this.tickDuration();
    }
}
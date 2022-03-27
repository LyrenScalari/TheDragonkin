package theDragonknight.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.HealEffect;
import theDragonknight.patches.DivineArmorPatches.DivineArmorField;

public class GainDivineArmorAction extends AbstractGameAction {
    public GainDivineArmorAction(AbstractCreature target, AbstractCreature source, int amount) {
        this.setValues(target, source, amount);
        this.actionType = ActionType.BLOCK;
    }

    public void update() {
        if (this.duration == 0.5F) {
            DivineArmorField.DivineArmor.set(this.target, (Integer)DivineArmorField.DivineArmor.get(this.target) + this.amount);
            if (this.amount > 0) {
                AbstractDungeon.effectsQueue.add(new HealEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY, this.amount));
                this.target.healthBarUpdatedEvent();
            }
        }
        this.tickDuration();
    }
}
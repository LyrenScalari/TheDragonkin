package theDragonkin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlyingOrbEffect;
import com.megacrit.cardcrawl.vfx.combat.HealEffect;
import theDragonkin.patches.DivineArmorPatches.DivineArmorField;
import theDragonkin.patches.Phlyactery.PhlyacteryField;

public class GainBloodPointsAction extends AbstractGameAction {
    public GainBloodPointsAction(AbstractCreature target, AbstractCreature source, int amount) {
        this.setValues(target, source, amount);
        this.actionType = ActionType.BLOCK;
    }

    public void update() {
        if (this.duration == 0.5F) {
            PhlyacteryField.BloodPoints.set(this.target, (Integer) PhlyacteryField.BloodPoints.get(this.target) + this.amount);
            AbstractDungeon.effectsQueue.add(new FlyingOrbEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY));
            this.target.healthBarUpdatedEvent();
        }
        this.tickDuration();
    }
}

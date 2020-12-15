package theDragonkin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

public class NagaCure extends AbstractGameAction {
    private AbstractCreature c;
    private static int removecount;
    private static ArrayList<AbstractPower> Debuffs = new ArrayList<>();
    public AbstractPower DebufftoRemove;

    public NagaCure(AbstractCreature target) {
        this.target = target;
        this.duration = 0.5F;
        for (AbstractPower p : this.target.powers) {
            if (p.type == AbstractPower.PowerType.DEBUFF) {
                Debuffs.add(p);
            }
        }
    }

    public void update() {
        if (Debuffs.size() > 0) {
            for (AbstractPower p : target.powers) {
                if (p.type == AbstractPower.PowerType.DEBUFF) {
                    this.addToTop(new GainBlockAction(AbstractDungeon.player, p.amount));
                    this.addToTop(new RemoveSpecificPowerAction(this.c, this.c, p.ID));
                }
            }

            this.isDone = true;
        }
        isDone = true;
    }
}

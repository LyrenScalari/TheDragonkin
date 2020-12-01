package theDragonkin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class CureandHealAction extends AbstractGameAction {
    private AbstractCreature c;
    private static int removecount;
    private static ArrayList<AbstractPower> Debuffs = new ArrayList<>();;
    public AbstractPower DebufftoRemove;

    public CureandHealAction(AbstractCreature target,int numbertoRemove) {
        this.target = target;
        removecount = numbertoRemove;
        this.duration = 0.5F;
        for (AbstractPower p : this.target.powers) {
            if (p.type == AbstractPower.PowerType.DEBUFF) {
                Debuffs.add(p);
            }
        }
    }

    public void update() {
        for (int i = 0; i < removecount; ++i) {
            DebufftoRemove = Debuffs.remove((AbstractDungeon.cardRandomRng.random(Debuffs.size()-1)));
            addToTop(new HealAction(AbstractDungeon.player,AbstractDungeon.player,DebufftoRemove.amount));
            addToTop(new RemoveSpecificPowerAction(target,AbstractDungeon.player,DebufftoRemove));
        }
        isDone = true;
    }
}


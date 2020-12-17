package theDragonkin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.powers.GroveKeeper.AlignmentPower;
import theDragonkin.powers.GroveKeeper.Moonstruck;
import theDragonkin.powers.GroveKeeper.Sunburn;

public class ConvertAlignmentAction  extends AbstractGameAction {
    public static AlignmentPower Alignment;
    public static boolean Half;
    public ConvertAlignmentAction(boolean half) {
        this.duration = 0.5F;
        Half = half;
        if (AbstractDungeon.player.hasPower(AlignmentPower.POWER_ID)){
            Alignment = (AlignmentPower) AbstractDungeon.player.getPower(AlignmentPower.POWER_ID);
        }
    }

    public void update() {
        if (Alignment != null){
            Alignment.ConvertAlignment(Half);
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
                for (AbstractPower p : m.powers){
                    if (p instanceof Sunburn || p instanceof Moonstruck){
                        p.updateDescription();
                    }
                }
            }
            this.isDone = true;
        }
        this.isDone = true;
    }
}

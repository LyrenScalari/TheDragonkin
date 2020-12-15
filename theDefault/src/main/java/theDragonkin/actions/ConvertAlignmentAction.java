package theDragonkin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.powers.GroveKeeper.AlignmentPower;

public class ConvertAlignmentAction  extends AbstractGameAction {
    public static AbstractPower Alignment;
    public static boolean Half;
    public ConvertAlignmentAction(boolean half) {
        this.duration = 0.5F;
        Half = half;
        if (AbstractDungeon.player.hasPower(AlignmentPower.POWER_ID)){
            Alignment = AbstractDungeon.player.getPower(AlignmentPower.POWER_ID);
        }
    }

    public void update() {
        if (Alignment != null){
            ((AlignmentPower)Alignment).ConvertAlignment(Half);
            this.isDone = true;
        }
        this.isDone = true;
    }
}

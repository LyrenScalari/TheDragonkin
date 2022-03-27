package theDragonknight.orbs;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theDragonknight.DragonkinMod;
import theDragonknight.util.AbstractSeal;

public class MightSeal extends AbstractSeal {
    public static final String ORB_ID = DragonkinMod.makeID("Might");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;
    public MightSeal(int Pow, int Pain){
        super();
        name = orbString.NAME;
        PainAmount = Pain;
        BreakAmount = Pow;
    }
    public void Break() {
        super.Break();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new StrengthPower(AbstractDungeon.player,BreakAmount)));
    }
    public void updateDescription() {
        description = DESCRIPTIONS[0] + PainAmount + DESCRIPTIONS[1] + DESCRIPTIONS[2] + BreakAmount + DESCRIPTIONS[3];
    }
}
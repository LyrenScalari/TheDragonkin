package theDragonknight.orbs.DragonShouts;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import theDragonknight.DragonknightMod;
import theDragonknight.powers.FrostbitePower;
import theDragonknight.powers.RotPower;
import theDragonknight.util.AbstractDragonMark;
import theDragonknight.util.Wiz;

public class PlagueMark extends AbstractDragonMark {
    public static final String ORB_ID = DragonknightMod.makeID("MarkofPlague");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;
    public PlagueMark(AbstractCreature Owner){
        super();
        Sealstrings = orbString;
        owner = Owner;
        name = orbString.DESCRIPTION[8] + orbString.NAME;
        BreakAmount = 2;
        PlayerAmount = BasePlayerAmount = 3;
        updateAnimation();
    }
    public void onEndOfTurn() {
        if (owner != AbstractDungeon.player) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner,owner,new RotPower(owner,owner,BreakAmount)));
        } else {
            AbstractMonster target = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
            Wiz.applyToEnemy(target,new PoisonPower(target,owner,PlayerAmount));
        }
    }
    public AbstractDragonMark MakeCopy(){
        return new FlameMark(owner);
    }
    public void updateDescription() {
        if (owner != AbstractDungeon.player) {
            description = DESCRIPTIONS[5] + DESCRIPTIONS[6] + BreakAmount + DESCRIPTIONS[7] + owner.name;
        } else {
            description =  DESCRIPTIONS[1]+ DESCRIPTIONS[2] + DESCRIPTIONS[3] + PlayerAmount + DESCRIPTIONS[4];
        }
    }
}
package theDragonknight.orbs.DragonShouts;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
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
import theDragonknight.util.AbstractNotOrb;
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
        BreakAmount = baseBreakAmount = 2;
        PlayerAmount = BasePlayerAmount = 3;
        updateAnimation();
    }
    public void onEndOfTurn() {
        for (int i = 0; i < PainAmount ; i++) {
            TriggerPassive();
        }
    }
    public void TriggerPassive(){
        AbstractMonster target = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
        Wiz.applyToEnemy(target, new PoisonPower(target, owner, PlayerAmount));
    }
    public void WhenRemoved() {
        AbstractMonster target = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target,target,new RotPower(target,target,BreakAmount)));
        super.WhenRemoved();
    }
    public AbstractDragonMark MakeCopy(){
        return new FlameMark(owner);
    }
    public void updateDescription() {
        ApplyModifers();
        description = DESCRIPTIONS[1]+ DESCRIPTIONS[2] + DESCRIPTIONS[3] + PlayerAmount + DESCRIPTIONS[4] + DESCRIPTIONS[5] + DESCRIPTIONS[6] + BreakAmount + DESCRIPTIONS[7];
    }
}
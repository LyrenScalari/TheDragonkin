package theDragonknight.orbs.DragonShouts;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import theDragonknight.DragonknightMod;
import theDragonknight.actions.SmiteAction;
import theDragonknight.util.AbstractDragonMark;
import theDragonknight.util.AbstractNotOrb;

public class FlameMark extends AbstractDragonMark {
    public static final String ORB_ID = DragonknightMod.makeID("MarkofFlame");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;
    public FlameMark(AbstractCreature Owner){
        super();
        Sealstrings = orbString;
        owner = Owner;
        name = orbString.DESCRIPTION[8] + orbString.NAME;
        BreakAmount = baseBreakAmount = 7;
        PlayerAmount = BasePlayerAmount = 3;
        updateAnimation();
    }
    public void onStartOfTurn() {
        for (int i = 0; i < PainAmount ; i++) {
           TriggerPassive();
        }
    }
    public void WhenRemoved() {
        AbstractMonster target = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
        AbstractDungeon.actionManager.addToBottom(new DamageAction(target,new DamageInfo(AbstractDungeon.player,BreakAmount, DamageInfo.DamageType.THORNS)));
        super.WhenRemoved();
    }
    public void TriggerPassive(){
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new VigorPower(owner, PlayerAmount)));
    }

    public AbstractDragonMark MakeCopy(){
        return new FlameMark(owner);
    }
    public void updateDescription() {
        ApplyModifers();
        description =  DESCRIPTIONS[1]+ DESCRIPTIONS[2] + DESCRIPTIONS[3] + PlayerAmount + DESCRIPTIONS[4] + DESCRIPTIONS[5] + DESCRIPTIONS[6] + BreakAmount + DESCRIPTIONS[7];
    }
}
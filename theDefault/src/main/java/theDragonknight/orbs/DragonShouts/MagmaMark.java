package theDragonknight.orbs.DragonShouts;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import theDragonknight.DragonknightMod;
import theDragonknight.powers.BurnPower;
import theDragonknight.util.AbstractDragonMark;
import theDragonknight.util.Wiz;

public class MagmaMark extends AbstractDragonMark {
    public static final String ORB_ID = DragonknightMod.makeID("MarkofMagma");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;
    public MagmaMark(AbstractCreature Owner){
        super();
        Sealstrings = orbString;
        owner = Owner;
        name = orbString.DESCRIPTION[8] + orbString.NAME;
        BreakAmount = baseBreakAmount = 4;
        PlayerAmount = BasePlayerAmount = 3;
        updateAnimation();
    }
    public void onEndOfTurn() {
        for (int i = 0; i < PainAmount ; i++) {
            TriggerPassive();
        }
    }
    public void TriggerPassive(){
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(owner,PlayerAmount));
    }
    public void WhenRemoved() {
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
            Wiz.applyToEnemy(m,new BurnPower(m,owner,BreakAmount));
        }
        super.WhenRemoved();
    }
    public AbstractDragonMark MakeCopy(){
        return new MagmaMark(owner);
    }

    public void updateDescription() {
        ApplyModifers();
        description =  DESCRIPTIONS[1]+ DESCRIPTIONS[2] + DESCRIPTIONS[3] + PlayerAmount + DESCRIPTIONS[4] + DESCRIPTIONS[5] + DESCRIPTIONS[6] + BreakAmount + DESCRIPTIONS[7];
    }
}
package theDragonknight.orbs.DragonShouts;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import theDragonknight.DragonknightMod;
import theDragonknight.util.AbstractDragonMark;

public class MagmaMark extends AbstractDragonMark {
    public static final String ORB_ID = DragonknightMod.makeID("MarkofMagma");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;
    public MagmaMark(AbstractCreature Owner){
        super();
        Sealstrings = orbString;
        owner = Owner;
        name = orbString.DESCRIPTION[8] + orbString.NAME;
        BreakAmount = 3;
        PlayerAmount = BasePlayerAmount = 3;
        updateAnimation();
    }
    public void onEndOfTurn() {
        if (owner != AbstractDungeon.player) {
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(AbstractDungeon.player,BreakAmount, DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
        } else {
            AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player,PlayerAmount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
            BreakAmount += 1;
            baseBreakAmount += 1;
        }
    }
    public AbstractDragonMark MakeCopy(){
        return new MagmaMark(owner);
    }

    public void updateDescription() {
        if (owner != AbstractDungeon.player) {
            description = DESCRIPTIONS[5] + DESCRIPTIONS[6] + BreakAmount + DESCRIPTIONS[7];
        } else {
            description =  DESCRIPTIONS[1]+ DESCRIPTIONS[2] + DESCRIPTIONS[3] + PlayerAmount + DESCRIPTIONS[4];
        }
    }
}
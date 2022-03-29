package theDragonknight.orbs.DragonShouts;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import theDragonknight.DragonknightMod;
import theDragonknight.actions.SmiteAction;
import theDragonknight.util.AbstractDragonMark;

public class FlameMark extends AbstractDragonMark {
    public static final String ORB_ID = DragonknightMod.makeID("MarkofFlame");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;
    public FlameMark(){
        super();
        Sealstrings = orbString;
        owner = AbstractDungeon.player;
        name = orbString.NAME;
        BreakAmount = 7;
        PlayerAmount = BasePlayerAmount = 3;
        updateAnimation();
    }
    public void onEndOfTurn() {
        if (owner != AbstractDungeon.player) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(owner,new DamageInfo(AbstractDungeon.player,BreakAmount, DamageInfo.DamageType.THORNS)));
        }
    }
    public void onStartOfTurn() {
        if (owner == AbstractDungeon.player) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner,owner,new VigorPower(owner,PlayerAmount)));
        }
    }
    public void updateDescription() {
        if (owner != AbstractDungeon.player) {
            description = DESCRIPTIONS[5] + DESCRIPTIONS[6] + BreakAmount + DESCRIPTIONS[7] + owner.name;
        } else {
            description =  DESCRIPTIONS[1]+ DESCRIPTIONS[2] + DESCRIPTIONS[3] + PlayerAmount + DESCRIPTIONS[4];
        }
    }
}
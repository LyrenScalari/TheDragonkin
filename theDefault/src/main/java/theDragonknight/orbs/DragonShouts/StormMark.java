package theDragonknight.orbs.DragonShouts;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonknight.DragonknightMod;
import theDragonknight.actions.SmiteAction;
import theDragonknight.util.AbstractDragonMark;

public class StormMark extends AbstractDragonMark {
    public static final String ORB_ID = DragonknightMod.makeID("StormMark");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;
    public StormMark(int Pain){
        super();
        Sealstrings = orbString;
        owner = AbstractDungeon.player;
        name = orbString.NAME;
        BreakAmount = 7;
        PlayerAmount = BasePlayerAmount = 2;
        updateAnimation();
    }
    public void onEndOfTurn() {
        if (owner != AbstractDungeon.player) {
            AbstractDungeon.actionManager.addToBottom(new SmiteAction(owner, new DamageInfo(AbstractDungeon.player, BreakAmount, DamageInfo.DamageType.THORNS)));
        } else {
            AbstractMonster target = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
            AbstractDungeon.actionManager.addToBottom(new SmiteAction(target, new DamageInfo(AbstractDungeon.player, PlayerAmount, DamageInfo.DamageType.THORNS)));
            target = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
            AbstractDungeon.actionManager.addToBottom(new SmiteAction(target, new DamageInfo(AbstractDungeon.player, PlayerAmount, DamageInfo.DamageType.THORNS)));
        }
    }
    public void updateDescription() {
        if (owner != AbstractDungeon.player) {
            description = DESCRIPTIONS[2] + DESCRIPTIONS[3] + BreakAmount + DESCRIPTIONS[4] + DESCRIPTIONS[6] + owner.name;
        } else {
            description =  DESCRIPTIONS[1]+ DESCRIPTIONS[3] + PlayerAmount + DESCRIPTIONS[4] + DESCRIPTIONS[5];
        }
    }
}
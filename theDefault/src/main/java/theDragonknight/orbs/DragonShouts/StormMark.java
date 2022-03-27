package theDragonknight.orbs.DragonShouts;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonknight.DragonkinMod;
import theDragonknight.actions.SmiteAction;
import theDragonknight.util.AbstractDragonMark;

public class StormMark extends AbstractDragonMark {
    public static final String ORB_ID = DragonkinMod.makeID("StormMark");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;
    public StormMark(int Pain){
        super();
        Sealstrings = orbString;
        PainAmount = Pain;
        basePainAmount = PainAmount;
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
        super.onEndOfTurn();
    }
    public void updateDescription() {
        if (owner != AbstractDungeon.player) {
            description = DESCRIPTIONS[0] + PainAmount + DESCRIPTIONS[1] + BreakAmount + DESCRIPTIONS[2] + DESCRIPTIONS[4];
        } else {
            description = DESCRIPTIONS[0] + PainAmount + DESCRIPTIONS[1] + PlayerAmount + DESCRIPTIONS[2] + DESCRIPTIONS[3];
        }
    }
}
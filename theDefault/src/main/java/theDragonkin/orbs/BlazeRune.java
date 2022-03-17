package theDragonkin.orbs;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.DragonkinMod;
import theDragonkin.powers.Dragonkin.ReflectiveScales;
import theDragonkin.powers.Dragonkin.Scorchpower;
import theDragonkin.util.AbstractNotOrb;
import theDragonkin.util.AbstractRune;

public class BlazeRune extends AbstractRune {
    public static final String ORB_ID = DragonkinMod.makeID("Blaze");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;
    public BlazeRune(int Pow, int Pain){
        super();
        Sealstrings = orbString;
        PainAmount = Pain;
        name = orbString.NAME;
        RuneText = DESCRIPTIONS[4];
        BreakAmount = Pow;
        updateAnimation();
    }
    public void onStartOfTurn() {
        AbstractMonster target = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
        AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(AbstractDungeon.player,BreakAmount, DamageInfo.DamageType.THORNS)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target,AbstractDungeon.player,new Scorchpower(target,AbstractDungeon.player,2)));
        PainAmount -= 1;
        AbstractRune that = this;
        if (PainAmount <= 0){
            AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                @Override
                public void update() {
                    DragonkinMod.Seals.remove(that);
                    isDone = true;
                }
            });
        }
    }
    public void updateDescription() {
        description = DESCRIPTIONS[0] + PainAmount + DESCRIPTIONS[1] + DESCRIPTIONS[2] + BreakAmount + DESCRIPTIONS[3];
    }
}
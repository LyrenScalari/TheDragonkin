package theDragonknight.orbs;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.powers.NoDrawPower;
import theDragonknight.CustomTags;
import theDragonknight.DragonkinMod;
import theDragonknight.util.AbstractRune;

public class SparkGlyph extends AbstractRune {
    public static final String ORB_ID = DragonkinMod.makeID("Spark");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;
    public SparkGlyph(int Pow, int Pain){
        super();
        Sealstrings = orbString;
        PainAmount = Pain;
        name = orbString.NAME;
        RuneText = DESCRIPTIONS[4];
        BreakAmount = Pow;
        updateAnimation();
    }
    public void onStartOfTurn() {
        if (!(AbstractDungeon.player.hand.size() >= BaseMod.MAX_HAND_SIZE) || !AbstractDungeon.player.hasPower(NoDrawPower.POWER_ID)){
            int burncount = 0;
            for (AbstractCard c : AbstractDungeon.player.drawPile.group){
                if (c.type == AbstractCard.CardType.STATUS || c.hasTag(CustomTags.Rune)){
                    burncount++;
                    if (burncount >= BreakAmount){
                        break;
                    }
                    AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                        @Override
                        public void update() {
                            AbstractDungeon.player.drawPile.group.remove(c);
                            AbstractDungeon.player.drawPile.addToTop(c);
                            isDone = true; }
                    });
                    AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1));
                }
            }
        }
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
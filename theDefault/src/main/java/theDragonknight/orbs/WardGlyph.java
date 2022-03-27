package theDragonknight.orbs;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import theDragonknight.DragonkinMod;
import theDragonknight.util.AbstractRune;

public class WardGlyph extends AbstractRune {
    public static final String ORB_ID = DragonkinMod.makeID("Ward");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;
    private AbstractCard scr;
    public WardGlyph(int Pow, int Pain, AbstractCard source){
        super();
        Sealstrings = orbString;
        PainAmount = Pain;
        name = orbString.NAME;
        RuneText = DESCRIPTIONS[4];
        BreakAmount = Pow;
        scr = source;
        updateAnimation();
    }
    public void onStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player,BreakAmount));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Burn(), 1, true, false));
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
package theDragonknight.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.ScreenOnFireEffect;
import theDragonknight.ui.TransfigureVFX;

import java.util.HashMap;
import java.util.Iterator;

public class TransfigureAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    public AbstractCard src;
    private boolean firstRun;
    public static int numExhausted;
    private HashMap<AbstractCard,AbstractCard> TransmutedPairs = new HashMap<>();

    public TransfigureAction(int amount, AbstractCard source) {
        this.p = AbstractDungeon.player;
        this.amount = amount;
        this.src = source;
        this.firstRun = true;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.EXHAUST;
        AbstractCard newSrc = src.makeCopy();
        TransmutedPairs.put(newSrc,src);
    }


    public void update() {
        if (firstRun) {
            firstRun = false;
            if (this.p.hand.size() == 0) {
                this.isDone = true;
                return;
            }

            if (this.amount > p.hand.size())
                this.amount = p.hand.size();
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, true, true);
            this.tickDuration();
        }
        else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                numExhausted = AbstractDungeon.handCardSelectScreen.selectedCards.group.size();
                for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                    this.p.hand.moveToExhaustPile(c);
                    if (src.magicNumber > 0) {
                        src.magicNumber -= 1;
                        src.baseMagicNumber = src.magicNumber;
                        if (src.magicNumber == 0){
                            src.applyPowers();
                            AbstractDungeon.effectsQueue.add(new TransfigureVFX(TransmutedPairs,this,1.75f));
                        }
                    }
                }

                CardCrawlGame.dungeon.checkForPactAchievement();
                AbstractDungeon.handCardSelectScreen.selectedCards.clear();
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            }

            this.tickDuration();
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
        TEXT = uiStrings.TEXT;
    }
}

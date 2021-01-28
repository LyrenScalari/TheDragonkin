package theDragonkin.CardMods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class RetainCardMod extends AbstractCardModifier {
    private int duration;
    private boolean wasExhaust = false;
    private UIStrings uiStrings =  CardCrawlGame.languagePack.getUIString("theDragonkin:CardmodStrings");
    public RetainCardMod (int length) {
        duration = length;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.retain = true;
    }
    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return  uiStrings.TEXT[3] + rawDescription;
    }
    @Override
    public void onRetained(AbstractCard card) {
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                CardModifierManager.removeSpecificModifier(card, RetainCardMod.this, true);
                isDone = true;
            }
        });
    }
    @Override
    public AbstractCardModifier makeCopy() {
        return new EtherealCardMod(duration);
    }
}
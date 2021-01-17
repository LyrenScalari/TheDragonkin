package theDragonkin.CardMods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.Objects;

public class BurnAfterReadingCardMod extends AbstractCardModifier {
    private int duration;
    private boolean wasExhaust = false;
    private UIStrings uiStrings =  CardCrawlGame.languagePack.getUIString("theDragonkin:UIText");
    public BurnAfterReadingCardMod (int length) {
        duration = length;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        if (!card.exhaust){
            card.exhaust = true;
        } else {
            wasExhaust = true;
        }
    }
    @Override
    public void onRemove(AbstractCard c){
        if (!wasExhaust){
            c.exhaust = false;
        }
    }
    @Override
    public void onApplyPowers(AbstractCard owner){
        if (Objects.equals(owner.glowColor.toString(), "AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy()")){
            owner.glowColor = Color.RED.cpy();
        }
    }
    @Override
    public boolean removeAtEndOfTurn(AbstractCard card) {
        return true;
    }
    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + uiStrings.TEXT[0];
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new BurnAfterReadingCardMod(duration);
    }
}

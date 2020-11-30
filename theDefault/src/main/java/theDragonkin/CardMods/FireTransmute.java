package theDragonkin.CardMods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import theDragonkin.util.CustomTags;

public class FireTransmute extends AbstractCardModifier {
    public AbstractCard.CardTags Startingtag;
    public CardStrings cardStrings;

    public FireTransmute(AbstractCard.CardTags OrginalTag) {
        Startingtag = OrginalTag;
    }

    @Override
    public void onInitialApplication(AbstractCard card){
        card.tags.add(CustomTags.Fire);
        card.tags.remove(Startingtag);
        cardStrings = CardCrawlGame.languagePack.getCardStrings(card.cardID);
    }

    @Override
    public void onRemove(AbstractCard card){
        card.tags.remove(CustomTags.Fire);
        card.tags.add(Startingtag);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                CardModifierManager.removeSpecificModifier(card, FireTransmute.this,true);
                isDone = true;
            }
        });
    }

    @Override
    public String identifier(AbstractCard c){
        return "FireTransmute";
    }

    @Override
    public void atEndOfTurn(AbstractCard card, CardGroup group) {
    }
    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return "thedragonkin:Transmuted: thedragonkin:Fire NL " + cardStrings.DESCRIPTION;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new FireTransmute(Startingtag);
    }
}
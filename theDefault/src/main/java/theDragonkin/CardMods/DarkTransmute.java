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
import theDragonkin.CustomTags;

public class DarkTransmute extends AbstractCardModifier {
    public AbstractCard.CardTags Startingtag;
    public CardStrings cardStrings;

    public DarkTransmute(AbstractCard.CardTags OrginalTag) {
        Startingtag = OrginalTag;
    }

    @Override
    public void onInitialApplication(AbstractCard card){
        card.tags.add(CustomTags.Dark);
        card.tags.remove(Startingtag);
        cardStrings = CardCrawlGame.languagePack.getCardStrings(card.cardID);
    }

    @Override
    public void onRemove(AbstractCard card){
        card.tags.remove(CustomTags.Dark);
        card.tags.add(Startingtag);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                CardModifierManager.removeSpecificModifier(card, DarkTransmute.this,true);
                isDone = true;
            }
        });
    }

    @Override
    public String identifier(AbstractCard c){
        return "DarkTransmute";
    }

    @Override
    public void atEndOfTurn(AbstractCard card, CardGroup group) {
    }
    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return "thedragonkin:Transmuted: thedragonkin:Dark NL " + cardStrings.DESCRIPTION;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new DarkTransmute(Startingtag);
    }
}

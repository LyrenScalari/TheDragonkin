package theDragonkin.CardMods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import theDragonkin.CustomTags;

public class BlutgangEnchantment extends AbstractCardModifier {
    private int duration;

    public BlutgangEnchantment() {
    }

    @Override
    public void onInitialApplication(AbstractCard card){
        card.tags.add(CustomTags.Enchanted);
    }

    @Override
    public void onRemove(AbstractCard card){
        card.tags.remove(CustomTags.Enchanted);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
    }

    @Override
    public void atEndOfTurn(AbstractCard card, CardGroup group) {
    }
    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return "thedragonkin:Enchanted. NL " + rawDescription;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new BlutgangEnchantment();
    }
}

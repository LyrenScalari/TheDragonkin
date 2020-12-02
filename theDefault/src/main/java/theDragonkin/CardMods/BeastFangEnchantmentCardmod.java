package theDragonkin.CardMods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theDragonkin.CustomTags;

public class BeastFangEnchantmentCardmod extends AbstractCardModifier {
    private int duration;

    public BeastFangEnchantmentCardmod (int length) {
        duration = length;
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
        duration -= 1;
        if (duration < 1){
            AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                @Override
                public void update() {
                    CardModifierManager.removeSpecificModifier(card,BeastFangEnchantmentCardmod.this,true);
                    isDone = true;
                }
            });
        }
    }
    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return "thedragonkin:Enchanted. NL " + rawDescription;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new BeastFangEnchantmentCardmod(duration);
    }
}

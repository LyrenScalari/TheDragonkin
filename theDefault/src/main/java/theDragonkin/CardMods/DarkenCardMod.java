package theDragonkin.CardMods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class DarkenCardMod extends AbstractCardModifier {
    public DarkenCardMod(int uses,int bonus) {
    }

    @Override
    public boolean removeOnCardPlayed(AbstractCard card) {
        return Boolean.parseBoolean(null);
    }
    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {}

    @Override
    public AbstractCardModifier makeCopy() {
        return this;
    }
}

package theDragonkin.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class HealDynVar extends DynamicVariable {
    public boolean HealModified;
    public boolean HealUpgraded;
    @Override
    public String key()
    {
        return "H";
        // What you put in your localization file between ! to show your value. Eg, !myKey!.
    }

    @Override
    public boolean isModified(AbstractCard card)
    {
        return card.heal != card.baseHeal;
        // Set to true if the value is modified from the base value.
    }

    @Override
    public void setIsModified(AbstractCard card, boolean v)
    {
        // Do something such that isModified will return the value v.
        // This method is only necessary if you want smith upgrade previews to function correctly.
    }

    @Override
    public int value(AbstractCard card)
    {
        return card.heal;
        // What the dynamic variable will be set to on your card. Usually uses some kind of int you store on your card.
    }

    @Override
    public int baseValue(AbstractCard card)
    {
        return card.baseHeal;
        // Should generally just be the above.
    }

    @Override
    public boolean upgraded(AbstractCard card)
    {
        return HealUpgraded;
        // Set to true if this value is changed on upgrade
    }
}

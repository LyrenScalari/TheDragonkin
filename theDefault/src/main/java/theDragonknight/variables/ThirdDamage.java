package theDragonknight.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theDragonknight.cards.AbstractDefaultCard;

import static theDragonknight.DragonkinMod.makeID;

public class ThirdDamage extends DynamicVariable {

    //For in-depth comments, check the other variable(DefaultCustomVariable). It's nearly identical.

    @Override
    public String key() {
        return makeID("D3");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractDefaultCard) card).isThirdDamageModified;

    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractDefaultCard) card).ThirdDamage;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractDefaultCard) card).baseThirdDamage;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractDefaultCard) card).upgradedThirdDamage;
    }
}
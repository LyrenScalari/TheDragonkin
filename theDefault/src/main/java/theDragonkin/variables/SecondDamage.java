package theDragonkin.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theDragonkin.cards.Dragonkin.AbstractDragonkinCard;

import static theDragonkin.DragonkinMod.makeID;

public class SecondDamage extends DynamicVariable {

    //For in-depth comments, check the other variable(DefaultCustomVariable). It's nearly identical.

    @Override
    public String key() {
        return makeID("D2");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractDragonkinCard) card).isSecondDamageModified;

    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractDragonkinCard) card).secondDamage;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractDragonkinCard) card).baseSecondDamage;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractDragonkinCard) card).upgradedSecondDamage;
    }
}

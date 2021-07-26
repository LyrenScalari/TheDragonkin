package theDragonkin.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theDragonkin.cards.Deathspeaker.AbstractDeathspeakerCard;
import theDragonkin.cards.Dragonkin.AbstractDragonkinCard;

import static theDragonkin.DragonkinMod.makeID;

public class MagicDamage extends DynamicVariable {

    //For in-depth comments, check the other variable(DefaultCustomVariable). It's nearly identical.

    @Override
    public String key() {
        return makeID("MD");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractDeathspeakerCard) card).isMagicDamageModified;

    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractDeathspeakerCard) card).MagicDamage;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractDeathspeakerCard) card).baseMagicDamage;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractDeathspeakerCard) card).upgradedMagicDamage;
    }
}
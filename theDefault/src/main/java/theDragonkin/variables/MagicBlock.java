package theDragonkin.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theDragonkin.cards.Deathspeaker.AbstractDeathspeakerCard;

import static theDragonkin.DragonkinMod.makeID;

public class MagicBlock extends DynamicVariable {

    //For in-depth comments, check the other variable(DefaultCustomVariable). It's nearly identical.

    @Override
    public String key() {
        return makeID("MB");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractDeathspeakerCard) card).isMagicBlockModified;

    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractDeathspeakerCard) card).MagicBlock;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractDeathspeakerCard) card).baseMagicBlock;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractDeathspeakerCard) card).upgradedMagicBlock;
    }
}
package theDragonkin.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theDragonkin.cards.AbstractDefaultCard;
import theDragonkin.cards.GroveKeeper.AbstractGroveKeeperCard;

import static theDragonkin.DefaultMod.makeID;

public class GrovekeeperSecondDamage extends DynamicVariable {

    //For in-depth comments, check the other variable(DefaultCustomVariable). It's nearly identical.

    @Override
    public String key() {
        return "AltD";
        // This is what you put between "!!" in your card strings to actually display the number.
        // You can name this anything (no spaces), but please pre-phase it with your mod name as otherwise mod conflicts can occur.
        // Remember, we're using makeID so it automatically puts "theDefault:" (or, your id) before the name.
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractGroveKeeperCard ) card).isGrovekeeperSecondDamageModified;

    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractGroveKeeperCard) card).GrovekeeperSecondDamage;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractGroveKeeperCard ) card).BaseGrovekeeperSecondDamage;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractGroveKeeperCard ) card).upgradedGrovekeeperSecondDamage;
    }
}

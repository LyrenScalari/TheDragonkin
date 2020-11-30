package theDragonkin.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.cards.Gremory.AbstractMagicGremoryCard;

public class MagicDamageDynVar extends DynamicVariable {

    @Override
    public String key() {
        return "MAG";
        // What you put in your localization file between ! to show your value. Eg, !myKey!.
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractMagicGremoryCard)card).MagDamage > ((AbstractMagicGremoryCard)card).baseMagDamage;
        // Set to true if the value is modified from the base value.
    }

    @Override
    public void setIsModified(AbstractCard card, boolean v) {
    }

    public void UpgradeMagDamage(AbstractMagicGremoryCard card, int upgradebonus){
        card.baseMagDamage += upgradebonus;
        card.MagDamageUpgraded = true;
    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractMagicGremoryCard)card).MagDamage;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractMagicGremoryCard)card).baseMagDamage;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractMagicGremoryCard)card).MagDamageUpgraded;
    }
}


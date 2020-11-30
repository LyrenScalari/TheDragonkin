package theDragonkin.variables;

import basemod.abstracts.DynamicVariable;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theDragonkin.cards.Gremory.AbstractMagicGremoryCard;

public class SpellUses extends DynamicVariable
{
    @Override
    public String key()
    {
        return "Gremory:Spells";
    }

    @Override
    public boolean isModified(AbstractCard card)
    {
        return ((AbstractMagicGremoryCard)card).misc != ((AbstractMagicGremoryCard)card).baseMisc;
    }

    @Override
    public int value(AbstractCard card)
    {
        if (((AbstractMagicGremoryCard)card).misc < 0){
            return 0;
        }
        else return ((AbstractMagicGremoryCard)card).misc;
    }

    @Override
    public int baseValue(AbstractCard card)
    {
        return ((AbstractMagicGremoryCard)card).baseMisc;
    }

    @Override
    public boolean upgraded(AbstractCard card)
    {
        return card instanceof AbstractMagicGremoryCard && ((AbstractMagicGremoryCard) card).upgradedMisc;
    }

    @Override
    public Color getNormalColor() {
        return Color.CYAN;
    }
}

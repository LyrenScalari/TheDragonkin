package theDragonkin.variables;

import basemod.abstracts.DynamicVariable;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theDragonkin.cards.AbstractDefaultCard;

public class FadingVar extends DynamicVariable
{
    @Override
    public String key()
    {
        return "Arisen:Fading";
    }

    @Override
    public boolean isModified(AbstractCard card)
    {
        return ((AbstractDefaultCard)card).fading != ((AbstractDefaultCard)card).basefading;
    }

    @Override
    public int value(AbstractCard card)
    {
        if (((AbstractDefaultCard)card).fading < 0){
            return 0;
        }
        else return ((AbstractDefaultCard)card).fading;
    }

    @Override
    public int baseValue(AbstractCard card)
    {
        return ((AbstractDefaultCard)card).basefading;
    }

    @Override
    public boolean upgraded(AbstractCard card)
    {
        return card instanceof AbstractDefaultCard && ((AbstractDefaultCard) card).upgradedfading;
    }

    @Override
    public Color getNormalColor() {
        return Color.SCARLET;
    }
}
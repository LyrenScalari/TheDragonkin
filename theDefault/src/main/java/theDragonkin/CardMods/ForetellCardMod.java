package theDragonkin.CardMods;

import basemod.abstracts.AbstractCardModifier;
import basemod.interfaces.AlternateCardCostModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theDragonkin.cards.Drifter.ForetellCard;

public class ForetellCardMod extends AbstractCardModifier implements AlternateCardCostModifier {
    private int StormRate;
    public boolean Foretold = false;
    public boolean Foretell = false;

    public ForetellCardMod(int stormRate) {
        priority = 0;
        this.StormRate = stormRate;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ForetellCardMod(StormRate);
    }

    @Override
    public boolean canSplitCost(AbstractCard card) {
        return false;
    }

    public boolean isInherent(AbstractCard card) {
        return true;
    }

    @Override
    public boolean prioritizeAlternateCost(AbstractCard card) {
        return true;
    }

    @Override
    public int getAlternateResource(AbstractCard card) {
        if (Foretold) {
            return card.costForTurn;
        }
        return -1;
    }

    @Override
    public int spendAlternateCost(AbstractCard card, int costToSpend) {
        if (Foretold) {
            costToSpend = 0;
            return costToSpend;
        } else return costToSpend;
    }
}
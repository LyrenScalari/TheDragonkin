package theDragonkin.CardMods;

import basemod.abstracts.AbstractCardModifier;
import basemod.interfaces.AlternateCardCostModifier;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theDragonkin.cards.Dragonkin.interfaces.StormCard;
import theDragonkin.powers.Deathspeaker.ManaPower;
import theDragonkin.powers.Dragonkin.FuryPower;

public class Spellcaster extends AbstractCardModifier implements AlternateCardCostModifier {
    private int StormRate;

    public Spellcaster(int stormRate) {
        priority = 0;
        this.StormRate = stormRate;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new Spellcaster(StormRate);
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
        if (AbstractDungeon.player.hasPower(ManaPower.POWER_ID)) {
            if (AbstractDungeon.player.getPower(ManaPower.POWER_ID).amount >= card.costForTurn) {
                return AbstractDungeon.player.getPower(ManaPower.POWER_ID).amount;
            } else return 0;
        }
        return 0;
    }

    @Override
    public int spendAlternateCost(AbstractCard card, int costToSpend) {
        if (AbstractDungeon.player.hasPower(ManaPower.POWER_ID)) {
            if (AbstractDungeon.player.getPower(ManaPower.POWER_ID).amount >= card.costForTurn) {
                costToSpend = 0;
                return costToSpend;
            }
        }
        return costToSpend;
    }
}
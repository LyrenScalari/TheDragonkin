package theDragonkin.CardMods;

import basemod.abstracts.AbstractCardModifier;
import basemod.interfaces.AlternateCardCostModifier;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theDragonkin.cards.Dragonkin.interfaces.StormCard;
import theDragonkin.powers.Dragonkin.FuryPower;

public class StormEffect extends AbstractCardModifier implements AlternateCardCostModifier {
    private int StormRate;
    public StormEffect(int stormRate) {
        priority = 0;
        this.StormRate =  stormRate;
    }
    @Override
    public AbstractCardModifier makeCopy() {
        return new StormEffect(StormRate);
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
        if (AbstractDungeon.player.hasPower(FuryPower.POWER_ID)) {
            if (AbstractDungeon.player.getPower(FuryPower.POWER_ID).amount >= StormRate) {
                return 1;
            } else return -1;
        }
        return -1;
    }
    @Override
    public int spendAlternateCost(AbstractCard abstractCard, int costToSpend) {
        if (AbstractDungeon.player.hasPower(FuryPower.POWER_ID)){
            if (AbstractDungeon.player.getPower(FuryPower.POWER_ID).amount >= StormRate){
                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, FuryPower.POWER_ID, StormRate));
                ((StormCard)abstractCard).onStorm();
                costToSpend = 0;
                return costToSpend;
            }
        }
        return costToSpend;
    }
}

package theDragonkin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import theDragonkin.cards.GroveKeeper.AbstractChooseOneCard;
import static theDragonkin.powers.GroveKeeper.RoadLessTraveledPower.POWER_ID;

public class TwinRoadsDiscardAction extends AbstractGameAction {
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public TwinRoadsDiscardAction() {
        this.duration = 0.5F;
    }

    public void update() {
        for (AbstractCard ca : AbstractChooseOneCard.Roadsuntraveled.group){
            AbstractCard tempchoice = ca.makeStatEquivalentCopy();
            addToBot(new MakeTempCardInDiscardAction(tempchoice,1));
        }
            this.isDone = true;
        }
    }


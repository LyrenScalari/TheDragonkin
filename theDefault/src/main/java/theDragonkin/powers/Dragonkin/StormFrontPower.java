package theDragonkin.powers.Dragonkin;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.DefaultMod;


public class StormFrontPower extends TwoAmountPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("StormFront");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    public StormFrontPower(final AbstractCreature owner, final AbstractCreature source, final int amount1, final int amount2) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount1;
        this.amount2 = amount2;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.loadRegion("storm");

        updateDescription();
    }

    @Override
    public void updateDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(DESCRIPTIONS[0]);
        sb.append(this.amount2);
        sb.append(DESCRIPTIONS[1]);
        sb.append(this.amount);
        if (amount < 2){
            sb.append(DESCRIPTIONS[2]);
        } else {
            sb.append(DESCRIPTIONS[3]);
        }
        description = sb.toString();
    }
    @Override
    public AbstractPower makeCopy() {
        return new StormFrontPower(owner, source, amount,amount2);
    }
}
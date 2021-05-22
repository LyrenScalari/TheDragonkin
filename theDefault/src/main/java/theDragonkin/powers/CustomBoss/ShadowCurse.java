package theDragonkin.powers.CustomBoss;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.DragonkinMod;
import theDragonkin.cards.Dragonkin.Suffering;

public class ShadowCurse extends AbstractPower {
    public AbstractCreature source;
    AbstractPower p = AbstractDungeon.player.getPower(ShadowCurse.POWER_ID);
    public static final String POWER_ID = DragonkinMod.makeID("ShadowCurse");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public int delay = 2;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.

    public ShadowCurse(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.DEBUFF;
        isTurnBased = true;

        // We load those textures here.
        this.loadRegion("darkembrace");
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        AbstractCard newCurse = new Suffering();
        addToBot(new MakeTempCardInHandAction(newCurse));
        addToBot(new ReducePowerAction(owner,owner,this,1));
    }
    @Override
    public void updateDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(DESCRIPTIONS[0]);
        sb.append(amount);
        if (amount > 1) {
            sb.append(DESCRIPTIONS[1]);
        } else sb.append(DESCRIPTIONS[2]);
        sb.append(DESCRIPTIONS[3]);
        sb.append(DESCRIPTIONS[4]);
        description = sb.toString();
    }
}

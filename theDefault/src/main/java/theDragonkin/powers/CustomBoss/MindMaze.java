package theDragonkin.powers.CustomBoss;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.DefaultMod;

public class MindMaze extends AbstractPower implements NonStackablePower {
    public AbstractCreature source;
    AbstractPower p = AbstractDungeon.player.getPower(MindMaze.POWER_ID);
    public static final String POWER_ID = DefaultMod.makeID("MindMaze");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public int delay = 2;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.

    public MindMaze (final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.DEBUFF;
        isTurnBased = true;

        // We load those textures here.
        this.loadRegion("corruption");
        updateDescription();
    }
    @Override
    public void onCardDraw(AbstractCard card) {
        if (amount > 0) {
            --amount;
            addToBot(new DiscardSpecificCardAction(card));
            addToBot(new MakeTempCardInHandAction(AbstractDungeon.returnRandomCurse().makeStatEquivalentCopy()));
            if (amount <= 0){
                addToTop(new RemoveSpecificPowerAction(owner,owner,this));
            }
        }
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
        description = sb.toString();
    }
}
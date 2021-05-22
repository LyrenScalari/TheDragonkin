package theDragonkin.powers.CustomBoss;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.DragonkinMod;

public class DarkRitual extends TwoAmountPower {
    public AbstractCreature source;
    AbstractPower p = AbstractDungeon.player.getPower(DarkRitual.POWER_ID);
    public static final String POWER_ID = DragonkinMod.makeID("DarkRitual");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public int delay = 2;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.

    public DarkRitual(final AbstractCreature owner, final int amount, final int Threshold) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;
        amount2 = Threshold;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those textures here.
        this.loadRegion("brutality");
        updateDescription();
    }
    @Override
    public void  atStartOfTurn() {
        int increasecount = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group){
            if (c.type == AbstractCard.CardType.CURSE){
                increasecount ++;
            }
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group){
            if (c.type == AbstractCard.CardType.CURSE){
                increasecount ++;
            }
        }
        for (AbstractCard c : AbstractDungeon.player.drawPile.group){
            if (c.type == AbstractCard.CardType.CURSE){
                increasecount ++;
            }
        }
        int finalIncreasecount;
     if  (AbstractDungeon.ascensionLevel < 9) {
         finalIncreasecount = increasecount;
     } else finalIncreasecount = increasecount*2;
        addToBot(new AbstractGameAction() {
            public void update() {
                addToTop(new ApplyPowerAction(owner,owner,new DarkRitual(owner,finalIncreasecount,amount2), finalIncreasecount));
                isDone = true;
            }
        });
    }
    public void increaseLimit(int incamt){
        amount = 0;
        amount2 += incamt;
        updateDescription();
    }
    @Override
    public void updateDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(DESCRIPTIONS[0]);
        description = sb.toString();
    }
}
package theDragonkin.powers.CustomBoss;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.DragonkinMod;

public class Exhaustion extends AbstractPower implements NonStackablePower {
    public AbstractCreature source;
    AbstractPower p = AbstractDungeon.player.getPower(Exhaustion.POWER_ID);
    public static final String POWER_ID = DragonkinMod.makeID("Exhaustion");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private UIStrings uiStrings =  CardCrawlGame.languagePack.getUIString("theDragonkin:CardmodStrings");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public int delay = 2;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.

    public Exhaustion (final AbstractCreature owner, final AbstractCreature source, final int amount) {
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
    public void atStartOfTurnPostDraw() {
        addToBot(new AbstractGameAction() {
            public void update() {
                addToTop(new SelectCardsInHandAction(1,uiStrings.TEXT[2],false,false, (c) ->true,list ->{
                    if (list.get(0).type == AbstractCard.CardType.CURSE){
                        addToTop(new LoseEnergyAction(1));
                    }
                    addToTop(new ExhaustSpecificCardAction(list.get(0),AbstractDungeon.player.hand));
                }));
                isDone = true;
            }
        });
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
        description = sb.toString();
    }
}
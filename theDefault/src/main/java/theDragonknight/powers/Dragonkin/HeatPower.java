package theDragonknight.powers.Dragonkin;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonknight.DragonkinMod;

public class HeatPower extends AbstractPower  implements CloneablePowerInterface {
    public AbstractCreature source;
    public static final String POWER_ID = DragonkinMod.makeID("HeatPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.

    public HeatPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = AbstractPower.PowerType.BUFF;
        isTurnBased = false;

        // We load those textures here.
        this.loadRegion("flameBarrier");
        updateDescription();
    }

    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if (DragonkinMod.HeatValid(card)){
            action.exhaustCard = true;
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(owner,owner,this,1));
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(owner,1));
            DragonkinMod.TriggerOnCycle(card);
            if (card.cardID.equals(Burn.ID)){
                    addToBot(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, card.magicNumber, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
            }
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];

    }

    @Override
    public AbstractPower makeCopy() {
        return new HeatPower(owner, source, amount);
    }
}
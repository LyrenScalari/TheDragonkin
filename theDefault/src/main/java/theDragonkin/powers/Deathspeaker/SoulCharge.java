package theDragonkin.powers.Deathspeaker;

import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.DragonkinMod;
import theDragonkin.cards.Deathspeaker.AbstractSpellCard;

public class SoulCharge extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DragonkinMod.makeID("SoulCharge");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    public SoulCharge(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.source = source;
        this.amount = amount;
        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.

        updateDescription();
    }
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card instanceof AbstractSpellCard || card.type != AbstractCard.CardType.SKILL){
            this.flash();
            addToBot(new ApplyPowerAction(owner,owner,new SoulCharge(owner,owner,1)));

        } else {
            int temp = this.amount;
            for (int i = 0; i < temp; i++){
                this.flash();
                addToBot(new DamageRandomEnemyAction(new DamageInfo(owner,3, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                addToBot(new ReducePowerAction(owner,owner,this,1));
            }
        }

    }
    @Override
    public void updateDescription() {
         description = DESCRIPTIONS[0] + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    }
    @Override
    public AbstractPower makeCopy() {
        return new InfernalWatch(owner, source, amount);
    }

}
package theDragonkin.powers.Deathspeaker;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theDragonkin.DragonkinMod;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DragonkinMod.makePowerPath;

public class InfernalWatch extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DragonkinMod.makeID("Watch");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    public InfernalWatch(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.source = source;
        this.amount = amount;
        if (owner instanceof AbstractPlayer){
            type = NeutralPowertypePatch.NEUTRAL;
        } else type = PowerType.DEBUFF;
        isTurnBased = false;
        this.loadRegion("mantra");
        // We load those txtures here.

        updateDescription();
    }
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (type == NeutralPowertypePatch.NEUTRAL && ((card.target == AbstractCard.CardTarget.SELF_AND_ENEMY) || (card.target == AbstractCard.CardTarget.ENEMY) )) {
            this.flash();
            addToBot(new ApplyPowerAction(action.target,action.source,new InfernalWatch(action.target,action.source,this.amount)));
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }

    }
    public void onDeath() {
        if (!AbstractDungeon.getCurrRoom().isBattleEnding()) {
            this.flashWithoutSound();
            addToBot(new ApplyPowerAction(AbstractDungeon.player,owner,new InfernalWatch(AbstractDungeon.player,owner,this.amount)));
        }
    }
    @Override
    public void atStartOfTurn() {
        if (type == PowerType.DEBUFF){
            addToBot(new DamageAction(owner,new DamageInfo(owner,amount, DamageInfo.DamageType.THORNS)));
        }
    }
    @Override
    public void updateDescription() {
        if (this.type == PowerType.DEBUFF) {
            description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        } else description = DESCRIPTIONS[2];
    }
    @Override
    public AbstractPower makeCopy() {
        return new InfernalWatch(owner, source, amount);
    }

}
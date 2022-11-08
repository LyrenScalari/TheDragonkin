package theDragonkin.powers.WindWalker;

import basemod.helpers.CardModifierManager;
import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.CardMods.PlusDamageBlockCardMod;
import theDragonkin.DragonkinMod;
import theDragonkin.Stances.Tempest;

public class MasteryPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DragonkinMod.makeID("Mastery");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.

    public MasteryPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;
        this.loadRegion("storm");
        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.

        updateDescription();
    }
    @Override
    public void updateDescription() {
        description = powerStrings.DESCRIPTIONS[0];

    }
    public void onUseCard(AbstractCard card, UseCardAction action) {
        for (AbstractCard c : AbstractDungeon.player.hand.group){
            if (card.originalName.equals(c.originalName)){
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        CardModifierManager.addModifier(c,new PlusDamageBlockCardMod(2));
                        isDone = true;
                    }
                });
            }
        }
        for (AbstractCard c : AbstractDungeon.player.drawPile.group){
            if (card.originalName.equals(c.originalName)){
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        CardModifierManager.addModifier(c,new PlusDamageBlockCardMod(2));
                        isDone = true;
                    }
                });
            }
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group){
            if (card.originalName.equals(c.originalName)){
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        CardModifierManager.addModifier(c,new PlusDamageBlockCardMod(2));
                        isDone = true;
                    }
                });
            }
        }
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group){
            if (card.originalName.equals(c.originalName)){
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        CardModifierManager.addModifier(c,new PlusDamageBlockCardMod(2));
                        isDone = true;
                    }
                });
            }
        }
        addToBot(new ReducePowerAction(owner,owner,this,1));
    }
    @Override
    public AbstractPower makeCopy() {
        return new MasteryPower(owner, source, amount);
    }
}
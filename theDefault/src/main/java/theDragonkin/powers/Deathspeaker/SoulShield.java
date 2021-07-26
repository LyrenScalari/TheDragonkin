package theDragonkin.powers.Deathspeaker;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.CustomTags;
import theDragonkin.DragonkinMod;
import theDragonkin.powers.Dragonkin.Scorchpower;
import theDragonkin.relics.Dragonkin.MukySludge;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DragonkinMod.makePowerPath;

public class SoulShield extends AbstractPower implements CloneablePowerInterface, HealthBarRenderPower {
    public AbstractCreature source;

    public static final String POWER_ID = DragonkinMod.makeID("SoulShield");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.

    public SoulShield(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;
        priority = 70;
        type = PowerType.BUFF;
        isTurnBased = false;
        this.loadRegion("noPain");
        // We load those txtures here.


        updateDescription();
    }

    @Override
    public int onAttacked(DamageInfo di, int d){
        if (d > 0) {
            this.flash();
            int temp = d;
            if (this.amount > d) {
                AbstractDungeon.actionManager.addToTop(
                        new ReducePowerAction(this.owner, this.owner, this, temp));
                return 0;
            } else {
                temp -= this.amount;
                AbstractDungeon.actionManager.addToTop(
                        new ReducePowerAction(this.owner, this.owner, this, temp));
                return temp;
            }
        }
        return d;
    }

    // Note: If you want to apply an effect when a power is being applied you have 3 options:
    //onInitialApplication is "When THIS power is first applied for the very first time only."
    //onApplyPower is "When the owner applies a power to something else (only used by Sadistic Nature)."
    //onReceivePowerPower from StSlib is "When any (including this) power is applied to the owner."


    // At the end of the turn, remove gained Dexterity

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount/2 + DESCRIPTIONS[2];
    }
    @Override
    public AbstractPower makeCopy() {
        return new SoulShield(owner, source, amount);
    }
    @Override
    public void onInitialApplication() {
        if (this.amount > owner.maxHealth){
            this.amount = owner.maxHealth;
        }
    }
    public void onVictory() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p.currentHealth > 0) {
            p.heal((int) Math.floor(this.amount/2));
        }

    }
    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount >= owner.maxHealth) {
            this.amount = owner.maxHealth;
        }
    }
    @Override
    public int getHealthBarAmount() {
        return amount;
    }

    @Override
    public Color getColor() {
        return CardHelper.getColor(102,21,161);
    }
}
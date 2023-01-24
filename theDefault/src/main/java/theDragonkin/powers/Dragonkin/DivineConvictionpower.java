package theDragonkin.powers.Dragonkin;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.DragonkinMod;
import theDragonkin.cards.Dragonkin.AbstractHolyCard;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DragonkinMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class DivineConvictionpower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DragonkinMod.makeID("DivineConvictionpower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.

    public DivineConvictionpower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.loadRegion("mantra");

        updateDescription();
    }
    @Override
    public void renderIcons(SpriteBatch sb, float x, float y, Color c) {
        super.renderIcons(sb, x, y, Color.GOLD.cpy());
    }
    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        if (card instanceof AbstractHolyCard){
            damage = damage + this.amount;
        }
        return super.atDamageGive(damage, type, card);
    }

    @Override
    public float modifyBlock(float block, AbstractCard card) {
        if (card instanceof AbstractHolyCard){
            block = block + this.amount;
        }
        return super.modifyBlock(block, card);
    }

    // note: If you want to apply an effect when a power is being applied you have 3 options:
    //onInitialApplication is "When THIS power is first applied for the very first time only."
    //onApplyPower is "When the owner applies a power to something else (only used by Sadistic Nature)."
    //onReceivePowerPower from StSlib is "When any (including this) power is applied to the owner."


    // At the end of the turn, remove gained Dexterity

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        if (amount < 5){
            description = powerStrings.DESCRIPTIONS[0] + amount;
        } else {
            description = powerStrings.DESCRIPTIONS[0] + amount;
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new DivineConvictionpower(owner, source, amount);
    }
}

package theDragonkin.powers.Dragonkin;



import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.CustomTags;
import theDragonkin.DragonkinMod;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DragonkinMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class Scorchpower extends AbstractPower implements CloneablePowerInterface, HealthBarRenderPower {
    public AbstractCreature source;

    public static final String POWER_ID = DragonkinMod.makeID("Scorch");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("Scorch.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("Scorch32.png"));

    public Scorchpower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.DEBUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public int onAttacked(DamageInfo di, int d){
        if (di.type == DamageInfo.DamageType.NORMAL) {
            this.flash();
            int temp = amount;
            AbstractDungeon.actionManager.addToTop(
                    new ReducePowerAction(this.owner, this.owner, this, 1));
            return d + temp;
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
        description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + DESCRIPTIONS[2];
    }
    @Override
    public AbstractPower makeCopy() {
        return new Scorchpower(owner, source, amount);
    }

    @Override
    public int getHealthBarAmount() {
        if (AbstractDungeon.player.hoveredCard != null) {
            if (AbstractDungeon.player.hoveredCard.type == AbstractCard.CardType.ATTACK && !AbstractDungeon.player.hoveredCard.hasTag(CustomTags.Dragon_Breath)) {
                if (AbstractDungeon.player.hoveredCard.target == AbstractCard.CardTarget.ENEMY && !(owner.hb.hovered)) {
                    return 0;
                } else return amount;
            }
        }
        return 0;
    }

    @Override
    public Color getColor() {
        return CardHelper.getColor(209,107,4);
    }
}

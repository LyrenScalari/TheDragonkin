package theDragonknight.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import theDragonknight.DragonknightMod;
import theDragonknight.util.TextureLoader;

import static theDragonknight.DragonknightMod.makePowerPath;

public class RotPower extends TwoAmountPower implements CloneablePowerInterface, HealthBarRenderPower {
    public AbstractCreature source;

    public static final String POWER_ID = DragonknightMod.makeID("RotPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("ScarletRot.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("ScarletRot32.png"));

    public RotPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;
        priority = 0;
        type = PowerType.DEBUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }
    @Override
    public void atEndOfTurn(final boolean isPlayer) {
        if (owner.hasPower(PoisonPower.POWER_ID)){
            addToBot(new DamageAction(owner,new DamageInfo(owner, (int) (owner.getPower(PoisonPower.POWER_ID).amount*.50), DamageInfo.DamageType.HP_LOSS)));
        }
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        if (owner.hasPower(PoisonPower.POWER_ID)) {
            amount2 = (int) (owner.getPower(PoisonPower.POWER_ID).amount*.50);
            description = DESCRIPTIONS[0] + DESCRIPTIONS[1] + (int) (owner.getPower(PoisonPower.POWER_ID).amount*.50) + DESCRIPTIONS[2];
        } else {
            description = DESCRIPTIONS[0];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new RotPower(owner, source, amount);
    }

    @Override
    public int getHealthBarAmount() {
        updateDescription();
        if (owner.hasPower(PoisonPower.POWER_ID)){
            return (int) (owner.getPower(PoisonPower.POWER_ID).amount*.50);
        } else {
            return 0;
        }
    }

    @Override
    public Color getColor() {
        return Color.MAROON;
    }

}

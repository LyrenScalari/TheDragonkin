package theDragonknight.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import theDragonknight.CustomTags;
import theDragonknight.DragonknightMod;
import theDragonknight.orbs.ModifySigilPower;
import theDragonknight.util.AbstractDragonMark;
import theDragonknight.util.TextureLoader;

import static theDragonknight.DragonknightMod.makePowerPath;

public class DragonscalesPower extends AbstractPower implements CloneablePowerInterface, ModifySigilPower {
    public AbstractCreature source;

    public static final String POWER_ID = DragonknightMod.makeID("DragonscalesPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("Dragonblood.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("Dragonblood32.png"));

    public DragonscalesPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;
        priority = 0;
        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount;
    }
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        if (card.hasTag(CustomTags.Draconic)){
            return super.atDamageGive(damage+amount, type, card);
        }
        return super.atDamageGive(damage, type, card);
    }
    public float modifyBlock(float block, AbstractCard card) {
        if (card.hasTag(CustomTags.Draconic)){
            return super.modifyBlock(block+amount, card);
        }
        return super.modifyBlock(block, card);
    }
    @Override
    public AbstractPower makeCopy() {
        return new RotPower(owner, source, amount);
    }

    @Override
    public void ModifyOrb(AbstractDragonMark DragonMark) {
        DragonMark.PlayerAmount = amount + DragonMark.BasePlayerAmount;
        DragonMark.BreakAmount = amount + DragonMark.baseBreakAmount;
    }
}
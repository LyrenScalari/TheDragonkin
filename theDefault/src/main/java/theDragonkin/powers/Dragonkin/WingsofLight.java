package theDragonkin.powers.Dragonkin;

import IconsAddon.actions.GainCustomBlockAction;
import IconsAddon.cardmods.AddIconToDescriptionMod;
import IconsAddon.icons.HolyIcon;
import IconsAddon.util.BlockModifierManager;
import basemod.helpers.CardModifierManager;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.HealEffect;
import com.megacrit.cardcrawl.vfx.combat.HealVerticalLineEffect;
import com.megacrit.cardcrawl.vfx.stance.DivinityParticleEffect;
import theDragonkin.CustomTags;
import theDragonkin.DamageModifiers.BlockModifiers.DivineBlock;
import theDragonkin.DragonkinMod;
import theDragonkin.actions.GainDivineArmorAction;
import theDragonkin.powers.LosePowerPower;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DragonkinMod.makePowerPath;

public class WingsofLight extends TwoAmountPower implements CloneablePowerInterface, OnReceivePowerPower {
    public AbstractCreature source;

    public static final String POWER_ID = DragonkinMod.makeID("WingsofLight");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("DivineArmor.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("DivineArmor32.png"));
    private static final float X_JITTER;
    private static final float Y_JITTER;
    private static final float OFFSET_Y;
    private static AbstractCard srcCard;
    static {
        X_JITTER = 120.0F * Settings.scale;
        Y_JITTER = 120.0F * Settings.scale;
        OFFSET_Y = -50.0F * Settings.scale;
    }
    public WingsofLight(final AbstractCreature owner, final AbstractCreature source,int dmgamount,int armoramt, AbstractCard srccard) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = dmgamount;
        this.source = source;
        this.amount2 += armoramt;
        type = PowerType.BUFF;
        srcCard = srccard;
        isTurnBased = false;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        updateDescription();
    }

    @Override
    public void  atEndOfTurnPreEndTurnCards(boolean isplayer) {
        this.flash();
        CardCrawlGame.sound.play("POWER_MANTRA", 0.05F);
        addToBot(new VFXAction(new HealVerticalLineEffect(owner.drawX+ MathUtils.random(-X_JITTER * 1.5F, X_JITTER * 1.5F),owner.drawY+ OFFSET_Y + MathUtils.random(-Y_JITTER, Y_JITTER))));
        addToBot(new VFXAction(new HealVerticalLineEffect(owner.drawX+ MathUtils.random(-X_JITTER * 1.5F, X_JITTER * 1.5F),owner.drawY+ OFFSET_Y + MathUtils.random(-Y_JITTER, Y_JITTER))));
        addToBot(new DamageAction(owner,new DamageInfo(owner,amount, DamageInfo.DamageType.THORNS)));
        addToBot(new GainCustomBlockAction(srcCard,owner,amount2));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + amount2 + DESCRIPTIONS[2];
    }
    @Override
    public AbstractPower makeCopy() {
        return new DivineRetributionPower(owner, source,amount);
    }

    @Override
    public boolean onReceivePower(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        if (abstractPower instanceof WingsofLight){
            amount2 += ((WingsofLight) abstractPower).amount2;
        }
        return true;
    }
}
package theDragonkin.powers.Dragonkin;


import basemod.helpers.CardModifierManager;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.actions.common.GainCustomBlockAction;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
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
import theDragonkin.orbs.FortitudeSeal;
import theDragonkin.powers.LosePowerPower;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DragonkinMod.makePowerPath;

public class WingsofLight extends TwoAmountPower implements CloneablePowerInterface{
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
    private int ActivationCounter;
    static {
        X_JITTER = 120.0F * Settings.scale;
        Y_JITTER = 120.0F * Settings.scale;
        OFFSET_Y = -50.0F * Settings.scale;
    }
    public WingsofLight(final AbstractCreature owner, final AbstractCreature source,int amt1,int amt2, AbstractCard srccard) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amt1;
        this.source = source;
        this.amount2 = amt2;
        type = PowerType.BUFF;
        srcCard = srccard;
        isTurnBased = false;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        ActivationCounter = 0;
        updateDescription();
    }
    public void atStartOfTurn() {
        this.ActivationCounter = 0;
    }
    @Override
    public void updateDescription() {
        if (amount < 2){
            description = DESCRIPTIONS[0] + DESCRIPTIONS[1] + amount2 + DESCRIPTIONS[2];
        } else description = DESCRIPTIONS[3] + amount2 + DESCRIPTIONS[4] + DESCRIPTIONS[1] + amount2 + DESCRIPTIONS[2];
    }

    public void onSealBreak(){
        if (ActivationCounter <= this.amount){
            AbstractDungeon.actionManager.addToBottom(new GainCustomBlockAction(srcCard,AbstractDungeon.player,amount2));
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new WingsofLight(owner, source,amount,amount2,srcCard);
    }
}
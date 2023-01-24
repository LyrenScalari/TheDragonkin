package theDragonkin.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PainfulStabsPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.BobEffect;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.MiracleEffect;
import theDragonkin.DragonkinMod;
import theDragonkin.actions.SmiteAction;
import theDragonkin.cards.Dragonkin.interfaces.ReciveDamageEffect;
import theDragonkin.powers.Dragonkin.WingsofLight;

import java.util.ArrayList;

public abstract class AbstractSeal extends AbstractNotOrb implements ReciveDamageEffect {
    public static boolean DevotionEffects = false;
    public AbstractSeal() {
        this.c = Settings.CREAM_COLOR.cpy();
        this.shineColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        this.hb = new Hitbox(96.0F * Settings.scale, 96.0F * Settings.scale);
        this.img = ImageMaster.EYE_ANIM_0;
        this.bobEffect = new BobEffect(3.0F * Settings.scale, 3.0F);
        this.fontScale = 0.7F;
        this.showEvokeValue = true;
        this.channelAnimTimer = 0.5F;
        basePainAmount = PainAmount;
        baseBreakAmount = BreakAmount;
    }
    public void renderText(SpriteBatch sb) {
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.BreakAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET - 4.0F * Settings.scale, new Color(0.2F, 1.0F, 1.0F, this.c.a), this.fontScale);
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.PainAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET + 20.0F * Settings.scale,new Color(0.5F, 0.0F, 3.0F, this.c.a), this.fontScale);
    }
    @Override
    public void onReciveDamage(int damage) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT){
            PainAmount -= damage;
        }
        if (PainAmount <= 0){
            Break();
        }
    }
    public void onStartOfTurn() {
        PainAmount = basePainAmount;
    }
    public void Break(){
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new BorderFlashEffect(Color.GOLDENROD, true)));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new MiracleEffect()));
        if (!AbstractDungeon.actionManager.turnHasEnded){
            PainAmount = basePainAmount;
        } else {
            AbstractSeal that = this;
            AbstractDungeon.actionManager.addToTop(new AbstractGameAction() {
                @Override
                public void update() {
                    DragonkinMod.Seals.remove(that);
                    isDone = true;
                }
            });
        }
        for (AbstractPower p : AbstractDungeon.player.powers){
            if (p instanceof WingsofLight){
                ((WingsofLight) p).onSealBreak();
            }
        }
    }
    public void updateAnimation() {
        this.bobEffect.update();
        if (this.channelAnimTimer != 0.0F) {
            this.channelAnimTimer -= Gdx.graphics.getDeltaTime();
            if (this.channelAnimTimer < 0.0F) {
                this.channelAnimTimer = 0.0F;
            }
        }
        this.c.a = Interpolation.pow2In.apply(1.0F, 0.01F, this.channelAnimTimer / 0.5F);
        this.scale = Interpolation.swingIn.apply(Settings.scale, 0.01F, this.channelAnimTimer / 0.5F);
        if (!AnimTimer){
            AbstractDungeon.effectsQueue.add(new SealParticleEffect(cX,cY,this));
            AnimTimer = true;
        }
    }
    public void onEndOfTurn() {
        if (!DevotionEffects){
            CardCrawlGame.sound.play("POWER_MANTRA", 0.05F);
            for (int i = 0; i < AbstractDungeon.miscRng.random(10, 15); ++i) {
                AbstractDungeon.effectsQueue.add(new DivineEyeParticle());
            }
            DevotionEffects = true;
        }
        if (!DragonkinMod.damagetaken){
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new BorderFlashEffect(Color.SCARLET, true)));
            for (AbstractNotOrb notOrb : DragonkinMod.Seals){
                if (notOrb instanceof AbstractSeal){
                    if (notOrb.PainAmount > 0){
                        AbstractDungeon.actionManager.addToBottom(new SmiteAction(AbstractDungeon.player,new DamageInfo(AbstractDungeon.player,notOrb.PainAmount, DamageInfo.DamageType.THORNS)));
                        notOrb.PainAmount = 0;
                    }
                }
            }
            DevotionEffects = true;
        }
    }
    public void updateDescription() {

    }
    public void update() {
        this.hb.update();
        angle = (360f/DragonkinMod.Seals.size()) * DragonkinMod.Seals.indexOf(this);
        cX = (AbstractDungeon.player.hb.cX-50f) + (float)(dy2*Math.cos((Math.toRadians(angle))));
        cY = (AbstractDungeon.player.hb.cY+50f) + (float)(dy2*Math.sin(Math.toRadians(angle)));
        hb.move(cX, cY); //I think this is correct, but might not be. Might need some offset calculations
        if (this.hb.hovered) {
            TipHelper.renderGenericTip(this.cX + 96.0F * Settings.scale, this.cY + 64.0F * Settings.scale, this.name, this.description);
        }
        this.fontScale = MathHelper.scaleLerpSnap(this.fontScale, 0.7F);
    }

}

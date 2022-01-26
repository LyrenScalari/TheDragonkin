package theDragonkin.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.vfx.BobEffect;
import theDragonkin.DragonkinMod;
import theDragonkin.cards.Dragonkin.interfaces.ReciveDamageEffect;

import java.util.ArrayList;

public abstract class AbstractSeal implements ReciveDamageEffect {
    public String name;
    public String description;
    public String ID;
    public OrbStrings Sealstrings;
    protected ArrayList<PowerTip> tips = new ArrayList();
    public int BreakAmount = 0;
    public int PainAmount = 0;
    protected int baseBreakAmount = 0;
    protected int basePainAmount = 0;
    public float cX = 0.0F;
    public float cY = 0.0F;
    public float tX;
    public float tY;
    protected Color c;
    protected Color shineColor;
    protected static final int W = 96;
    private static  float dy2 = 200;
    public static float angleSpeed = 0.00f;
    private static float angle;
    public Hitbox hb;
    protected TextureAtlas.AtlasRegion img;
    protected BobEffect bobEffect;
    protected static final float NUM_X_OFFSET;
    protected static final float NUM_Y_OFFSET;
    protected float scale;
    protected float fontScale;
    protected boolean showEvokeValue;
    protected boolean hit = false;
    protected static final float CHANNEL_IN_TIME = 0.5F;
    protected float channelAnimTimer;
    protected Boolean AnimTimer = false;
    public AbstractSeal() {
        this.c = Settings.CREAM_COLOR.cpy();
        this.shineColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        this.hb = new Hitbox(96.0F * Settings.scale, 96.0F * Settings.scale);
        this.img = ImageMaster.EYE_ANIM_0;
        this.name = Sealstrings.NAME;
        this.bobEffect = new BobEffect(3.0F * Settings.scale, 3.0F);
        this.fontScale = 0.7F;
        this.showEvokeValue = true;
        this.channelAnimTimer = 0.5F;
    }
    public void renderText(SpriteBatch sb) {
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.BreakAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET - 4.0F * Settings.scale, new Color(0.2F, 1.0F, 1.0F, this.c.a), this.fontScale);
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.PainAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET + 20.0F * Settings.scale,new Color(0.5F, 0.0F, 3.0F, this.c.a), this.fontScale);

    }
    public void onStartOfTurn() {
        hit = false;
    }
    public void onEndOfTurn() {

    }
    @Override
    public void onReciveDamage(int damage) {
        hit = true;
        PainAmount -= damage;
        if (PainAmount <= 0){
            Break();
        }
    }
    public void Break(){
        AbstractSeal that = this;
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                DragonkinMod.Seals.remove(that);
                isDone = true;
            }
        });
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
    public void updateDescription() {

    }
    public void update() {
        this.hb.update();
        this.updateDescription();
        angle = (360f/DragonkinMod.Seals.size()) * DragonkinMod.Seals.indexOf(this);
        cX = (AbstractDungeon.player.hb.cX-100f) + (float)(dy2*Math.cos((Math.toRadians(angle))));
        cY = (AbstractDungeon.player.hb.cY+50f) + (float)(dy2*Math.sin(Math.toRadians(angle)));
        hb.move(cX, cY); //I think this is correct, but might not be. Might need some offset calculations
        if (this.hb.hovered) {
            TipHelper.renderGenericTip(this.cX + 96.0F * Settings.scale, this.cY + 64.0F * Settings.scale, this.name, this.description);
        }
        this.fontScale = MathHelper.scaleLerpSnap(this.fontScale, 0.7F);
    }
    static {
        NUM_X_OFFSET = 20.0F * Settings.scale;
        NUM_Y_OFFSET = -12.0F * Settings.scale;
    }

}

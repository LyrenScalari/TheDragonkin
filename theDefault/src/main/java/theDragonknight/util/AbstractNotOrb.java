package theDragonknight.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.vfx.BobEffect;

import java.util.ArrayList;

public class AbstractNotOrb {
    public String name;
    public String description;
    public String ID;
    public OrbStrings Sealstrings;
    protected ArrayList<PowerTip> tips = new ArrayList();
    public int BreakAmount = 0;
    public int PainAmount = 0;
    public int baseBreakAmount = 0;
    protected int basePainAmount = 0;
    public float cX = 0.0F;
    public float cY = 0.0F;
    public float tX;
    public float tY;
    protected Color c;
    protected Color shineColor;
    protected static final int W = 96;
    public static  float dy2 = 200;
    public static float angleSpeed = 0.00f;
    public static float angle;
    public Hitbox hb;
    protected TextureAtlas.AtlasRegion img;
    protected BobEffect bobEffect;
    protected static final float NUM_X_OFFSET;
    protected static final float NUM_Y_OFFSET;
    protected float scale;
    protected float fontScale;
    protected boolean showEvokeValue;
    protected static final float CHANNEL_IN_TIME = 0.5F;
    protected float channelAnimTimer;
    protected Boolean AnimTimer = false;

    public void renderText(SpriteBatch sb) {
    }
    public void onStartOfTurn() {

    }
    public void ApplyModifers() {

    }
    public void onEndOfTurn() {
    }
    public void updateAnimation() {

    }
    public void updateDescription() {

    }
    public void update() {

    }
    static {
        NUM_X_OFFSET = 20.0F * Settings.scale;
        NUM_Y_OFFSET = -12.0F * Settings.scale;
    }
}

package theDragonknight.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.HashMap;

public class DragonknightParticle extends AbstractGameEffect {
    private static final Texture WATER_TEXTURE = new Texture("theDragonknightResources/images/VFX/BloodSprite.png");
    private static final TextureRegion[] WATER = new TextureRegion[8];
    private static final int ANIM_FPS = 60;
    private static final float FRAME_DURATION = 1.0f / ANIM_FPS;
    private static HashMap<String, TextureRegion[]> regions;
    private float x;
    private float y;
    private int index = 0;
    private float rotation;
    private float scale;
    private float endDuration;
    private TextureRegion[] region;

    public DragonknightParticle(float x, float y, float rotation, float scale) {
        initializeRegions();
        region = regions.get("Blood");
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.scale = scale;
        endDuration = region.length * FRAME_DURATION;
        duration = 0;
    }

    public static void initializeRegions() {
        //Water texture: 4 x 2, 72x72 sections
        int wa = 0;
        for (int h = 0; h < 2; ++h) {
            for (int w = 0; w < 4; ++w) {
                WATER[wa] = new TextureRegion(WATER_TEXTURE, 72 * w, 72 * h, 72, 72);
                ++wa;
            }
        }
        regions = new HashMap<>();
        regions.put("Blood", WATER);
    }

    public void update() {
        duration += Gdx.graphics.getDeltaTime();
        index = (int)Math.floor((duration / endDuration) * region.length);
        if (index >= region.length) {
            isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
        TextureRegion img = region[index];
        float w = img.getRegionWidth();
        float h = img.getRegionHeight();
        sb.draw(img, x - (w / 2.0f), y - (h / 2.0f), (w / 2.0f), (h / 2.0f), w, h, scale * Settings.scale, scale * Settings.scale, rotation);
    }

    public void dispose() {

    }
}
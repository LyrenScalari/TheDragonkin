package theDragonkin.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class ChiParticleEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private float oX;
    private float oY;
    private float vX;
    private float vY;
    private float scale;
    private float angle;
    private float dur_div2;
    private TextureAtlas.AtlasRegion img;
    private final Color defaultColor = new Color(0.0f / 255.0F, 168.0f / 255.0F, 107f / 255.0F, 1.0F);
    public ChiParticleEffect(float x, float y, float oX, float oY, float scale, float angle) {
        this.img = ImageMaster.GLOW_SPARK_2;
        this.x = x;
        this.y = y;
        this.oX = oX;
        this.oY = oY;
        this.scale = scale * MathUtils.random(0.4F, 0.7F);
        this.angle = angle + MathUtils.random(-8.0F, 8.0F);
        color = defaultColor;

        this.renderBehind = MathUtils.randomBoolean(0.2F + (this.scale - 0.5F));
        this.duration = MathUtils.random(0.8F, 1.0F);
        this.dur_div2 = this.duration / 2.0F;
        this.vX = MathUtils.random(-5.0F, 5.0F) * Settings.scale;
        this.vY = MathUtils.random(-7.0F, 7.0F) * Settings.scale;
    }
    public void update() {
        if (this.duration > this.dur_div2) {
            this.color.a = Interpolation.pow3In.apply(0.75F, 0.0F, (this.duration - this.dur_div2) / this.dur_div2);
        } else {
            this.color.a = Interpolation.pow3In.apply(0.0F, 0.75F, this.duration / this.dur_div2);
        }
        //this.color.a = Interpolation.fade.apply(1F, 0F, duration);

        this.oX += this.vX * Gdx.graphics.getDeltaTime();
        this.oY += this.vY * Gdx.graphics.getDeltaTime();
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.setBlendFunction(770, 1);

        sb.draw(img, x, y, oX, oY, (float) img.packedWidth, (float) img.packedHeight, scale * Settings.scale, scale * Settings.scale, angle);

        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}

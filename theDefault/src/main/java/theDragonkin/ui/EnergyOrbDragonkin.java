package theDragonkin.ui;

import basemod.abstracts.CustomEnergyOrb;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.megacrit.cardcrawl.core.Settings;
import theDragonkin.DragonkinMod;
import theDragonkin.util.ImageHelper;

public class EnergyOrbDragonkin extends CustomEnergyOrb
{
    private static final float ORB_IMG_SCALE = 1.15f * Settings.scale;

    private static final int LAYER_COUNT = 4;
    private Texture mask;
    private Texture border;
    private Texture[] layers = new Texture[LAYER_COUNT];
    private Texture[] layers_d = new Texture[LAYER_COUNT];
    private float[] angles = new float[LAYER_COUNT];
    private FrameBuffer fbo;
    public EnergyOrbDragonkin()
    {
        super(null, null, null);
        fbo = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false, false);
        orbVfx = AssetLoader.loadImage("theDragonkinResources/images/char/defaultCharacter/orb/vfx.png");
        border =  AssetLoader.loadImage("theDragonkinResources/images/char/defaultCharacter/orb/border.png");
        mask = AssetLoader.loadImage("theDragonkinResources/images/char/defaultCharacter/orb/mask.png");
        for (int i=0; i<LAYER_COUNT; ++i) {
            layers[i] = AssetLoader.loadImage("theDragonkinResources/images/char/defaultCharacter/orb/" + (i) + ".png");
        }
        for (int i=0; i<LAYER_COUNT; ++i) {
            layers_d[i] = AssetLoader.loadImage("theDragonkinResources/images/char/defaultCharacter/orb/" + (i) + "d.png");
        }
    }

    @Override
    public void updateOrb(int energyCount)
    {
        if (energyCount == 0) {
            angles[0] += Gdx.graphics.getDeltaTime() * -5;
            angles[1] += Gdx.graphics.getDeltaTime() * 5;
            angles[2] += Gdx.graphics.getDeltaTime() * -8;
            angles[3] += Gdx.graphics.getDeltaTime() * 8;
        } else {
            angles[0] += Gdx.graphics.getDeltaTime() * -20;
            angles[1] += Gdx.graphics.getDeltaTime() * 20;
            angles[2] += Gdx.graphics.getDeltaTime() * -40;
            angles[3] += Gdx.graphics.getDeltaTime() * 40;
        }
    }

    @Override
    public void renderOrb(SpriteBatch sb, boolean enabled, float current_x, float current_y)
    {
        sb.setColor(Color.WHITE);
        sb.end();
        fbo.begin();

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glColorMask(true, true, true, true);
        sb.begin();

        sb.setColor(Color.WHITE);
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        if (enabled) {
            for (int i=0; i<LAYER_COUNT; ++i) {
                sb.draw(layers[i], current_x - 64, current_y - 64, 64, 64, 148, 148, ORB_IMG_SCALE, ORB_IMG_SCALE, angles[i], 0, 0, 128, 128, false, false);
            }
        } else {
            for (int i = 0; i < LAYER_COUNT; ++i) {
                sb.draw(layers_d[i], current_x - 64, current_y - 64, 64, 64, 148, 148, ORB_IMG_SCALE, ORB_IMG_SCALE, angles[i], 0, 0, 128, 128, false, false);
            }
        }

        sb.setBlendFunction(0, GL20.GL_SRC_ALPHA);
        sb.setColor(new Color(1, 1, 1, 1));
        sb.draw(mask, current_x - 64, current_y - 64, 64, 64, 180, 180, ORB_IMG_SCALE, ORB_IMG_SCALE, 0, 0, 0, 128, 128, false, false);
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        sb.end();

        fbo.end();
        sb.begin();
        TextureRegion drawTex = new TextureRegion(fbo.getColorBufferTexture());
        drawTex.flip(false, true);
        sb.draw(drawTex, -Settings.VERT_LETTERBOX_AMT, -Settings.HORIZ_LETTERBOX_AMT);
        sb.draw(border, current_x - 64, current_y - 64, 64, 64, 128, 128, ORB_IMG_SCALE, ORB_IMG_SCALE, 0, 0, 0, 128, 128, false, false);
    }
}
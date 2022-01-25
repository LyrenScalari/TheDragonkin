package theDragonkin.patches;

import IconsAddon.damageModifiers.AbstractDamageModifier;
import IconsAddon.util.DamageModifierManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theDragonkin.DragonkinMod;
import theDragonkin.util.ChiParticleEffect;

@SpirePatch2(clz = AbstractPlayer.class, method = "render")
public class RenderFloatyChi {
    public static float angleSpeed = 1f;
    private static float angle;
    private static  float dy = 110;
    public static  float dx = 0;
    private static  float dy2 = 90;
    public static  float dx2 = 0;
    private static final float renderScale = 2f;
    private static float particleTimer;
    private static final float INTERVAL = 0.1f;
    public static float amplitude;
    public static float bobTimer;
    public static float bobX;
    @SpirePrefixPatch
    public static void patch(AbstractPlayer __instance, SpriteBatch sb) {
        particleTimer -= Gdx.graphics.getRawDeltaTime();
        if (angleSpeed > 1) {
            angleSpeed -= (Gdx.graphics.getDeltaTime());
        }
        angle -= 100*angleSpeed*Gdx.graphics.getDeltaTime();
        angle %= 360;
        bobTimer -=angleSpeed*Gdx.graphics.getDeltaTime();
        bobTimer %= 360;
        sb.setColor(Color.WHITE.cpy());
        float da = 360f/ChiField.Chi.get(AbstractDungeon.player);
        float a = 0;
        for (int i = 0; i < ChiField.Chi.get(AbstractDungeon.player) ; i++) {
            if (particleTimer < 0.0F) {
                AbstractDungeon.effectList.add(new ChiParticleEffect(__instance.hb.cX + dx + bobX, __instance.hb.cY + dy, -dx - bobX, -dy, renderScale, angle+a));
            }
            TextureAtlas.AtlasRegion img = DragonkinMod.TypeEnergyAtlas.findRegion("[Chi]");
            sb.draw(img, __instance.hb.cX + dx2 + bobX + img.offsetX - (float) img.originalWidth / 2.0F,
                    __instance.hb.cY + dy2 + img.offsetY - (float) img.originalHeight / 2.0F,
                    (float) img.originalWidth / 2.0F - img.offsetX - dx2 - bobX, (float) img.originalHeight / 2.0F - img.offsetY - dy2,
                    (float) img.packedWidth, (float) img.packedHeight,
                    renderScale * Settings.scale, renderScale * Settings.scale, angle+a);
            a += da;
        }
        if (particleTimer < 0.0F) {
            particleTimer = INTERVAL/angleSpeed;
        }
    }
}


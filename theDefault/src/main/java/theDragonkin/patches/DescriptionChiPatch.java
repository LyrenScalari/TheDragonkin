package theDragonkin.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import javassist.CtBehavior;
import theDragonkin.DragonkinMod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DescriptionChiPatch {
    @SpirePatch(
            clz = AbstractCard.class,
            method = "renderDescription"
    )
    @SpirePatch(
            clz = AbstractCard.class,
            method = "renderDescriptionCN"
    )
    public static class RenderTypeEnergy {
        private static final float CARD_ENERGY_IMG_WIDTH = 24.0f * Settings.scale;

        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"spacing", "i", "start_x", "draw_y", "font", "textColor", "tmp", "gl"}
        )
        public static void Insert(AbstractCard __instance, SpriteBatch sb,
                                  float spacing, int i, @ByRef float[] start_x, float draw_y,
                                  BitmapFont font, Color textColor, @ByRef String[] tmp, GlyphLayout gl) {
            if (tmp[0].length() > 0 && tmp[0].charAt(0) == '[') {
                String key = tmp[0].trim();
                if (key.endsWith("En]")) {
                    key = key.replace("*d", "D").replace("*b", "B").replace("*m", "M");
                }
                if (DragonkinMod.TypeEnergyAtlas.findRegion(key) != null) {
                    gl.width = CARD_ENERGY_IMG_WIDTH * __instance.drawScale;
                    renderTypeEnergy(__instance, sb, DragonkinMod.TypeEnergyAtlas.findRegion(key),
                            (start_x[0] - __instance.current_x) / Settings.scale / __instance.drawScale,
                            (-98.0f - ((__instance.description.size() - 4.0f) / 2.0f - i + 1.0f) * spacing));
                    start_x[0] += gl.width;
                    tmp[0] = "";
                }
            }
        }

        public static void renderTypeEnergy(AbstractCard card, SpriteBatch sb, TextureAtlas.AtlasRegion region, float offsetX, float offsetY) {

            Affine2 aff = new Affine2();
            aff.setToTrnRotScl(
                    card.current_x + offsetX * card.drawScale * Settings.scale,
                    card.current_y + offsetY * card.drawScale * Settings.scale,
                    MathUtils.degreesToRadians * card.angle,
                    card.drawScale * Settings.scale,
                    card.drawScale * Settings.scale
            );
            sb.draw(
                    region,
                    region.packedWidth,
                    region.packedHeight,
                    aff
            );
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(GlyphLayout.class, "setText");
            int[] lines = LineFinder.findAllInOrder(ctBehavior, matcher);
            return new int[]{lines[lines.length - 1]}; // Only last occurrence
        }
    }
    @SpirePatch(
            clz= SingleCardViewPopup.class,
            method="renderDescription"
    )
    @SpirePatch(
            clz=SingleCardViewPopup.class,
            method="renderDescriptionCN"
    )
    public static class RenderSmallNoteSingleCardView
    {
        @SpireInsertPatch(
                locator=Locator.class,
                localvars={
                        "spacing", "i", "start_x", "tmp", "gl",
                        "card_energy_w", "drawScale", "current_x", "card"
                }
        )
        public static void Insert(SingleCardViewPopup __instance, SpriteBatch sb,
                                  float spacing, int i, @ByRef float[] start_x,
                                  @ByRef String[] tmp, GlyphLayout gl,
                                  float card_energy_w, float drawScale, float current_x, AbstractCard card)
        {
            if (tmp[0].length() > 0 && tmp[0].charAt(0) == '[') {
                String key = tmp[0].trim();
                if (key.endsWith("Chi]")) {
                    key = key.replace("*d", "D").replace("*b", "B").replace("*m", "M");
                }

                if (DragonkinMod.TypeEnergyAtlas.findRegion(key) != null) {
                    gl.width = card_energy_w * drawScale;
                    ShaderProgram oldShader = sb.getShader();
                    try {
                        Method renderSmallEnergy = SingleCardViewPopup.class.getDeclaredMethod("renderSmallEnergy", SpriteBatch.class, TextureAtlas.AtlasRegion.class, float.class, float.class);
                        renderSmallEnergy.setAccessible(true);

                        renderSmallEnergy.invoke(__instance, sb, DragonkinMod.TypeEnergyAtlas.findRegion(key),
                                (start_x[0] - current_x) / Settings.scale / drawScale,
                                -86.0f - ((card.description.size() - 4.0f) / 2.0f - i + 1.0f) * spacing);
                        sb.flush();
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    } finally {
                        sb.setShader(oldShader);
                    }
                    start_x[0] += gl.width;
                    tmp[0] = "";
                }
            }
        }

        private static class Locator extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception
            {
                Matcher matcher = new Matcher.MethodCallMatcher(GlyphLayout.class, "setText");
                int[] lines = LineFinder.findAllInOrder(ctBehavior, matcher);
                return new int[]{lines[lines.length-1]}; // Only last occurrence
            }
        }
    }


    @SpirePatch(
            clz= AbstractCard.class,
            method="initializeDescription"
    )
    public static class AlterNoteDescriptionSize
    {
        private static final float CARD_ENERGY_IMG_WIDTH = 16.0f * Settings.scale;

        @SpireInsertPatch(
                locator=Locator.class,
                localvars={"gl", "word"}
        )
        public static void Insert(AbstractCard __instance, @ByRef GlyphLayout[] gl, String word)
        {
            if (word.length() > 0 && word.charAt(0) == '[') {
                if (word.equals("[Chi]")) {
                    gl[0].setText(FontHelper.cardDescFont_N, " ");
                    gl[0].width = CARD_ENERGY_IMG_WIDTH;
                }
            }
        }

        private static class Locator extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception
            {
                Matcher matcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "DESC_BOX_WIDTH");
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }
}

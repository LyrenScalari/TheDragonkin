package theDragonknight.patches.DivineArmorPatches;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import javassist.CtBehavior;
import theDragonknight.DragonkinMod;

import java.lang.reflect.Field;
import java.util.ArrayList;

@SpirePatch(
        clz = AbstractCreature.class,
        method = "renderHealth"
)

public class RenderHealthBar {
    private static float HEALTH_BAR_HEIGHT = -1.0F;
    private static float HEALTH_BAR_OFFSET_Y = -1.0F;

    public RenderHealthBar() {
    }

    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"x", "y"}
    )
    public static void Insert(AbstractCreature __instance, SpriteBatch sb, float x, float y) {
        if (HEALTH_BAR_HEIGHT == -1.0F) {
            HEALTH_BAR_HEIGHT = 20.0F * Settings.scale;
            HEALTH_BAR_OFFSET_Y = -28.0F * Settings.scale;
        }

        if (!Gdx.input.isKeyPressed(36) && (Integer) DivineArmorField.DivineArmor.get(__instance) > 0 && __instance.hbAlpha > 0.0F) {
            renderTempHPIconAndValue(__instance, sb, x, y);
        }

    }

    private static <O, T> T getPrivate(Class<O> cls, Object obj, String varName, Class<T> type) {
        try {
            Field f = cls.getDeclaredField(varName);
            f.setAccessible(true);
            return type.cast(f.get(obj));
        } catch (IllegalAccessException | NoSuchFieldException var5) {
            var5.printStackTrace();
            return null;
        }
    }

    private static <O, T> T getPrivate(Class<O> obj, String varName, Class<T> type) {
        return getPrivate(obj, (Object)null, varName, type);
    }

    private static void renderTempHPIconAndValue(AbstractCreature creature, SpriteBatch sb, float x, float y) {
        sb.setColor(Settings.LIGHT_YELLOW_COLOR);
        sb.draw(DragonkinMod.DIVINE_ARMOR_ICON, x + (Float)getPrivate(AbstractCreature.class, "BLOCK_ICON_X", Float.class) - 60.0F,
                y + (Float)getPrivate(AbstractCreature.class, "BLOCK_ICON_Y", Float.class) - 30.0F, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        FontHelper.renderFontCentered(sb, FontHelper.blockInfoFont, Integer.toString((Integer)DivineArmorField.DivineArmor.get(creature)),
                x + (Float)getPrivate(AbstractCreature.class, "BLOCK_ICON_X", Float.class) - 28.0F, y - 14.0F * Settings.scale, Settings.CREAM_COLOR, 1.0F);
    }

    private static class Locator extends SpireInsertLocator {
        private Locator() {
        }

        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCreature.class, "currentBlock");
            ArrayList<Matcher> matchers = new ArrayList();
            matchers.add(finalMatcher);
            return LineFinder.findInOrder(ctBehavior, matchers, finalMatcher);
        }
    }
}
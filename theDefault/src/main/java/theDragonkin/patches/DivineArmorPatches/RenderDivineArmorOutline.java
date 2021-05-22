package theDragonkin.patches.DivineArmorPatches;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import javassist.CtBehavior;

@SpirePatch(
        clz = AbstractCreature.class,
        method = "renderHealth"
)
public class RenderDivineArmorOutline {
    private static float HEALTH_BAR_HEIGHT = -1.0F;
    private static float HEALTH_BAR_OFFSET_Y = -1.0F;

    public RenderDivineArmorOutline() {
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
            renderTempHPOutline(__instance, sb, x, y);
        }

    }

    private static void renderTempHPOutline(AbstractCreature creature, SpriteBatch sb, float x, float y) {
        sb.setColor(Settings.LIGHT_YELLOW_COLOR);
        sb.setBlendFunction(770, 1);
        sb.draw(ImageMaster.BLOCK_BAR_L, x - HEALTH_BAR_HEIGHT, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
        sb.draw(ImageMaster.BLOCK_BAR_B, x, y + HEALTH_BAR_OFFSET_Y, creature.hb.width, HEALTH_BAR_HEIGHT);
        sb.draw(ImageMaster.BLOCK_BAR_R, x + creature.hb.width, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
        sb.setBlendFunction(770, 771);
    }

    private static class Locator extends SpireInsertLocator {
        private Locator() {
        }

        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCreature.class, "currentBlock");
            return LineFinder.findInOrder(ctBehavior, finalMatcher);
        }
    }
}

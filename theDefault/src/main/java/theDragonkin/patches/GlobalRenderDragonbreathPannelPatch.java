package theDragonkin.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import theDragonkin.util.EasyInfoDisplayPanel;
import theDragonkin.util.SuperTip;
@SpirePatch(
        clz = AbstractPlayer.class,
        method = "render",
        paramtypez = {SpriteBatch.class}
)
public class GlobalRenderDragonbreathPannelPatch {
    @SpirePostfixPatch
    public static void Postfix(AbstractPlayer __instance, SpriteBatch sb) {
        SuperTip.render(sb, EasyInfoDisplayPanel.RENDER_TIMING.TIMING_ENERGYPANEL_RENDER);
    }
}

package theDragonkin.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import theDragonkin.DragonkinMod;
import theDragonkin.util.AbstractNotOrb;
import theDragonkin.util.AbstractSeal;

@SpirePatch2(clz = AbstractPlayer.class, method = "render")
public class RenderActiveSealsPatch {
    @SpirePostfixPatch
    public static void patch(AbstractPlayer __instance, SpriteBatch sb) {
        for (AbstractNotOrb seal : DragonkinMod.Seals){
            seal.update();
            seal.updateAnimation();
            seal.updateDescription();
            seal.renderText(sb);
        }
    }
}
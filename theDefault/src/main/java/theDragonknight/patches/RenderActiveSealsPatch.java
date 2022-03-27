package theDragonknight.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import theDragonknight.DragonkinMod;
import theDragonknight.util.AbstractNotOrb;

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

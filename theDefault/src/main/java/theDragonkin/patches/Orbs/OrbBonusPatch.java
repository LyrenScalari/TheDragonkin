package theDragonkin.patches.Orbs;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Plasma;
import theDragonkin.orbs.ModifyOrbStance;


@SpirePatch(
        cls = "com.megacrit.cardcrawl.orbs.AbstractOrb",
        method = "applyFocus"
)
public class OrbBonusPatch {
    @SpirePostfixPatch
    public static void OrbBonus(AbstractOrb __instance) {
        if (!(__instance instanceof Plasma)) {
            if (AbstractDungeon.player.stance instanceof ModifyOrbStance){
                ((ModifyOrbStance)AbstractDungeon.player.stance).ModifyOrb(__instance);
            }
        }
    }
}

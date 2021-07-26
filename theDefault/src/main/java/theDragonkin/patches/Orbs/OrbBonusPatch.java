package theDragonkin.patches.Orbs;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Plasma;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.orbs.ModifyOrbStance;
import theDragonkin.powers.Deathspeaker.modifyOrbPower;


@SpirePatch(
        cls = "com.megacrit.cardcrawl.orbs.AbstractOrb",
        method = "applyFocus"
)
public class OrbBonusPatch {
    @SpirePostfixPatch
    public static void OrbBonus(AbstractOrb __instance) {
        if (!(__instance instanceof Plasma)) {
            for (AbstractPower abstractPower : AbstractDungeon.player.powers) {
                if (abstractPower instanceof modifyOrbPower) {
                    ((modifyOrbPower) abstractPower).OrbBonusPower(__instance);
                }
            }
            if (AbstractDungeon.player.stance instanceof ModifyOrbStance){
                ((ModifyOrbStance)AbstractDungeon.player.stance).ModifyOrb(__instance);
            }
        }
    }
}

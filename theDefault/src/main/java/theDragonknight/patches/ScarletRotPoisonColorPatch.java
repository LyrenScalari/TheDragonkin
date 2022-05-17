package theDragonknight.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import javassist.CtBehavior;
import theDragonknight.DragonknightMod;
import theDragonknight.orbs.OnUseCardOrb;
import theDragonknight.patches.Orbs.OnUseCardOrbPatch;
import theDragonknight.powers.RotPower;
import theDragonknight.util.AbstractDragonMark;
import theDragonknight.util.AbstractNotOrb;
@SpirePatch2(
        clz = AbstractCreature.class,
        method = "renderGreenHealthBar"
)
public class ScarletRotPoisonColorPatch {
    public ScarletRotPoisonColorPatch() {
    }

    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"sb"}
    )
    public static void Insert(AbstractCreature __instance, SpriteBatch sb) {
        if (__instance.hasPower(RotPower.POWER_ID)){
            sb.setColor(((HealthBarRenderPower)__instance.getPower(RotPower.POWER_ID)).getColor());
        }
    }
    private static class Locator extends SpireInsertLocator {
        private Locator() {
        }

        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCreature.class, "currentHealth");
            return LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}

package theDragonknight.patches.Orbs;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import javassist.CtBehavior;
import theDragonknight.DragonknightMod;
import theDragonknight.orbs.OnUseCardOrb;
import theDragonknight.util.AbstractDragonMark;
import theDragonknight.util.AbstractNotOrb;

import java.util.ArrayList;

@SpirePatch(clz = UseCardAction.class, method = "update")
public class OnUseCardOrbPatch {
    public OnUseCardOrbPatch() {
    }

    @SpireInsertPatch(
            locator = OnUseCardOrbPatch.Locator.class,
            localvars = {"targetCard"}
    )
    public static void Insert(UseCardAction __instance, AbstractCard targetCard) {

        for (AbstractNotOrb abstractOrb : DragonknightMod.Seals) {
            AbstractDragonMark Orb = (AbstractDragonMark) abstractOrb;
            if (Orb instanceof OnUseCardOrb) {
                ((OnUseCardOrb) Orb).onUseCardOrb(targetCard, __instance);
            }
        }

    }

    private static class Locator extends SpireInsertLocator {
        private Locator() {
        }

        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractDungeon.class, "getMonsters");
            return LineFinder.findInOrder(ctBehavior, new ArrayList(), finalMatcher);
        }
    }
}

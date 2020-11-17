package theDragonkin.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import javassist.CtBehavior;
import theDragonkin.orbs.OnUseCardOrb;

import java.util.ArrayList;
import java.util.Iterator;

@SpirePatch(clz = UseCardAction.class, method = "update")
public class OnUseCardOrbPatch {
    public OnUseCardOrbPatch() {
    }

    @SpireInsertPatch(
            locator = OnUseCardOrbPatch.Locator.class,
            localvars = {"targetCard"}
    )
    public static void Insert(UseCardAction __instance, AbstractCard targetCard) {

        for (AbstractOrb abstractOrb : AbstractDungeon.player.orbs) {
            AbstractOrb Orb = abstractOrb;
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

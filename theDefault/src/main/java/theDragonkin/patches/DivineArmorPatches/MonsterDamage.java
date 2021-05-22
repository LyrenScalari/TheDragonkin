package theDragonkin.patches.DivineArmorPatches;

import com.evacipated.cardcrawl.mod.stslib.patches.tempHp.PlayerDamage;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CtBehavior;

@SpirePatch(
        clz = AbstractMonster.class,
        method = "damage"
)
public class MonsterDamage {
    public MonsterDamage() {
    }

    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"damageAmount", "hadBlock"}
    )
    public static void Insert(AbstractMonster __instance, DamageInfo info, @ByRef int[] damageAmount, @ByRef boolean[] hadBlock) {
        PlayerDamage.Insert(__instance, info, damageAmount, hadBlock);
    }

    private static class Locator extends SpireInsertLocator {
        private Locator() {
        }

        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractMonster.class, "decrementBlock");
            return offset(LineFinder.findInOrder(ctMethodToPatch, finalMatcher), 0);
        }

        private static int[] offset(int[] originalArr, int offset) {
            for(int i = 0; i < originalArr.length; ++i) {
                originalArr[i] += offset;
            }

            return originalArr;
        }
    }
}

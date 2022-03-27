package theDragonkin.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import javassist.CtBehavior;
import theDragonkin.Stances.ModifyBlockStance;
import theDragonkin.orbs.onDrawCardOrb;

@SpirePatch(clz = AbstractCard.class, method = "applyPowersToBlock")
public class ModifyBlockStancePatch {
    @SpireInsertPatch(
            locator= EvokeLocator.class,
            localvars = {"tmp"}
    )
    public static void onCardDrawOrb(AbstractCard __instance,@ByRef float[] tmp) {
        if (AbstractDungeon.player.stance instanceof ModifyBlockStance){
            tmp[0] = ((ModifyBlockStance) AbstractDungeon.player.stance).modifyBlock(tmp[0]);
        }
    }

    private static class EvokeLocator extends SpireInsertLocator {
        private EvokeLocator() {
        }

        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "baseBlock");
            int[] tmp = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
            return new int[]{tmp[1]};
        }
    }
}

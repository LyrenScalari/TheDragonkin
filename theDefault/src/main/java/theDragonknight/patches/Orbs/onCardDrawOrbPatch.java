package theDragonknight.patches.Orbs;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import javassist.CtBehavior;
import theDragonknight.orbs.onDrawCardOrb;

@SpirePatch(clz = AbstractPlayer.class, method = "draw",
        paramtypez={int.class})
public class onCardDrawOrbPatch {
    @SpireInsertPatch(
            locator= EvokeLocator.class
    )
    public static void onCardDrawOrb(AbstractPlayer __instance) {
        for (AbstractOrb o : AbstractDungeon.player.orbs) {
            if (o instanceof onDrawCardOrb) {
                ((onDrawCardOrb) o).OnDrawCard();
            }
        }
    }

    private static class EvokeLocator extends SpireInsertLocator {
        private EvokeLocator() {
        }

        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "triggerWhenDrawn");
            int[] tmp = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
            return new int[]{tmp[0]};
        }
    }
}

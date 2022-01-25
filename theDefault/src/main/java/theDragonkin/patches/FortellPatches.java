package theDragonkin.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import javassist.CtBehavior;
import theDragonkin.cards.Drifter.ForetellCard;

@SpirePatch(clz = AbstractPlayer.class, method = "applyStartOfTurnRelics")

public class FortellPatches {
    @SpireInsertPatch(
            locator= FortellPatches.EvokeLocator.class
    )
    public static void onCardDrawOrb(AbstractPlayer __instance) {
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group){
            if (c instanceof ForetellCard){
                c.triggerAtStartOfTurn();
            }
        }
    }
    private static class EvokeLocator extends SpireInsertLocator {
        private EvokeLocator() {
        }

        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class,"stance");
            int[] tmp = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
            return new int[]{tmp[0]};
        }
    }
}

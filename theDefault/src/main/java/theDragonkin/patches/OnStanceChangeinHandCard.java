package theDragonkin.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import javassist.CtBehavior;
import theDragonkin.orbs.onDrawCardOrb;

@SpirePatch(clz = ChangeStanceAction.class, method = "update")
public class OnStanceChangeinHandCard {
    @SpireInsertPatch(
            locator= EvokeLocator.class,
            localvars = {"newStance"}
    )
    public static void onCardDrawOrb(AbstractGameAction __instance,AbstractStance newStance) {
        for (AbstractCard c : AbstractDungeon.player.hand.group){
            c.triggerExhaustedCardsOnStanceChange(newStance);
        }
    }
    private static class EvokeLocator extends SpireInsertLocator {
        private EvokeLocator() {
        }

        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractStance.class, "onExitStance");
            int[] tmp = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
            return new int[]{tmp[0]};
        }
    }
}

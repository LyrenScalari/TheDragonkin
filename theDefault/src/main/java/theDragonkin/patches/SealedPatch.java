package theDragonkin.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import javassist.CtBehavior;
import theDragonkin.DragonkinMod;
import theDragonkin.cards.Dragonkin.AbstractPrimalCard;
import theDragonkin.patches.Orbs.onCardDrawOrbPatch;

@SpirePatch2(clz = DiscardAction.class, method = "update")
public class SealedPatch {
    @SpireInsertPatch(
            locator= DiscardLocator.class,
            localvars = {"c"}
    )
    public static void SealedPatch(DiscardAction __instance, AbstractCard c){
        if (c instanceof AbstractPrimalCard || c.type == AbstractCard.CardType.STATUS){
            DragonkinMod.TriggerOnCycle(c);
        }
    }
    private static class DiscardLocator extends SpireInsertLocator {
        private DiscardLocator() {
        }

        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "triggerOnManualDiscard");
            int[] tmp = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
            return new int[]{tmp[0]};
        }
    }
}

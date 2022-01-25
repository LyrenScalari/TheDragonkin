package theDragonkin.patches;

import basemod.ReflectionHacks;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import javassist.CtBehavior;
import org.lwjgl.Sys;
import theDragonkin.CardMods.ForetellCardMod;
import theDragonkin.cards.Drifter.ForetellCard;

public class FortellRedirectPatch {
    @SpirePatch(
            clz = UseCardAction.class,
            method = SpirePatch.CLASS
    )

    @SpirePatch(
            clz = UseCardAction.class,
            method = "update"
    )
    public static class UseCardActionInsertPatch {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static SpireReturn Insert(UseCardAction __instance) {
            //quick fix, remove cards from limbo for multi-play effects
            AbstractDungeon.player.limbo.removeCard((AbstractCard) ReflectionHacks.getPrivate(__instance, UseCardAction.class, "targetCard"));
            boolean foretold = false;
            //if the played card was transmuted, handle separately from regular UseCardAction logic
            if (ReflectionHacks.getPrivate(__instance, UseCardAction.class, "targetCard") instanceof ForetellCard) {
                AbstractCard card = ReflectionHacks.getPrivate(__instance, UseCardAction.class, "targetCard");
                for (AbstractCardModifier ca : CardModifierManager.modifiers(card)){
                    if (ca instanceof ForetellCardMod){
                        foretold = ((ForetellCardMod)ca).Foretell;
                    }
                }
                if (foretold){
                card.freeToPlayOnce = false;
                card.isInAutoplay = false;
                card.exhaustOnUseOnce = false;
                card.dontTriggerOnUseCard = false;
                CardStrings cardStrings =  CardCrawlGame.languagePack.getCardStrings(card.cardID);
                card.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
                card.resetAttributes();
                ((ForetellCard)card).Foretold();
                AbstractDungeon.player.hand.moveToExhaustPile(card);
                __instance.isDone = true;
                System.out.println(card.rawDescription);
                return SpireReturn.Return(null);
                } else return SpireReturn.Continue();
            }else {
                return SpireReturn.Continue();
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "freeToPlayOnce");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}

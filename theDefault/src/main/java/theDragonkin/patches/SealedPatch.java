package theDragonkin.patches;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;
import theDragonkin.DragonkinMod;
import theDragonkin.cards.Dragonkin.AbstractPrimalCard;
import theDragonkin.patches.Orbs.onCardDrawOrbPatch;
import theDragonkin.util.TriggerOnCycleEffect;
import theDragonkin.util.TriggerOnCycleMod;

import java.util.ArrayList;

@SpirePatch2(clz = AbstractCard.class, method = "triggerOnManualDiscard")
public class SealedPatch {
    @SpirePrefixPatch()
    public static void SealedPatch(AbstractCard __instance){
        if (AbstractDungeon.player.discardPile.getTopCard() instanceof AbstractPrimalCard ||AbstractDungeon.player.discardPile.getTopCard().type == AbstractCard.CardType.STATUS){
            for (AbstractCard ca : AbstractDungeon.player.discardPile.group){
                if (ca instanceof TriggerOnCycleEffect){
                    ((TriggerOnCycleEffect) ca).TriggerOnCycle(AbstractDungeon.player.discardPile.getTopCard());
                }
                for (AbstractCardModifier mod : CardModifierManager.modifiers(ca)) {
                    if (mod instanceof TriggerOnCycleMod) {
                        ((TriggerOnCycleMod) mod).TriggerOnCycle(ca, AbstractDungeon.player.discardPile.getTopCard());
                    }
                }
            }
            for (AbstractCard ca : AbstractDungeon.player.hand.group){
                if (ca instanceof TriggerOnCycleEffect){
                    ((TriggerOnCycleEffect) ca).TriggerOnCycle(AbstractDungeon.player.discardPile.getTopCard());
                }
                for (AbstractCardModifier mod : CardModifierManager.modifiers(ca)) {
                    if (mod instanceof TriggerOnCycleMod) {
                        ((TriggerOnCycleMod) mod).TriggerOnCycle(ca, AbstractDungeon.player.discardPile.getTopCard());
                    }
                }
            }
            for (AbstractCard ca : AbstractDungeon.player.drawPile.group){
                if (ca instanceof TriggerOnCycleEffect){
                    ((TriggerOnCycleEffect) ca).TriggerOnCycle(AbstractDungeon.player.discardPile.getTopCard());
                }
                for (AbstractCardModifier mod : CardModifierManager.modifiers(ca)) {
                    if (mod instanceof TriggerOnCycleMod) {
                        ((TriggerOnCycleMod) mod).TriggerOnCycle(ca, AbstractDungeon.player.discardPile.getTopCard());
                    }
                }
            }
            for (AbstractCard ca : AbstractDungeon.player.exhaustPile.group){
                if (ca instanceof TriggerOnCycleEffect){
                    ((TriggerOnCycleEffect) ca).TriggerOnCycle(AbstractDungeon.player.discardPile.getTopCard());
                }
                for (AbstractCardModifier mod : CardModifierManager.modifiers(ca)) {
                    if (mod instanceof TriggerOnCycleMod) {
                        ((TriggerOnCycleMod) mod).TriggerOnCycle(ca, AbstractDungeon.player.discardPile.getTopCard());
                    }
                }
            }
        }
    }
}

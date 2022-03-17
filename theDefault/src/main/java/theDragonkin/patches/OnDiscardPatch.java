package theDragonkin.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import javassist.CtBehavior;
import theDragonkin.DragonkinMod;
import theDragonkin.util.AbstractNotOrb;
import theDragonkin.util.AbstractRune;
import theDragonkin.util.TriggerOnCycleEffect;

@SpirePatch2(clz = AbstractPlayer.class, method = "updateCardsOnDiscard")
public class OnDiscardPatch {
    @SpirePostfixPatch
    public static void OnDiscardPatch() {
        for (AbstractNotOrb notOrb : DragonkinMod.Seals){
            if (notOrb instanceof AbstractRune){
                ((AbstractRune) notOrb).onManualDiscard();
            }
        }
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof TriggerOnCycleEffect) {
                ((TriggerOnCycleEffect) p).TriggerOnCycle(null);
            }
        }
    }
}

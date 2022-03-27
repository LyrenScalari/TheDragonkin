package theDragonknight.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonknight.DragonkinMod;
import theDragonknight.util.AbstractNotOrb;
import theDragonknight.util.AbstractRune;
import theDragonknight.util.TriggerOnCycleEffect;

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

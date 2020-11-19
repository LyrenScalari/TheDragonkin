package theDragonkin.patches;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theDragonkin.CardMods.TailwindCardmod;
import static theDragonkin.cards.Gremory.AbstractMagicGremoryCard.AllCards;

@SpirePatch(clz = AbstractPlayer.class, method = "applyStartOfTurnCards")
public class TailWindRemovalPatch {
    public static void Prefix(){
        for (AbstractCard c : AllCards.group) {
            for (AbstractCardModifier m : CardModifierManager.modifiers(c)) {
                AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                    @Override
                    public void update() {
                        if (m instanceof TailwindCardmod) {
                            CardModifierManager.removeSpecificModifier(c, m, true);
                            isDone = true;
                        }
                        isDone = true;
                    }
                });
            }
        }
    }
}

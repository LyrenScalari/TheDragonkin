package theDragonkin.patches;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theDragonkin.CardMods.TailwindCardmod;
import theDragonkin.powers.GroveKeeper.AlignmentPower;

import static theDragonkin.cards.Gremory.AbstractMagicGremoryCard.AllCards;
import static theDragonkin.characters.TheGroveKeeper.Enums.THE_GROVEKEEPER;

@SpirePatch(clz = AbstractPlayer.class, method = "applyStartOfTurnCards")
public class TailWindRemovalPatch {
    public static void Prefix(){
        if (AbstractDungeon.player.chosenClass.equals(THE_GROVEKEEPER) && !AbstractDungeon.player.hasPower(AlignmentPower.POWER_ID)) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,
                        new AlignmentPower(AbstractDungeon.player,AbstractDungeon.player,0,false)));
        }
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

package theDragonkin.patches;


import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.cards.GroveKeeper.AbstractGroveKeeperCard;
import theDragonkin.powers.GroveKeeper.AbstractUpdatingTwoAmountPower;
import theDragonkin.powers.GroveKeeper.AlignmentPower;
import static theDragonkin.characters.TheGroveKeeper.Enums.THE_GROVEKEEPER;

@SpirePatch(clz = AbstractCard.class, method = "applyPowers")
public class GrovekeeperAlignmentAddpatch {
    static int gkcardcounter;

    public static void Prefix(){
        if ((!AbstractDungeon.player.chosenClass.equals(THE_GROVEKEEPER)) && HasGrovekeeperCards() && !AbstractDungeon.player.hasPower(AlignmentPower.POWER_ID)) {
            if (!AbstractGroveKeeperCard.alignmentgiven) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                        new AlignmentPower(AbstractDungeon.player, AbstractDungeon.player, 0, false)));
                AbstractGroveKeeperCard.alignmentgiven = false;
            }
        }
        if ((AbstractDungeon.player.chosenClass.equals(THE_GROVEKEEPER) || HasGrovekeeperCards())) {
            for (AbstractPower p : AbstractDungeon.player.powers) {
                if (p instanceof AbstractUpdatingTwoAmountPower) {
                    ((AbstractUpdatingTwoAmountPower) p).UpdateAmount2();
                }
            }
        }
    }
    public static boolean HasGrovekeeperCards(){
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group){
            if (c instanceof AbstractGroveKeeperCard){
                ++gkcardcounter;
            }
        }
        return gkcardcounter >= 1;
    }
}

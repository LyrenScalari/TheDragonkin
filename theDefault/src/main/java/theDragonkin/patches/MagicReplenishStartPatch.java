package theDragonkin.patches;


import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.campfire.RestOption;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepEffect;
import theDragonkin.cards.Gremory.AbstractMagicGremoryCard;
import theDragonkin.characters.TheGremory;
import theDragonkin.ui.ReplenishMagicEffect;

@SpirePatch(clz = RestOption.class, method = "useOption")
public class MagicReplenishStartPatch {
    public static void Postfix(){

        final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ReplenishMagic");
        CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
            if (card instanceof AbstractMagicGremoryCard && ((AbstractMagicGremoryCard) card).misc != ((AbstractMagicGremoryCard) card).baseMisc) {
                group.addToBottom(card);
            }
        }
        if (group.size() > 0) {
            AbstractDungeon.effectList.add(new ReplenishMagicEffect(TheGremory.BASE_MAGIC_REPLENISH,group));
        }
    }
}

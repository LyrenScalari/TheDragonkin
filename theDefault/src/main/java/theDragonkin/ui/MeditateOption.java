package theDragonkin.ui;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepScreenCoverEffect;
import theDragonkin.cards.Gremory.AbstractMagicGremoryCard;
import theDragonkin.characters.TheGremory;

public class MeditateOption extends AbstractCampfireOption {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("MeditateOption");
    public int healAmt;
    public CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    public MeditateOption() {
        this.label = uiStrings.TEXT[0];
        this.usable = true;

        healAmt = TheGremory.BASE_MAGIC_REPLENISH * 2;

        this.description = uiStrings.TEXT[1] + healAmt;
        if (healAmt > 1){
            this.description += uiStrings.TEXT[3];
        } else {
            this.description += uiStrings.TEXT[2];
        }
        this.updateUsability();
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
            if (card instanceof AbstractMagicGremoryCard && ((AbstractMagicGremoryCard) card).misc != ((AbstractMagicGremoryCard) card).baseMisc) {
                group.addToBottom(card);
            }
        }
    }

    public void updateUsability() {
        if (usable) {
        }

        this.img = ImageMaster.CAMPFIRE_REST_BUTTON;
    }

    public void useOption() {
        CardCrawlGame.sound.play("SLEEP_BLANKET");
        AbstractDungeon.effectList.add(new ReplenishMagicEffect(healAmt,group));

        for(int i = 0; i < 30; ++i) {
            AbstractDungeon.topLevelEffects.add(new CampfireSleepScreenCoverEffect());
        }
    }
}

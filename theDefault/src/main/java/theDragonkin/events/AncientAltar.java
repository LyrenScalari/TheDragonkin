package theDragonkin.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.colorless.Apotheosis;
import com.megacrit.cardcrawl.cards.curses.Doubt;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import theDragonkin.DefaultMod;
import theDragonkin.cards.Dragonkin.Insanity;
import theDragonkin.cards.Dragonkin.MeteorStorm;
import theDragonkin.relics.Dragonkin.SunblessedCharm;

import static theDragonkin.DefaultMod.makeEventPath;

public class AncientAltar extends AbstractImageEvent {


    public static final String ID = DefaultMod.makeID("AncientAltar");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("IdentityCrisisEvent.png");

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;

    public AncientAltar() {
        super(NAME, DESCRIPTIONS[0] + DESCRIPTIONS[1], IMG);


        // The first dialogue options available to us.
        imageEventText.setDialogOption(OPTIONS[0], new MeteorStorm()); // Inspiration - Gain a Random Starting Relic
        imageEventText.setDialogOption(OPTIONS[1], new Doubt(), new SunblessedCharm()); // Acceptance - Gain Apotheosis
        imageEventText.setDialogOption(OPTIONS[2]); // TOUCH THE MIRROR
        imageEventText.setDialogOption(OPTIONS[3]);
    }

    @Override
    protected void buttonEffect(int i) { // This is the event:
        switch (screenNum) {
            case 0: // While you are on screen number 0 (The starting screen)
                switch (i) {
                    case 0: // If you press button the first button (Button at index 0), in this case: Inspiration.
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[4],new Insanity());
                        this.imageEventText.updateDialogOption(1, OPTIONS[5]);
                        screenNum = 1; // Screen set the screen number to 1. Once we exit the switch (i) statement,
                        break; // Onto screen 1 we go.
                    case 1: // If you press button the second button (Button at index 1), in this case: Deinal
                        this.imageEventText.updateBodyText(DESCRIPTIONS[5]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), new SunblessedCharm());
                        AbstractCard c = new Doubt().makeCopy();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 2;
                        break; // Onto screen 2 we go.
                    case 2: // If you press button the third button (Button at index 2), in this case: Acceptance
                        if (CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()).size() > 0) {
                            // If you have cards you can remove - remove a card
                            AbstractDungeon.gridSelectScreen.open(
                                    CardGroup.getGroupWithoutBottledCards(
                                            AbstractDungeon.player.masterDeck.getPurgeableCards()),
                                    1, OPTIONS[6], false, false, false, true);
                        }

                        this.imageEventText.updateBodyText(DESCRIPTIONS[6]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 2;
                        break;
                    case 3: // If you press button the fourth button (Button at index 3), in this case: TOUCH
                        // Other than that, this option doesn't do anything special.
                        openMap();
                        break;
                }
                break;
            case 2: // Welcome to screenNum = 1;
                switch (i) {
                    case 0: // If you press the first (and this should be the only) button,
                        openMap(); // You'll open the map and end the event.
                        break;
                }
                break;
            case 1: // Welcome to screenNum = 1;
                switch (i) {
                    case 0: // If you press button the first button (Button at index 0), in this case: Inspiration.
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        AbstractCard c = new Insanity().makeCopy();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                        AbstractCard c2 = new MeteorStorm().makeCopy();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c2, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 2; // Screen set the screen number to 1. Once we exit the switch (i) statement,

                        break; // Onto screen 1 we go.
                    case 1: // If you press button the second button (Button at index 1), in this case: Deinal
                        this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 2;

                        break; // Onto screen 1 we go.
                }
                break;
        }
    }

    public void update() { // We need the update() when we use grid screens (such as, in this case, the screen for selecting a card to remove)
        super.update(); // Do everything the original update()
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) { // Once the grid screen isn't empty (we selected a card for removal)
            AbstractCard c = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0); // Get the card
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, (float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2))); // Create the card removal effect
            AbstractDungeon.player.masterDeck.removeCard(c); // Remove it from the deck
            AbstractDungeon.gridSelectScreen.selectedCards.clear(); // Or you can .remove(c) instead of clear,
            // if you want to continue using the other selected cards for something
        }

    }

}

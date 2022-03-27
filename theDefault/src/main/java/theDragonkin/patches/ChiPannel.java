package theDragonkin.patches;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import theDragonkin.characters.TheWindWalker;
import theDragonkin.util.EasyInfoDisplayPanel;

public class ChiPannel extends EasyInfoDisplayPanel {

    private static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theDragonkin:ChiPannel");
    public ChiPannel() {
        super(50, 800, 50);
    } // NOTE: X, Y, Width are all multipled by settings.scale on constructor, so use values like this.

    @Override
    public String getTitle() {
        return uiStrings.TEXT[0];
    }

    @Override
    public String getDescription() {
        String s = "";
        if (ChiField.Chi.get(AbstractDungeon.player) > 0 || AbstractDungeon.player.chosenClass.equals(TheWindWalker.Enums.TheWindWalker)){
            s +=  ChiField.Chi.get(AbstractDungeon.player) + " / 5";
        }
        if (s == "") {
            return "NORENDER";
        }
        return s;
    }

    @Override
    public RENDER_TIMING getTiming() {
        return RENDER_TIMING.TIMING_PLAYER_RENDER;
    }
}
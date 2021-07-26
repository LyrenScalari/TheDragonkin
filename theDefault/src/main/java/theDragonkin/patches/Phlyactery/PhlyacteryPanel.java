package theDragonkin.patches.Phlyactery;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import org.lwjgl.Sys;
import theDragonkin.actions.GainBloodPointsAction;
import theDragonkin.cards.Deathspeaker.BloodGlobe;
import theDragonkin.characters.TheDeathspeaker;
import theDragonkin.util.EasyInfoDisplayPanel;

public class PhlyacteryPanel extends EasyInfoDisplayPanel {

    private static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theDragonkin:PhlyacteryPanel");
    private static boolean Extracted = false;
    public PhlyacteryPanel() {
        super(50, 600, 100);
    } // NOTE: X, Y, Width are all multipled by settings.scale on constructor, so use values like this.

    @Override
    public String getTitle() {
        return uiStrings.TEXT[0];
    }

    @Override
    public String getDescription() {
        String s = "";
        if (PhlyacteryField.BloodPoints.get(AbstractDungeon.player) > 0 || AbstractDungeon.player.chosenClass.equals(TheDeathspeaker.Enums.THE_DEATHSPEAKER)){
            s +=  PhlyacteryField.BloodPoints.get(AbstractDungeon.player) + " / " + AbstractDungeon.player.maxHealth;
        }
        if (s == "") {
            return "NORENDER";
        }
        if (AbstractDungeon.player.hb != null){
            if (AbstractDungeon.player.hb.clicked && !Extracted) {
                if (PhlyacteryField.BloodPoints.get(AbstractDungeon.player) > 4) {
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new BloodGlobe((int) Math.ceil(PhlyacteryField.BloodPoints.get(AbstractDungeon.player) * 0.30f))));
                    PhlyacteryField.BloodPoints.set(AbstractDungeon.player, PhlyacteryField.BloodPoints.get(AbstractDungeon.player) - (int) Math.ceil(PhlyacteryField.BloodPoints.get(AbstractDungeon.player) * 0.30f));
                    AbstractDungeon.actionManager.addToBottom(new GainBloodPointsAction(AbstractDungeon.player,AbstractDungeon.player, PhlyacteryField.BloodPoints.get(AbstractDungeon.player) - (int) Math.ceil(PhlyacteryField.BloodPoints.get(AbstractDungeon.player) * 0.30f)));
                    Extracted = true;
                }
            }
        }
        return s;
    }

    @Override
    public RENDER_TIMING getTiming() {
        return RENDER_TIMING.TIMING_PLAYER_RENDER;
    }
}

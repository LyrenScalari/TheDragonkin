package theDragonkin.util;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.powers.Dragonkin.DragonBreaths.AbstractDragonBreathPower;

public class DragonbreathPanel extends EasyInfoDisplayPanel {

    private static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("dragonkin:DragonbreathDisplay");

    public DragonbreathPanel() {
        super(600, 900, 200);
    } // NOTE: X, Y, Width are all multipled by settings.scale on constructor, so use values like this.

    @Override
    public String getTitle() {
        int remainingDelay = 3;
        for (AbstractPower p : AbstractDungeon.player.powers){
            if (p instanceof AbstractDragonBreathPower){
                if (((AbstractDragonBreathPower) p).amount2 < remainingDelay){
                    remainingDelay = ((AbstractDragonBreathPower) p).amount2;
                }
            }
        }
        return uiStrings.TEXT[0] + remainingDelay;
    }

    @Override
    public String getDescription() {
        String s = "";
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof AbstractDragonBreathPower) {
                CardStrings strings = CardCrawlGame.languagePack.getCardStrings(AbstractDragonBreathPower.sourcecard.cardID);
                s += strings.NAME + "- " + ((AbstractDragonBreathPower) p).amount2;
            }
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

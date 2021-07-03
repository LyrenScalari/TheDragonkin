package theDragonkin.relics.Dragonkin;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import theDragonkin.DragonkinMod;
import theDragonkin.cards.Dragonkin.AbstractHolyCard;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DragonkinMod.makeRelicOutlinePath;
import static theDragonkin.DragonkinMod.makeRelicPath;

public class GarnetScale extends CustomRelic{ // You must implement things you want to use from StSlib
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     * StSLib for Clickable Relics
     *
     * Usable once per turn. Right click: Evoke your rightmost orb.
     */

    // ID, images, text.
    public static final String ID = DragonkinMod.makeID("GarnetScale");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("GarnetScale.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("GarnetScale.png"));
    // You can also have a relic be only usable once per combat. Check out Hubris for more examples, including other StSlib things.

    private boolean isPlayerTurn = false; // We should make sure the relic is only activateable during our turn, not the enemies'.
    private  boolean used = false;
    public int Statuscount = 0;
    public int burncount = 0;

    public GarnetScale() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.CLINK);
        tips.clear();
        tips.add(new PowerTip(name, description));
    }

    @Override
    public void atPreBattle() {
    }

    @Override
    public void atTurnStartPostDraw() {
    }
    @Override
    public void onUseCard(final AbstractCard c , final UseCardAction ca){
        if (c instanceof AbstractHolyCard && !used){
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player,2));
            used = true;
        }
    }
    @Override
    public void onCardDraw(AbstractCard card) {
    }

@Override
public void atTurnStart(){
}


    @Override
    public void onPlayerEndTurn() {
        Statuscount = 0;
        used = false;
        burncount = 0;
    }


    @Override
    public void onVictory() {
        Statuscount = 0;
        used = false;
        stopPulse(); // Don't keep pulsing past the victory screen/outside of combat.
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}

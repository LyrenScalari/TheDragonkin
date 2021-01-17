package theDragonkin.relics.Dragonkin;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theDragonkin.DefaultMod;
import theDragonkin.cards.Dragonkin.AbstractHolyCard;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DefaultMod.makeRelicOutlinePath;
import static theDragonkin.DefaultMod.makeRelicPath;

public class GarnetScale extends CustomRelic{ // You must implement things you want to use from StSlib
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     * StSLib for Clickable Relics
     *
     * Usable once per turn. Right click: Evoke your rightmost orb.
     */

    // ID, images, text.
    public static final String ID = DefaultMod.makeID("GarnetScale");

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
        if (c instanceof AbstractHolyCard){
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player,4));
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
        burncount = 0;
    }


    @Override
    public void onVictory() {
        Statuscount = 0;
        stopPulse(); // Don't keep pulsing past the victory screen/outside of combat.
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}

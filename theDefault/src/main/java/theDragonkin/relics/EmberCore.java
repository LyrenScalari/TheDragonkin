package theDragonkin.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import theDragonkin.DefaultMod;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DefaultMod.makeRelicOutlinePath;
import static theDragonkin.DefaultMod.makeRelicPath;

public class EmberCore extends CustomRelic { // You must implement things you want to use from StSlib
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     * StSLib for Clickable Relics
     *
     * Usable once per turn. Right click: Evoke your rightmost orb.
     */

    // ID, images, text.
    public static final String ID = DefaultMod.makeID("EmberCore");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("EmberCore.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("EmberCore.png"));
    // You can also have a relic be only usable once per combat. Check out Hubris for more examples, including other StSlib things.

    private boolean isPlayerTurn = false; // We should make sure the relic is only activateable during our turn, not the enemies'.
    private  boolean used = false;

    public EmberCore() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.CLINK);
        tips.clear();
        tips.add(new PowerTip(name, description));
    }

    @Override
    public void atPreBattle() {
    }

    @Override
    public void onCardDraw(AbstractCard card){
        if (card.type == AbstractCard.CardType.STATUS && !used){
            addToBot(new DrawCardAction(1));
            addToBot(new AddTemporaryHPAction(AbstractDungeon.player,AbstractDungeon.player,6));
            used = true;
        }
    }
    @Override
    public void atTurnStart(){
        used = false;
    }

    @Override
    public void onPlayerEndTurn() {
        used = false;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}


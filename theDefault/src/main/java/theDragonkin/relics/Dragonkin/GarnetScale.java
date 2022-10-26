package theDragonkin.relics.Dragonkin;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnReceivePowerRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.DragonkinMod;
import theDragonkin.cards.Dragonkin.AbstractHolyCard;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DragonkinMod.makeRelicOutlinePath;
import static theDragonkin.DragonkinMod.makeRelicPath;

public class GarnetScale extends CustomRelic implements OnReceivePowerRelic { // You must implement things you want to use from StSlib
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
        counter = 3;
    }

    @Override
    public void atPreBattle() {
        counter =3;
    }

    @Override
    public void atTurnStartPostDraw() {
    }
    @Override
    public void onCardDraw(AbstractCard card) {
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

    @Override
    public boolean onReceivePower(AbstractPower abstractPower, AbstractCreature abstractCreature) {
        if (!used && abstractPower.type == AbstractPower.PowerType.DEBUFF){
            addToBot(new GainBlockAction(AbstractDungeon.player,counter));
            used = true;
        }
        return true;
    }
}

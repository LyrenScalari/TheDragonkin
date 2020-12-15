package theDragonkin.relics.Dragonkin;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theDragonkin.DefaultMod;
import theDragonkin.powers.Dragonkin.DivineConvictionpower;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DefaultMod.makeRelicOutlinePath;
import static theDragonkin.DefaultMod.makeRelicPath;

public class UthersMaul extends CustomRelic { // You must implement things you want to use from StSlib
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     * StSLib for Clickable Relics
     *
     * Usable once per turn. Right click: Evoke your rightmost orb.
     */

    // ID, images, text.
    public static final String ID = DefaultMod.makeID("UthersMaul");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));
    // You can also have a relic be only usable once per combat. Check out Hubris for more examples, including other StSlib things.

    private boolean isPlayerTurn = false; // We should make sure the relic is only activateable during our turn, not the enemies'.
    private  boolean used = false;

    public UthersMaul() {
        super(ID, IMG, OUTLINE, AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.HEAVY);
        tips.clear();
        tips.add(new PowerTip(name, description));
    }

    @Override
    public void atPreBattle() {
    }

    @Override
    public void onCardDraw(AbstractCard card){
    }

    @Override
    public void onUseCard(final AbstractCard c , final UseCardAction ca){
    }

    @Override
    public void atTurnStart(){
        used = false;
    }

    @Override
    public void onPlayerEndTurn() {
        used = false;
    }

    @Override
    public void onMonsterDeath(AbstractMonster m) {
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new DivineConvictionpower(AbstractDungeon.player,AbstractDungeon.player,1),1));
        addToBot(new GainBlockAction(AbstractDungeon.player,5));
    }
    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}

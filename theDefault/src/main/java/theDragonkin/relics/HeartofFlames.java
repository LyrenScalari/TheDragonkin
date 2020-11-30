package theDragonkin.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theDragonkin.DefaultMod;
import theDragonkin.cards.Gremory.AbstractMagicGremoryCard;
import theDragonkin.powers.*;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DefaultMod.makeRelicOutlinePath;
import static theDragonkin.DefaultMod.makeRelicPath;

public class HeartofFlames extends CustomRelic { // You must implement things you want to use from StSlib
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     * StSLib for Clickable Relics
     *
     * Usable once per turn. Right click: Evoke your rightmost orb.
     */

    // ID, images, text.
    public static final String ID = DefaultMod.makeID("CrestofFlames");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Crest_of_Flames.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Crest_of_Flames.png"));
    // You can also have a relic be only usable once per combat. Check out Hubris for more examples, including other StSlib things.

    private boolean isPlayerTurn = false; // We should make sure the relic is only activateable during our turn, not the enemies'.
    private boolean used = false;
    private static boolean physical = false;
    private static boolean fire = false;
    private static boolean ice = false;
    private static boolean wind = false;
    private static boolean thunder = false;
    private static boolean dark = false;
    private static boolean light = false;

    public HeartofFlames() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, AbstractRelic.LandingSound.HEAVY);
        tips.clear();
        tips.add(new PowerTip(name, description));
    }

    @Override
    public void atPreBattle() {
    }

    @Override
    public void onCardDraw(AbstractCard card) {
    }


    @Override
    public void onUseCard(final AbstractCard c, final UseCardAction ca) {
        if (!(c instanceof AbstractMagicGremoryCard) && !physical){
            addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player,AbstractDungeon.player,StrengthPower.POWER_ID));
            addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player,AbstractDungeon.player,DexterityPower.POWER_ID));
            physical = true;
        }
    }
    @Override
    public void atBattleStart() {
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new StrengthPower(AbstractDungeon.player,3),3));
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new DexterityPower(AbstractDungeon.player,3),3));
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new ChargedUpCards(AbstractDungeon.player,3,1),3));
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new DeepFrostCards(AbstractDungeon.player,3,1),3));
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new KindlingCards(AbstractDungeon.player,3,1),3));
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new GaleforceCards(AbstractDungeon.player,3,1),3));
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new LuminanceCards(AbstractDungeon.player,3,1),3));
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new LoptyrianShadowCards(AbstractDungeon.player,3,1),3));
    }

    @Override
    public void atTurnStart() {
        used = false;
    }

    public void onVictory() {
        physical = false;
        fire = false;
        ice = false;
        wind = false;
        thunder = false;
        dark = false;
        light = false;
    }

    @Override
    public void wasHPLost(int damageAmount) {
    }

    public void onEquip() {
    }

    public void onUnequip() {
    }

    @Override
    public void onPlayerEndTurn() {
        used = false;
    }

    @Override
    public void onMonsterDeath(AbstractMonster m) {
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}

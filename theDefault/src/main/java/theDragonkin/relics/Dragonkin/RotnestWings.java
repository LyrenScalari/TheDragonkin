package theDragonkin.relics.Dragonkin;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.powers.Dragonkin.DragonBreaths.AbstractDragonBreathPower;
import theDragonkin.powers.Dragonkin.NecroticAura;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DefaultMod.makeRelicOutlinePath;
import static theDragonkin.DefaultMod.makeRelicPath;

public class RotnestWings extends CustomRelic { // You must implement things you want to use from StSlib
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     * StSLib for Clickable Relics
     *
     * Usable once per turn. Right click: Evoke your rightmost orb.
     */

    // ID, images, text.
    public static final String ID = DefaultMod.makeID("RotnestWings");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));
    // You can also have a relic be only usable once per combat. Check out Hubris for more examples, including other StSlib things.

    private boolean isPlayerTurn = false; // We should make sure the relic is only activateable during our turn, not the enemies'.
    private  boolean used = false;

    public RotnestWings() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, AbstractRelic.LandingSound.HEAVY);
        tips.clear();
        tips.add(new PowerTip(name, description));
    }

    @Override
    public void onUseCard(final AbstractCard c , final UseCardAction ca){
        if (c.hasTag(CustomTags.Dragon_Breath) && !used){
            addToBot(new DrawCardAction(AbstractDungeon.player,1));
            used = true;
            for (AbstractCard card : AbstractDungeon.player.hand.group){
                if (card.hasTag(CustomTags.Dragon_Breath) && card.costForTurn == 0){
                    card.costForTurn = card.cost;
                }
            }
        } else if (!used) {
            for (AbstractCard card : AbstractDungeon.player.hand.group){
                if (card.hasTag(CustomTags.Dragon_Breath) && card.costForTurn != 0){
                    card.costForTurn = 0;
                }
            }
        }
    }
    @Override
    public void atTurnStartPostDraw(){
        for (AbstractCard c : AbstractDungeon.player.hand.group){
            if (c.hasTag(CustomTags.Dragon_Breath) && c.costForTurn != 0){
                c.costForTurn = 0;
            }
        }
        used = false;
    }
    @Override
    public void onEquip() {
        ++AbstractDragonBreathPower.BreathDelay;
    }

    @Override
    public void onUnequip() {
        --AbstractDragonBreathPower.BreathDelay;
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

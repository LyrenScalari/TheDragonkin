package theDragonkin.relics.Dragonkin;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import theDragonkin.DragonkinMod;
import theDragonkin.cards.Dragonkin.AbstractPrimalCard;
import theDragonkin.powers.Dragonkin.FuryPower;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DragonkinMod.makeRelicOutlinePath;
import static theDragonkin.DragonkinMod.makeRelicPath;

public class ObsidianScale extends CustomRelic { // You must implement things you want to use from StSlib
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     * StSLib for Clickable Relics
     *
     * Usable once per turn. Right click: Evoke your rightmost orb.
     */

    // ID, images, text.
    public static final String ID = DragonkinMod.makeID("ObsidianScale");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("ObsidianScale.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("ObsidianScale.png"));
    public ObsidianScale() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);
        counter = 2;
    }
    @Override
    public boolean canSpawn() {return AbstractDungeon.player.hasRelic(GarnetScale.ID);}
    @Override
    public String getUpdatedDescription() {
        // Colorize the starter relic's name
        String name = new GarnetScale().name;
        StringBuilder sb = new StringBuilder();
        for (String word : name.split(" ")) {
            sb.append("[#").append(DragonkinMod.DEFAULT_GRAY.cpy().toString()).append("]").append(word).append("[] ");
        }
        sb.setLength(sb.length()-1);
        sb.append("[#").append(DragonkinMod.DEFAULT_GRAY.cpy().toString()).append("]");

        return DESCRIPTIONS[0] + sb.toString() + DESCRIPTIONS[1];
    }
    public void onManualDiscard() {
        if (AbstractDungeon.player.discardPile.getTopCard() instanceof AbstractPrimalCard ||AbstractDungeon.player.discardPile.getTopCard().type == AbstractCard.CardType.STATUS){
            this.flash();
            addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player, new VigorPower(AbstractDungeon.player,counter)));
            addToBot(new GainBlockAction(AbstractDungeon.player,counter));
        }
    }
    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(GarnetScale.ID)) {
            for (int i=0; i<AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(GarnetScale.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }
    // Description

}

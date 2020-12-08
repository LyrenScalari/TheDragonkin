package theDragonkin.relics.Dragonkin;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theDragonkin.DefaultMod;
import theDragonkin.relics.Dragonkin.GarnetScale;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DefaultMod.makeRelicOutlinePath;
import static theDragonkin.DefaultMod.makeRelicPath;

public class ObsidianScale extends CustomRelic { // You must implement things you want to use from StSlib
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     * StSLib for Clickable Relics
     *
     * Usable once per turn. Right click: Evoke your rightmost orb.
     */

    // ID, images, text.
    public static final String ID = DefaultMod.makeID("ObsidianScale");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("ObsidianScale.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("ObsidianScale.png"));

    public ObsidianScale() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);
    }
    @Override
    public boolean canSpawn() {return AbstractDungeon.player.hasRelic(GarnetScale.ID);}
    @Override
    public String getUpdatedDescription() {
        // Colorize the starter relic's name
        String name = new GarnetScale().name;
        StringBuilder sb = new StringBuilder();
        for (String word : name.split(" ")) {
            sb.append("[#").append(DefaultMod.DEFAULT_GRAY.toString()).append("]").append(word).append("[] ");
        }
        sb.setLength(sb.length()-1);
        sb.append("[#").append(DefaultMod.DEFAULT_GRAY.toString()).append("]");

        return DESCRIPTIONS[0] + sb.toString() + DESCRIPTIONS[1];
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


    @Override
    public void atPreBattle() {

    }
    @Override
    public void atTurnStart(){
        DefaultMod.ObsidianMight = false;
    }

    @Override
    public void onPlayerEndTurn() {
        DefaultMod.ObsidianMight = false;
    }


    @Override
    public void onVictory() {
    }

    // Description

}

package theDragonkin.relics.WindWalker;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.Lightning;
import theDragonkin.DragonkinMod;
import theDragonkin.cards.WindWalker.StanceSelection.Conduit;
import theDragonkin.cards.WindWalker.StanceSelection.Tempest;
import theDragonkin.cards.WindWalker.StanceSelection.Whirlwind;
import theDragonkin.cards.WindWalker.StanceSelection.Zephyr;
import theDragonkin.relics.Dragonkin.GarnetScale;
import theDragonkin.util.TextureLoader;

import java.util.ArrayList;

import static theDragonkin.DragonkinMod.makeRelicOutlinePath;
import static theDragonkin.DragonkinMod.makeRelicPath;

public class MastersIdol extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.
    public static final String ID = DragonkinMod.makeID("MastersIdol");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));
    private static ArrayList<AbstractCard> stances = new ArrayList<>();
    public  MastersIdol() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    // Flash at the start of Battle
    public void atPreBattle(){
        this.flash();
        stances.add(new Tempest());
        stances.add(new Whirlwind());
        stances.add(new Conduit());
        stances.add(new Zephyr());
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new ChooseOneAction(stances));
    }
    @Override
    public boolean canSpawn() {return AbstractDungeon.player.hasRelic(SerpentIdol.ID);}
    @Override
    public String getUpdatedDescription() {
        // Colorize the starter relic's name
        String name = new SerpentIdol().name;
        StringBuilder sb = new StringBuilder();
        for (String word : name.split(" ")) {
            sb.append("[#").append(DragonkinMod.WindWalker_Jade.toString()).append("]").append(word).append("[] ");
        }
        sb.setLength(sb.length()-1);
        sb.append("[#").append(DragonkinMod.WindWalker_Jade.toString()).append("]");

        return DESCRIPTIONS[0] + sb.toString() + DESCRIPTIONS[1];
    }
    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(SerpentIdol.ID)) {
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
}
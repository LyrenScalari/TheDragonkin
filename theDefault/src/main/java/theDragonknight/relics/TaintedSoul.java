package theDragonknight.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theDragonknight.DragonknightMod;
import theDragonknight.orbs.DragonShouts.FlameMark;
import theDragonknight.util.AbstractDragonMark;
import theDragonknight.util.TextureLoader;

import static theDragonknight.DragonknightMod.makeRelicOutlinePath;
import static theDragonknight.DragonknightMod.makeRelicPath;

public class TaintedSoul extends CustomRelic {

    public static final String ID = DragonknightMod.makeID("TaintedSoul");
    private static AbstractDragonMark type = new FlameMark();
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public TaintedSoul() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    // Flash at the start of Battle.
    @Override
    public void atBattleStartPreDraw() {
        flash();

    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
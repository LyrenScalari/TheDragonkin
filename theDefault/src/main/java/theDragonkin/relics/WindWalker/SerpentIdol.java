package theDragonkin.relics.WindWalker;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theDragonkin.DragonkinMod;
import theDragonkin.Stances.Whirlwind;
import theDragonkin.powers.WindWalker.MasteryPower;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DragonkinMod.makeRelicOutlinePath;
import static theDragonkin.DragonkinMod.makeRelicPath;

public class SerpentIdol extends CustomRelic {

    public static final String ID = DragonkinMod.makeID("SerpentIdol");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public SerpentIdol() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
        counter = 2;
    }

    // Flash at the start of Battle.
    @Override
    public void atBattleStart() {
        this.flash();
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new MasteryPower(AbstractDungeon.player,AbstractDungeon.player,counter)));
    }
    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
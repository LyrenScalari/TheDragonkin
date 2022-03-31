package theDragonknight.relics;

import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import theDragonknight.DragonknightMod;
import theDragonknight.orbs.DragonShouts.FlameMark;
import theDragonknight.orbs.DragonShouts.StormMark;
import theDragonknight.util.AbstractDragonMark;
import theDragonknight.util.AbstractNotOrb;
import theDragonknight.util.TextureLoader;

import static theDragonknight.DragonknightMod.makeRelicOutlinePath;
import static theDragonknight.DragonknightMod.makeRelicPath;

public class TaintedSoul extends CustomRelic {

    public static final String ID = DragonknightMod.makeID("TaintedSoul");
    private static AbstractDragonMark type = new FlameMark(AbstractDungeon.player);
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public TaintedSoul() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
        type = new FlameMark(AbstractDungeon.player);
    }

    // Flash at the start of Battle.
    @Override
    public void atBattleStart() {
        flash();
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                DragonknightMod.Seals.add(type.MakeCopy());
                isDone = true;
            }
        });
        switch (type.Sealstrings.NAME){
            case "Flame":{
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new VigorPower(AbstractDungeon.player,type.PlayerAmount)));
            }
        }
    }
    public void onRest() {
    }
    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + "#y"+ type.Sealstrings.NAME+ " NL " + DESCRIPTIONS[1];
    }

}
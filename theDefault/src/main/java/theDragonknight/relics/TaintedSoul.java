package theDragonknight.relics;

import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.rewards.RewardItem;
import theDragonknight.DragonknightMod;
import theDragonknight.cards.Dragonknight.Token.*;
import theDragonknight.orbs.DragonShouts.FlameMark;
import theDragonknight.orbs.DragonShouts.StormMark;
import theDragonknight.util.AbstractDragonMark;
import theDragonknight.util.AbstractNotOrb;
import theDragonknight.util.TextureLoader;

import java.util.ArrayList;

import static theDragonknight.DragonknightMod.makeRelicOutlinePath;
import static theDragonknight.DragonknightMod.makeRelicPath;

public class TaintedSoul extends CustomRelic {

    public static final String ID = DragonknightMod.makeID("TaintedSoul");
    public static AbstractDragonMark type = new FlameMark(AbstractDungeon.player);
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public TaintedSoul() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
        type = new FlameMark(AbstractDungeon.player);
        this.getUpdatedDescription();
    }

    // Flash at the start of Battle.
    @Override
    public void atBattleStart() {
        flash();
        DragonknightMod.Seals.add(type.MakeCopy());
        getUpdatedDescription();
    }
    public void onRest() {
        ArrayList<AbstractCard> choices = new ArrayList<>();
        switch (type.Sealstrings.NAME){
            case "Flaming Scales":{
                choices.add(new NoSwap());
                choices.add(new StaticSwap());
                choices.add(new ToxicSwap());
                choices.add(new MoltenSwap());
                break;
            }
            case "Static Scales":{
                choices.add(new FlameSwap());
                choices.add(new NoSwap());
                choices.add(new ToxicSwap());
                choices.add(new MoltenSwap());
                break;
            }
            case "Toxic Scales":{
                choices.add(new FlameSwap());
                choices.add(new StaticSwap());
                choices.add(new NoSwap());
                choices.add(new MoltenSwap());
                break;
            }
            case "Molten Scales":{
                choices.add(new FlameSwap());
                choices.add(new StaticSwap());
                choices.add(new ToxicSwap());
                choices.add(new NoSwap());
                break;
            }
        }
        AbstractDungeon.cardRewardScreen.chooseOneOpen(choices);
    }
    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + "#y"+ type.Sealstrings.NAME+ " NL " + DESCRIPTIONS[1];
    }

}
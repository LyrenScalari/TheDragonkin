package theDragonkin.powers.Dragonkin.DragonBreaths;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.cards.Dragonkin.DivineWind;
import theDragonkin.powers.Dragonkin.DeepBreathPower;
import theDragonkin.powers.Dragonkin.FuryPower;
import theDragonkin.powers.Dragonkin.MagnussCoronaPower;
import theDragonkin.powers.Dragonkin.StormFrontPower;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DefaultMod.makePowerPath;

public abstract class AbstractDragonBreathPower extends TwoAmountPower implements InvisiblePower , NonStackablePower {
    public int BreathDelay = 1;
    public int BreathCount = 0;
    public static boolean toExhale = false;
    public static boolean Exhaled = false;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public AbstractDragonBreathPower(){
        amount2 = BreathDelay;
        type = PowerType.BUFF;
        toExhale = false;
        this.owner = AbstractDungeon.player;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
    }
    @Override
    public void atEndOfTurn(final boolean isplayer) {
        for (AbstractPower p : AbstractDungeon.player.powers){
            if (p instanceof AbstractDragonBreathPower && (!AbstractDungeon.player.hasPower(DeepBreathPower.POWER_ID))){
                ((TwoAmountPower)p).amount2 -= 1;
                if (((TwoAmountPower)p).amount2 <= 0){
                    toExhale = true;
                }
            }
        }
        if (toExhale && !Exhaled){
            for (AbstractPower p : AbstractDungeon.player.powers){
                if (p instanceof AbstractDragonBreathPower){
                    ((AbstractDragonBreathPower) p).onBreath();
                    BreathCount += 1;
                    if (AbstractDungeon.player.hasPower(MagnussCoronaPower.POWER_ID)){
                        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new FuryPower(AbstractDungeon.player,AbstractDungeon.player,
                                AbstractDungeon.player.getPower(MagnussCoronaPower.POWER_ID).amount), AbstractDungeon.player.getPower(MagnussCoronaPower.POWER_ID).amount));
                    }
                    addToBot(new AbstractGameAction() {
                        public void update() {
                            owner.powers.remove(p);
                            isDone = true;
                        }
                    });
                }
            }
            if (AbstractDungeon.player.hasPower(StormFrontPower.POWER_ID)){
                TwoAmountPower Stormfront = (TwoAmountPower) AbstractDungeon.player.getPower(StormFrontPower.POWER_ID);
                if (BreathCount >= Stormfront.amount2){
                    addToBot(new MakeTempCardInDrawPileAction(new DivineWind(),Stormfront.amount,true,false,false));
                }
            }
            Exhaled = true;
        }
    }
    @Override
    public void onInitialApplication(){
        if (toExhale){
            toExhale = false;
        }
        if (Exhaled){
            Exhaled = false;
        }
    }
    @Override
    public void atStartOfTurn() {
        BreathCount = 0;
    }

    public void onBreath(){
    }
}

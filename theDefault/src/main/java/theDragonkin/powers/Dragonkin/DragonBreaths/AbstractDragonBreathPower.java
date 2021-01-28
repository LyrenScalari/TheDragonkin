package theDragonkin.powers.Dragonkin.DragonBreaths;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;
import theDragonkin.cards.Dragonkin.DeepBreath;
import theDragonkin.cards.Dragonkin.DivineWind;
import theDragonkin.powers.CustomBoss.CurseofTonges;
import theDragonkin.powers.Dragonkin.DeepBreathPower;
import theDragonkin.powers.Dragonkin.FuryPower;
import theDragonkin.powers.Dragonkin.MagnussCoronaPower;
import theDragonkin.powers.Dragonkin.StormFrontPower;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DefaultMod.makePowerPath;

public abstract class AbstractDragonBreathPower extends TwoAmountPower implements InvisiblePower , NonStackablePower {
    public static int BreathDelay = 1;
    public static int BreathCount = 0;
                        // amount1, damage effects
    public int amount3; // Defense effects
    public int amount4; // Debuff effects
    public int amount5; // Buff effects
    public static boolean toExhale = false;
    public static boolean Exhaled = false;
    public AbstractCard sourcecard;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public AbstractDragonBreathPower(){
        BreathCount += 1;
        amount2 = BreathDelay;
        type = PowerType.BUFF;
        toExhale = false;
        if (AbstractDungeon.player.hasPower(DeepBreathPower.POWER_ID)){
            amount2 += AbstractDungeon.player.getPower(DeepBreathPower.POWER_ID).amount;
        }
        this.owner = AbstractDungeon.player;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
    }
    @Override
    public void atEndOfTurn(final boolean isplayer) {
        this.amount2 -= 1;
        if  (amount2 <= 0){
            toExhale = true;
        }
        if (toExhale && !Exhaled){
            addToBot(new VFXAction(new WhirlwindEffect()));
            for (AbstractPower p : AbstractDungeon.player.powers){
                if (p instanceof AbstractDragonBreathPower){
                    ((AbstractDragonBreathPower) p).onBreath();
                    System.out.println(BreathCount);
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
            BreathCount = 0;
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
            BreathCount = 1;
        }
    }
    @Override
    public void onVictory() {
        BreathCount = 0;
    }
    @Override
    public void atStartOfTurn() {
    }

    public void onBreath(){
    }
}

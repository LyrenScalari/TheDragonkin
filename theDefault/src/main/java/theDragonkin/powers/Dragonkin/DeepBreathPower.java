package theDragonkin.powers.Dragonkin;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.DefaultMod;
import theDragonkin.powers.Dragonkin.DragonBreaths.AbstractDragonBreathPower;
import theDragonkin.powers.GroveKeeper.NatrualSpikesPower;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DefaultMod.makePowerPath;

public class DeepBreathPower extends AbstractPower implements CloneablePowerInterface,OnReceivePowerPower {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("DeepBreath");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public DeepBreathPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.loadRegion("blur");

        updateDescription();
    }
    @Override
    public void onInitialApplication(){
        for (AbstractPower p : AbstractDungeon.player.powers){
            if (p instanceof AbstractDragonBreathPower){
                ((TwoAmountPower)p).amount2 += 1;
            }
        }
    }
    @Override
    public boolean onReceivePower(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        if (abstractPower.ID.equals(this.ID)){
            for (AbstractPower p : AbstractDungeon.player.powers){
                if (p instanceof AbstractDragonBreathPower){
                    ((AbstractDragonBreathPower) p).amount2 += 1;
                }
            }
        }
        return true;
    }
    @Override
    public void atEndOfTurn(final boolean isplayer) {
        addToBot(new ReducePowerAction(owner,owner,this,1));
    }
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
    @Override
    public AbstractPower makeCopy() {
        return new AcidMarkPower(owner, source, amount);
    }
}
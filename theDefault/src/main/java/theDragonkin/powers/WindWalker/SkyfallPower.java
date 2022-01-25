package theDragonkin.powers.WindWalker;

import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.mod.stslib.actions.defect.TriggerPassiveAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.DragonkinMod;
import theDragonkin.Stances.Tempest;

public class SkyfallPower extends AbstractPower implements CloneablePowerInterface, GainChiPower {
    public AbstractCreature source;

    public static final String POWER_ID = DragonkinMod.makeID("SkyfallPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.

    public SkyfallPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;
        this.loadRegion("storm");
        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.

        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = powerStrings.DESCRIPTIONS[0];
        if (this.amount < 2){
            description += powerStrings.DESCRIPTIONS[1];
        } else description += powerStrings.DESCRIPTIONS[2];

    }

    @Override
    public AbstractPower makeCopy() {
        return new SkyfallPower(owner, source, amount);
    }

    @Override
    public int onGainChi(int Chigain) {
        System.out.println("Chi Gain power " + this.name);
        for (int i =1 ; i < amount ; i++){
            addToBot(new TriggerPassiveAction(0,1));
        }
        return Chigain;
    }
}
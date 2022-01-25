package theDragonkin.powers.WindWalker;

import IconsAddon.damageModifiers.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public abstract class AbstractChakraPower extends AbstractPower implements OnReceivePowerPower {
    public AbstractDamageModifier GetChakraType(){
        return null;
    }
    @Override
    public boolean onReceivePower(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        if (abstractPower instanceof AbstractChakraPower ){
            for (AbstractPower p : AbstractDungeon.player.powers){
                if (p instanceof AbstractChakraPower && !p.ID.equals(abstractPower.ID)){
                    addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player,AbstractDungeon.player,p));
                }
            }
        }
        return true;
    }
}

package theDragonkin.DamageModifiers.Icons;

import basemod.BaseMod;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import theDragonkin.DamageModifiers.DivineDamage;

public class PrimalDamage  extends AbstractDamageModifier {
    boolean inherent;
    public PrimalDamage(){
        inherent = true;
        this.automaticBindingForCards = false;
    }
    public boolean isInherent() {
        return inherent;
    }
    public String getCardDescriptor() {
        return BaseMod.getKeywordTitle("thedragonkin:Primal");
    }
    @Override
    public AbstractDamageModifier makeCopy() {
        return new PrimalDamage();
    }
    public int priority() {
        return 1;
    }
}

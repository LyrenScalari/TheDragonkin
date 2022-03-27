package theDragonkin.DamageModifiers;


import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import theDragonkin.powers.WindWalker.SpiritWound;

import java.util.ArrayList;

public class SpiritDamage extends AbstractDamageModifier {
    public boolean ignoresBlock(AbstractCreature abstractCreature) {
        return true;
    }
    boolean inherent;
    public SpiritDamage(boolean isinherent){
        inherent = isinherent;
    }
    public boolean ignoresTempHP(AbstractCreature target) {
        return true;
    }
    public boolean isInherent() {
        return inherent;
    }
    public ArrayList<TooltipInfo> getCustomTooltips() {
        ArrayList<TooltipInfo> tips = new ArrayList<>();
        tips.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Spirit"), BaseMod.getKeywordDescription("thedragonkin:Spirit")));
        tips.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Spirit_Wound"), BaseMod.getKeywordDescription("thedragonkin:Spirit_Wound")));
        return tips;
    }
    public int onAttackToChangeDamage(DamageInfo info, int damageAmount, AbstractCreature target) {
        addToBot(new ApplyPowerAction(target,info.owner,new SpiritWound(target,info.owner,damageAmount)));
        return 0;
    }
    @Override
    public AbstractDamageModifier makeCopy() {
        return new SpiritDamage(inherent);
    }
    public String getCardDescriptor() {
        return BaseMod.getKeywordTitle("thedragonkin:Spirit_Damage");
    }
    public int priority() {
        return 4;
    }
}

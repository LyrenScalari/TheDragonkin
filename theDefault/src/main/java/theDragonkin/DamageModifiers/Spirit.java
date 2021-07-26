package theDragonkin.DamageModifiers;

import IconsAddon.damageModifiers.AbstractDamageModifier;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import theDragonkin.powers.WindWalker.SpiritWound;

import java.util.ArrayList;

public class Spirit extends AbstractDamageModifier {
    public boolean ignoresBlock() {
        return true;
    }
    public TooltipInfo getCustomTooltip() {
        return new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Spirit"),BaseMod.getKeywordDescription("thedragonkin:Spirit"));
    }
    public int onAttackToChangeDamage(DamageInfo info, int damageAmount, AbstractCreature target) {
        addToBot(new ApplyPowerAction(target,info.owner,new SpiritWound(target,info.owner,damageAmount)));
        return 0;
    }
}

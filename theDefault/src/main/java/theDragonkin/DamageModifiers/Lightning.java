package theDragonkin.DamageModifiers;

import IconsAddon.damageModifiers.AbstractDamageModifier;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import theDragonkin.powers.WindWalker.Static;

public class Lightning extends AbstractDamageModifier {
    public TooltipInfo getCustomTooltip() {
        return new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Lightning"), BaseMod.getKeywordDescription("thedragonkin:Lightning"));
    }
    public void onDamageModifiedByBlock(DamageInfo info, int unblockedAmount, int blockedAmount, AbstractCreature target) {
        addToBot(new ApplyPowerAction(target,info.owner,new Static(target,info.owner,unblockedAmount/2)));
    }
}
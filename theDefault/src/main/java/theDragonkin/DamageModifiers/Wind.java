package theDragonkin.DamageModifiers;

import IconsAddon.damageModifiers.AbstractDamageModifier;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import theDragonkin.powers.WindWalker.SpiritWound;

public class Wind extends AbstractDamageModifier {
    public boolean ignoresBlock() {
        return true;
    }
    public TooltipInfo getCustomTooltip() {
        return new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Wind"),BaseMod.getKeywordDescription("thedragonkin:Wind"));
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCreature target, AbstractCard card) {
        if (target.currentBlock > 0) {
            return damage*0.8f;
        }
        return damage; //Only used for AbstractCard.calculateDamage() as of Version 0.0.3
    }
}
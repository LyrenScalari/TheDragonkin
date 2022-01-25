package theDragonkin.DamageModifiers;

import IconsAddon.damageModifiers.AbstractDamageModifier;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import theDragonkin.powers.WindWalker.Static;

import java.util.ArrayList;

public class LightningDamage extends AbstractDamageModifier {
    boolean inherent;
    String TipType;
    public LightningDamage(boolean isinherent){
        inherent = isinherent;
    }
    public boolean isInherent() {
        return inherent;
    }

    public ArrayList<TooltipInfo> getCustomTooltips() {
        ArrayList<TooltipInfo> tips = new ArrayList<>();
        tips.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Lightning"), BaseMod.getKeywordDescription("thedragonkin:Lightning")));
        tips.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Static"), BaseMod.getKeywordDescription("thedragonkin:Static")));
        return tips;
    }
    public void onDamageModifiedByBlock(DamageInfo info, int unblockedAmount, int blockedAmount, AbstractCreature target) {
        addToBot(new ApplyPowerAction(target,info.owner,new Static(target,info.owner,(int)Math.ceil(unblockedAmount/2))));
    }
    @Override
    public AbstractDamageModifier makeCopy() {
        return new LightningDamage(inherent);
    }
    public String getCardDescriptor() {
        return BaseMod.getKeywordTitle("thedragonkin:Lightning_Damage");
    }
    public int priority() {
        return 2;
    }
}
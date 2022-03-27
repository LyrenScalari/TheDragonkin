package theDragonknight.DamageModifiers;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import theDragonknight.powers.Dragonkin.PenancePower;
import theDragonknight.powers.WindWalker.Static;

import java.util.ArrayList;

public class DivineDamage extends AbstractDamageModifier {
    boolean inherent;
    public DivineDamage(boolean isinherent, boolean autoAdd){
        inherent = isinherent;
        this.automaticBindingForCards = autoAdd;
    }
    public boolean isInherent() {
        return inherent;
    }
    public ArrayList<TooltipInfo> getCustomTooltips() {
        ArrayList<TooltipInfo> tips = new ArrayList<>();
        tips.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Divine"), BaseMod.getKeywordDescription("thedragonkin:Divine")));
        return tips;
    }
    public void onDamageModifiedByBlock(DamageInfo info, int unblockedAmount, int blockedAmount, AbstractCreature target) {
        addToBot(new ApplyPowerAction(target,info.owner,new PenancePower(target,info.owner,(int)Math.ceil(unblockedAmount/2))));
    }
    @Override
    public AbstractDamageModifier makeCopy() {
        return new DivineDamage(inherent,automaticBindingForCards);
    }
    public int priority() {
        return 1;
    }
}
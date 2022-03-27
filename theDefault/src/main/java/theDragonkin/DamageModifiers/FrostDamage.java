package theDragonkin.DamageModifiers;


import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.DamageModifiers.BlockModifiers.FrostBlock;
import theDragonkin.powers.Dragonkin.FuryPower;

import java.util.ArrayList;

public class FrostDamage extends AbstractDamageModifier {
    boolean inherent;
    public FrostDamage(boolean isinherent, boolean autoAdd){
        inherent = isinherent;
        this.automaticBindingForCards = autoAdd;
    }
    public boolean isInherent() {
        return inherent;
    }
    public ArrayList<TooltipInfo> getCustomTooltips() {
        ArrayList<TooltipInfo> tips = new ArrayList<>();
        tips.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Frost"), BaseMod.getKeywordDescription("thedragonkin:Frost")));
        return tips;
    }
    public void onDamageModifiedByBlock(DamageInfo info, int unblockedAmount, int blockedAmount, AbstractCreature target) {
    }
    public String getCardDescriptor() {
        return BaseMod.getKeywordTitle("thedragonkin:Frost_Damage");
    }
    @Override
    public AbstractDamageModifier makeCopy() {
        return new FrostDamage(inherent,automaticBindingForCards);
    }
    public int priority() {
        return 1;
    }
}
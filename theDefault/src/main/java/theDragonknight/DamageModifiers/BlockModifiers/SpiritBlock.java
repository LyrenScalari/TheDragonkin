package theDragonknight.DamageModifiers.BlockModifiers;


import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.blockmods.AbstractBlockModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.helpers.CardHelper;
import theDragonknight.powers.WindWalker.SpiritWound;

import java.util.ArrayList;

public class SpiritBlock extends AbstractBlockModifier {
    public int lastblock;
    boolean inherent;
    public SpiritBlock(boolean inherent) {
        this.inherent = inherent;
    }
    public AbstractBlockModifier makeCopy() {
        return new SpiritBlock(inherent);
    }
    public boolean isInherent() {
        return inherent;
    }
    public void onApplication() {
        lastblock = getCurrentAmount();
    }
    public void onStack(int amount) {
        lastblock = getCurrentAmount();
    }
    @Override
    public String getName() {
        return BaseMod.getKeywordTitle("thedragonkin:Spirit");
    }

    @Override
    public String getDescription() {
        return BaseMod.getKeywordDescription("thedragonkin:Spirit_Damage");
    }
    public ArrayList<TooltipInfo> getCustomTooltips() {
        ArrayList<TooltipInfo> tips = new ArrayList<>();
        tips.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Spirit"), BaseMod.getKeywordDescription("thedragonkin:Spirit_Damage")));
        tips.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Spirit_Wound"), BaseMod.getKeywordDescription("thedragonkin:Spirit_Wound")));
        return tips;
    }

    public AbstractBlockModifier.Priority priority() {return Priority.BOTTOM; }

    public int onRemove(boolean lostByStartOfTurn, DamageInfo info, int remainingDamage) {
        if(!lostByStartOfTurn){
            addToBot(new ApplyPowerAction(info.owner,this.owner,new SpiritWound(info.owner,this.owner,lastblock)));
            addToBot(new ApplyPowerAction(this.owner,info.owner,new SpiritWound(this.owner,info.owner,lastblock)));
        }
        return remainingDamage;
    }
    public void onAttacked(DamageInfo info, int damageAmount) {
        if (lastblock - damageAmount > 0) {
            lastblock -= damageAmount;
        }
    }
    public Color blockImageColor() {
        return CardHelper.getColor(0.0f, 168.0f, 107.0f);
    }

    public Color blockTextColor() {
        return CardHelper.getColor(0.0f, 168.0f, 107.0f);
    }
    public int amountLostAtStartOfTurn() {
        return 0;
    }
}
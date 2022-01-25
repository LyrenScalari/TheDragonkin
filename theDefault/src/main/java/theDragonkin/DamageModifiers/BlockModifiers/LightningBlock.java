package theDragonkin.DamageModifiers.BlockModifiers;

import IconsAddon.blockModifiers.AbstractBlockModifier;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.helpers.CardHelper;
import theDragonkin.powers.WindWalker.Static;

import java.util.ArrayList;

public class LightningBlock extends AbstractBlockModifier {
    boolean inherent;
    public int lastblock;
    public LightningBlock(boolean inherent) {
        this.inherent = inherent;
    }
    public boolean isInherent() {
        return inherent;
    }
    public void onApplication() {
        lastblock = getCurrentAmount();
    }
    public AbstractBlockModifier.Priority priority() {return Priority.TOP; }
    public void onStack(int amount) {
        lastblock = getCurrentAmount();
    }
    @Override
    public String getName() {
        return BaseMod.getKeywordTitle("thedragonkin:Lightning");
    }
    public Color blockImageColor() {
        return CardHelper.getColor(235, 235, 52);
    }

    public Color blockTextColor() {
        return CardHelper.getColor(235, 235, 52);
    }

    @Override
    public String getDescription() {
        return BaseMod.getKeywordDescription("thedragonkin:Lightning_Damage");
    }
    public ArrayList<TooltipInfo> getCustomTooltips() {
        ArrayList<TooltipInfo> tips = new ArrayList<>();
        tips.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Lightning"), BaseMod.getKeywordDescription("thedragonkin:Lightning_Damage")));
        tips.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Static"), BaseMod.getKeywordDescription("thedragonkin:Static")));
        return tips;
    }
    @Override
    public AbstractBlockModifier makeCopy() {
        return new LightningBlock(inherent);
    }
    public int onRemove(boolean lostByStartOfTurn, DamageInfo info, int remainingDamage) {
        if(!lostByStartOfTurn){
            addToBot(new ApplyPowerAction(info.owner,this.owner,new Static(info.owner,this.owner,lastblock)));
        }
        return remainingDamage;
    }
    public void onThisBlockDamaged(DamageInfo info, int lostAmount) {
        if (lastblock - lostAmount > 0) {
            lastblock -= lostAmount;
        }
    }

}

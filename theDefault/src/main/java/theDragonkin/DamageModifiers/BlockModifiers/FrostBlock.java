package theDragonkin.DamageModifiers.BlockModifiers;

import IconsAddon.blockModifiers.AbstractBlockModifier;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.powers.WeakPower;
import theDragonkin.powers.WindWalker.SpiritWound;

import java.util.ArrayList;

public class FrostBlock extends AbstractBlockModifier {
    public int lastblock;
    boolean inherent;

    public FrostBlock(boolean inherent) {
        this.inherent = inherent;
    }
    public AbstractBlockModifier makeCopy() {
        return new FrostBlock(inherent);
    }

    public boolean isInherent() {
        return inherent;
    }

    @Override
    public String getName() {
        return BaseMod.getKeywordTitle("thedragonkin:Frost");
    }

    @Override
    public String getDescription() {
        return BaseMod.getKeywordDescription("thedragonkin:Frost_Damage");
    }
    public ArrayList<TooltipInfo> getCustomTooltips() {
        ArrayList<TooltipInfo> tips = new ArrayList<>();
        tips.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Frost"), BaseMod.getKeywordDescription("thedragonkin:Frost_Damage")));
        return tips;
    }

    public AbstractBlockModifier.Priority priority() {return Priority.BOTTOM; }

    public Color blockImageColor() {
        return CardHelper.getColor(0.0f, 68.0f, 197.0f);
    }

    public Color blockTextColor() {
        return CardHelper.getColor(0.0f, 68.0f, 197.0f);
    }
    public int amountLostAtStartOfTurn() {
        return 0;
    }
    public void onStartOfTurnBlockLoss(int blockLost) {
        addToBot(new ApplyPowerAction(owner,owner,new WeakPower(owner,1,false)));
    }
}
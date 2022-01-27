package theDragonkin.DamageModifiers.BlockModifiers;

import IconsAddon.blockModifiers.AbstractBlockModifier;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.helpers.CardHelper;
import theDragonkin.powers.Dragonkin.Scorchpower;

import java.util.ArrayList;

public class FireBlock extends AbstractBlockModifier {
    public int lastblock;
    boolean inherent;

    public FireBlock(boolean inherent) {
        this.inherent = inherent;
    }

    public AbstractBlockModifier makeCopy() {
        return new FireBlock(inherent);
    }

    public boolean isInherent() {
        return inherent;
    }

    @Override
    public String getName() {
        return BaseMod.getKeywordTitle("thedragonkin:Fire");
    }

    @Override
    public String getDescription() {
        return BaseMod.getKeywordDescription("thedragonkin:Fire_Damage");
    }

    public ArrayList<TooltipInfo> getCustomTooltips() {
        ArrayList<TooltipInfo> tips = new ArrayList<>();
        tips.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Fire"), BaseMod.getKeywordDescription("thedragonkin:Fire_Damage")));
        return tips;
    }
    public void onAttacked(DamageInfo info, int damageAmount) {
        addToBot(new ApplyPowerAction(info.owner,this.owner,new Scorchpower(info.owner,this.owner,damageAmount)));
    }
    public AbstractBlockModifier.Priority priority() {
        return Priority.NORMAL;
    }

    public Color blockImageColor() {
        return CardHelper.getColor(107.0f, 38.0f, 00.0f);
    }

    public Color blockTextColor() {
        return CardHelper.getColor(107.0f, 38.0f, 00.0f);
    }

    public int amountLostAtStartOfTurn() {
        return getCurrentAmount();
    }
}
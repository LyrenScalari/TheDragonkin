package theDragonkin.DamageModifiers.BlockModifiers;

import IconsAddon.blockModifiers.AbstractBlockModifier;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.powers.WeakPower;
import theDragonkin.DragonkinMod;

import java.util.ArrayList;

public class DivineBlock extends AbstractBlockModifier {
    boolean inherent;

    public DivineBlock(boolean inherent) {
        this.inherent = inherent;
    }

    public AbstractBlockModifier makeCopy() {
        return new DivineBlock(inherent);
    }

    public boolean isInherent() {
        return inherent;
    }

    @Override
    public String getName() {
        return BaseMod.getKeywordTitle("thedragonkin:Divine");
    }

    @Override
    public String getDescription() {
        return BaseMod.getKeywordDescription("thedragonkin:Divine_Damage");
    }

    public ArrayList<TooltipInfo> getCustomTooltips() {
        ArrayList<TooltipInfo> tips = new ArrayList<>();
        tips.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Divine"), BaseMod.getKeywordDescription("thedragonkin:Divine_Damage")));
        return tips;
    }

    public AbstractBlockModifier.Priority priority() {
        return Priority.TOP;
    }

    public Color blockImageColor() {
        return CardHelper.getColor(255.0f, 255.0f, 255.0f);
    }

    public Color blockTextColor() {
        return CardHelper.getColor(255.0f, 255.0f, 255.0f);
    }

    public Texture customBlockImage() {
        return DragonkinMod.DIVINE_ARMOR_ICON;
    }

    public int amountLostAtStartOfTurn() {
        return 0;
    }
}
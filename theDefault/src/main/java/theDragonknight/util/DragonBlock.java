package theDragonknight.util;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.blockmods.AbstractBlockModifier;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import theDragonknight.DragonknightMod;
import theDragonknight.powers.DragonscalesPower;
import theDragonknight.powers.LosePowerPower;

import java.util.ArrayList;

public class DragonBlock extends AbstractBlockModifier {
    boolean inherent;

    public DragonBlock(boolean inherent) {
        this.inherent = inherent;
    }
    public AbstractBlockModifier makeCopy() {
        return new DragonBlock(inherent);
    }

    public boolean isInherent() {
        return inherent;
    }

    @Override
    public String getName() {
        return BaseMod.getKeywordTitle("thedragonknight:Dragonscales");
    }

    @Override
    public String getDescription() {
        return BaseMod.getKeywordDescription("thedragonknight:Dragonscales");
    }
    public ArrayList<TooltipInfo> getCustomTooltips() {
        ArrayList<TooltipInfo> tips = new ArrayList<>();
        tips.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonknight:Dragonscales"), BaseMod.getKeywordDescription("thedragonknight:Dragonscales")));
        return tips;
    }
    public AbstractBlockModifier.Priority priority() {return Priority.NORMAL; }
    public Color blockImageColor() {
        return CardHelper.getColor(255.0f, 255.0f, 255.0f);
    }
    public Color blockTextColor() {
        return CardHelper.getColor(167.0f, 00.0f, 137.0f);
    }
    public Texture customBlockImage() {
        return DragonknightMod.DIVINE_ARMOR_ICON;
    }
    public int amountLostAtStartOfTurn() {
        if (owner.hasPower(DragonscalesPower.POWER_ID)){
            addToBot(new ReducePowerAction(owner,owner,DragonscalesPower.POWER_ID,1));
            for (AbstractPower p : owner.powers){
                if (p instanceof LosePowerPower){
                    if (((LosePowerPower) p).powerToLose instanceof DragonscalesPower){
                        addToBot(new ReducePowerAction(owner,owner,p,1));
                    }
                }
            }
            return 0;
        } else return this.getCurrentAmount();
    }
}
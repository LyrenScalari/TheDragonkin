package theDragonkin.DamageModifiers.BlockModifiers;

import IconsAddon.blockModifiers.AbstractBlockModifier;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theDragonkin.util.Wiz;

import java.util.ArrayList;

public class ArcaneBlock extends AbstractBlockModifier {
    public int lastblock;
    boolean inherent;

    public ArcaneBlock(boolean inherent) {
        this.inherent = inherent;
    }
    public AbstractBlockModifier makeCopy() {
        return new ArcaneBlock(inherent);
    }

    public boolean isInherent() {
        return inherent;
    }

    @Override
    public String getName() {
        return BaseMod.getKeywordTitle("thedragonkin:Arcane");
    }

    @Override
    public String getDescription() {
        return BaseMod.getKeywordDescription("thedragonkin:Arcane_Damage");
    }
    public ArrayList<TooltipInfo> getCustomTooltips() {
        ArrayList<TooltipInfo> tips = new ArrayList<>();
        tips.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Arcane"), BaseMod.getKeywordDescription("thedragonkin:Arcane_Damage")));
        return tips;
    }
    public float onModifyBlock(float block, AbstractCard card) {
        AbstractPower Fury = AbstractDungeon.player.getPower(FocusPower.POWER_ID);
        if (Fury != null) {
            return block + AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount;
        }
        return block;
    }
    public AbstractBlockModifier.Priority priority() {return Priority.NORMAL; }

    public Color blockImageColor() {
        return CardHelper.getColor(167.0f, 00.0f, 137.0f);
    }

    public Color blockTextColor() {
        return CardHelper.getColor(167.0f, 00.0f, 137.0f);
    }
    public void onStartOfTurnBlockLoss(int blockLost) {
        Wiz.applyToSelfTemp(new FocusPower(owner,blockLost));
    }
}
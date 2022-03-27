package theDragonknight.DamageModifiers;


import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.LockOnPower;

import java.util.ArrayList;

public class ArcaneDamage extends AbstractDamageModifier {
    boolean inherent;
    public ArcaneDamage(boolean isinherent, boolean autoAdd){
        inherent = isinherent;
        this.automaticBindingForCards = autoAdd;
    }
    public boolean isInherent() {
        return inherent;
    }
    public ArrayList<TooltipInfo> getCustomTooltips() {
        ArrayList<TooltipInfo> tips = new ArrayList<>();
        tips.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Arcane"), BaseMod.getKeywordDescription("thedragonkin:Arcane")));
        return tips;
    }

    @Override
    public AbstractDamageModifier makeCopy() {
        return new ArcaneDamage(inherent,automaticBindingForCards);
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCreature target, AbstractCard card) {
        AbstractPower Fury = AbstractDungeon.player.getPower(FocusPower.POWER_ID);
        if (Fury != null) {
            damage += AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount;
        }
        if (target.hasPower(LockOnPower.POWER_ID)){
            damage = damage+(damage*0.50f);
        }
        return damage;
    }
    public int priority() {
        return 1;
    }
}

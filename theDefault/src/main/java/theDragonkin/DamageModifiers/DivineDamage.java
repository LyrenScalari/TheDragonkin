package theDragonkin.DamageModifiers;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theDragonkin.powers.Dragonkin.FuryPower;
import theDragonkin.powers.Dragonkin.PenancePower;
import theDragonkin.powers.WindWalker.Static;

import java.util.ArrayList;

public class DivineDamage extends AbstractDamageModifier {
    boolean inherent;
    public DivineDamage(){
        inherent = true;
        this.automaticBindingForCards = false;
    }
    public boolean isInherent() {
        return inherent;
    }
    public String getCardDescriptor() {
        return BaseMod.getKeywordTitle("thedragonkin:Holy");
    }
    @Override
    public AbstractDamageModifier makeCopy() {
        return new DivineDamage();
    }
    public int priority() {
        return 1;
    }
}
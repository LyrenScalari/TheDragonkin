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
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.powers.Dragonkin.FuryPower;
import theDragonkin.powers.Dragonkin.Scorchpower;
import theDragonkin.powers.WindWalker.Static;

import java.util.ArrayList;

public class FireDamage extends AbstractDamageModifier {
    boolean inherent;
    public FireDamage(boolean isinherent, boolean autoAdd){
        inherent = isinherent;
        this.automaticBindingForCards = autoAdd;
    }
    public boolean isInherent() {
        return inherent;
    }
    public ArrayList<TooltipInfo> getCustomTooltips() {
        ArrayList<TooltipInfo> tips = new ArrayList<>();
        tips.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Fire"), BaseMod.getKeywordDescription("thedragonkin:Fire")));
        return tips;
    }

    @Override
    public AbstractDamageModifier makeCopy() {
        return new FireDamage(inherent,automaticBindingForCards);
    }

    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (damageAmount/2 > 0){
            addToBot(new ApplyPowerAction(target,info.owner,new Scorchpower(target,info.owner,(int)Math.ceil(damageAmount/2))));
        }
    }
    public int priority() {
        return 1;
    }
}
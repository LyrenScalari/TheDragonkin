package theDragonkin.DamageModifiers;

import IconsAddon.damageModifiers.AbstractDamageModifier;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
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

    public void onDamageModifiedByBlock(DamageInfo info, int unblockedAmount, int blockedAmount, AbstractCreature target) {
        addToBot(new ApplyPowerAction(target,info.owner,new Scorchpower(target,info.owner,(int)Math.ceil(unblockedAmount+blockedAmount))));
    }
    public float atDamageFinalGive(float damage, DamageInfo.DamageType type, AbstractCreature target, AbstractCard card) {
        if (target.currentHealth > 0){
            return (float) Math.ceil(damage/2);
        } return damage;
    }
    public int priority() {
        return 1;
    }
}
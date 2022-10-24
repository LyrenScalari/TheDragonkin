package theDragonkin.augments;

import CardAugments.cardmods.AbstractAugment;
import basemod.BaseMod;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.DragonkinMod;
import theDragonkin.actions.PyroAction;
import theDragonkin.orbs.WrathSeal;
import theDragonkin.util.TriggerOnCycleEffect;
import theDragonkin.util.TriggerOnCycleMod;

public class PyroMod extends AbstractAugment implements TriggerOnCycleMod {
    public static final String ID = DragonkinMod.makeID("PyroMod");
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    private int baseCost;
    private int increaseamt = 5;
    @Override
    public AugmentRarity getModRarity() {
        return AugmentRarity.RARE;
    }

    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if (card.baseDamage > 0) {
            return damage * 4;
        }
        return damage;
    }
    public void onDrawn(AbstractCard card) {
       addToTop(new MakeTempCardInHandAction(new Burn(), 1));
    }
    @Override
    public void onInitialApplication(AbstractCard card) {
        baseCost = Math.max(1, card.cost);
        card.cost = card.cost + 2;
        card.costForTurn = card.cost;
    }
    @Override
    public boolean validCard(AbstractCard abstractCard) {
        return DragonkinMod.enableChimeraCrossover && cardCheck(abstractCard, (c) -> (c.baseDamage > 0));
    }
    @Override
    public String modifyName(String cardName, AbstractCard card) {
        return TEXT[0] + cardName + TEXT[1];
    }
    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + String.format(TEXT[2], increaseamt);
    }
    @Override
    public AbstractCardModifier makeCopy() {
        return new PyroMod();
    }
    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public void TriggerOnCycle(AbstractCard owner,AbstractCard discard) {
        owner.updateCost(-1);
        owner.baseDamage += increaseamt;
        owner.damage += increaseamt;
    }
}
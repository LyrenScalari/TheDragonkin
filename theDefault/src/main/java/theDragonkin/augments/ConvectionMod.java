package theDragonkin.augments;

import CardAugments.cardmods.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.DamageModifiers.DivineDamage;
import theDragonkin.DragonkinMod;
import theDragonkin.actions.CycleAction;
import theDragonkin.cards.Dragonkin.AbstractHolyCard;
import theDragonkin.cards.Dragonkin.AbstractPrimalCard;
import theDragonkin.powers.Dragonkin.PenancePower;
import theDragonkin.powers.Dragonkin.SacrificePower;

public class ConvectionMod extends AbstractAugment {
    public static final String ID = DragonkinMod.makeID("ConvectionMod");
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static int PENANCE = 1;
    @Override
    public AugmentRarity getModRarity() {
        return AugmentRarity.COMMON;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        addToBot(new SelectCardsInHandAction(PENANCE," Cycle",false,false,(c)->true,(List)-> {
            for (AbstractCard c : List){
                addToBot(new CycleAction(c,1));
            }
        }));
    }
    @Override
    public boolean validCard(AbstractCard abstractCard) {
        return DragonkinMod.enableChimeraCrossover && cardCheck(abstractCard, (c) -> (c.baseDamage > 0 || c.baseBlock > 0));
    }
    @Override
    public String modifyName(String cardName, AbstractCard card) {
        return TEXT[0] + cardName + TEXT[1];
    }
    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + String.format(TEXT[2], PENANCE);
    }
    @Override
    public AbstractCardModifier makeCopy() {
        return new ConvectionMod();
    }
    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}

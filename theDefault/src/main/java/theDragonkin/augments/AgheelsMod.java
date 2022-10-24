package theDragonkin.augments;

import CardAugments.cardmods.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import basemod.abstracts.CustomCard;
import basemod.devcommands.power.Power;
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
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import theDragonkin.DamageModifiers.DivineDamage;
import theDragonkin.DamageModifiers.Icons.PrimalDamage;
import theDragonkin.DragonkinMod;
import theDragonkin.actions.CycleAction;
import theDragonkin.cards.Dragonkin.AbstractHolyCard;
import theDragonkin.cards.Dragonkin.AbstractPrimalCard;
import theDragonkin.powers.Dragonkin.PenancePower;
import theDragonkin.powers.Dragonkin.SacrificePower;
import theDragonkin.powers.Dragonkin.Scorchpower;

public class AgheelsMod extends AbstractAugment {
    public static final String ID = DragonkinMod.makeID("AgheelsMod");
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static int PENANCE = 3;
    @Override
    public AugmentRarity getModRarity() {
        return AugmentRarity.UNCOMMON;
    }

    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        return damage * MINOR_DEBUFF;
    }

    @Override
    public float modifyBaseBlock(float block, AbstractCard card) {
        return block * MINOR_DEBUFF;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        for (final AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters){
            addToBot(new ApplyPowerAction(mo,AbstractDungeon.player,new Scorchpower(mo,AbstractDungeon.player,PENANCE)));
        }
        if (card.costForTurn > 0) {
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new VigorPower(AbstractDungeon.player, card.costForTurn*2)));
        }
    }
    public void onInitialApplication(AbstractCard card) {
        DamageModifierManager.addModifier(card, new PrimalDamage());
        if (card instanceof CustomCard){
            ((CustomCard) card).setOrbTexture(DragonkinMod.PRIMAL_SMALL_ORB, DragonkinMod.PRIMAL_LARGE_ORB);
        }
    }
    @Override
    public boolean validCard(AbstractCard abstractCard) {
        return DragonkinMod.enableChimeraCrossover && !(abstractCard instanceof AbstractHolyCard || abstractCard instanceof AbstractPrimalCard) && cardCheck(abstractCard, (c) -> (c.baseDamage > 0 || c.baseBlock > 0));
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
        return new AgheelsMod();
    }
    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}

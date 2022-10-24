package theDragonkin.augments;

import CardAugments.CardAugmentsMod;
import CardAugments.cardmods.AbstractAugment;
import basemod.BaseMod;
import basemod.abstracts.AbstractCardModifier;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.utils.compression.lzma.Base;
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
import theDragonkin.cards.Dragonkin.AbstractHolyCard;
import theDragonkin.cards.Dragonkin.AbstractPrimalCard;
import theDragonkin.powers.Dragonkin.PenancePower;
import theDragonkin.powers.Dragonkin.SacrificePower;

public class SinnersMod extends AbstractAugment {
    public static final String ID = DragonkinMod.makeID("SinnersMod");
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static int PENANCE = 2;
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
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new PenancePower(AbstractDungeon.player,AbstractDungeon.player,PENANCE)));
        for (final AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters){
            addToBot(new ApplyPowerAction(mo,AbstractDungeon.player,new PenancePower(mo,AbstractDungeon.player,PENANCE)));
        }
        if (card.costForTurn > 0) {
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SacrificePower(AbstractDungeon.player, AbstractDungeon.player, card.costForTurn*2)));
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
    public void onInitialApplication(AbstractCard card) {
        DamageModifierManager.addModifier(card, new DivineDamage());
        if (card instanceof CustomCard){
            ((CustomCard) card).setOrbTexture(DragonkinMod.HOLY_SMALL_ORB, DragonkinMod.HOLY_LARGE_ORB);
        }
    }
    @Override
    public AbstractCardModifier makeCopy() {
        return new SinnersMod();
    }
    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}

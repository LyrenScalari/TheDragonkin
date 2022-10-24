package theDragonkin.augments;

import CardAugments.cardmods.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import theDragonkin.DamageModifiers.Icons.PrimalDamage;
import theDragonkin.DragonkinMod;
import theDragonkin.characters.TheDefault;
import theDragonkin.orbs.WrathSeal;
import theDragonkin.powers.Dragonkin.Scorchpower;

public class FlagellantsMod extends AbstractAugment {
    public static final String ID = DragonkinMod.makeID("FlagellantsMod");
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static int PENANCE = 5;
    @Override
    public AugmentRarity getModRarity() {
        return AugmentRarity.UNCOMMON;
    }

    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        return damage * MAJOR_DEBUFF;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                DragonkinMod.Seals.add(new WrathSeal(card.damage*3,PENANCE));
                isDone = true;
            }
        });
    }
    @Override
    public boolean validCard(AbstractCard abstractCard) {
        return DragonkinMod.enableChimeraCrossover && (AbstractDungeon.player.chosenClass == TheDefault.Enums.THE_JUSTICAR || AbstractDungeon.player.chosenClass == AbstractPlayer.PlayerClass.IRONCLAD) && cardCheck(abstractCard, (c) -> (c.baseDamage > 0));
    }
    @Override
    public String modifyName(String cardName, AbstractCard card) {
        return TEXT[0] + cardName + TEXT[1];
    }
    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + String.format(TEXT[2], card.damage*3);
    }
    @Override
    public AbstractCardModifier makeCopy() {
        return new FlagellantsMod();
    }
    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
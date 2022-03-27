package theDragonknight.cards.Dragonkin;


import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.evacipated.cardcrawl.mod.stslib.blockmods.BlockModifierManager;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonknight.CardMods.AddIconToDescriptionMod;
import theDragonknight.DamageModifiers.BlockModifiers.FireBlock;
import theDragonknight.DamageModifiers.Icons.FireIcon;
import theDragonknight.DragonkinMod;
import theDragonknight.actions.CycleAction;
import theDragonknight.characters.TheDefault;

import static theDragonknight.DragonkinMod.makeCardPath;

public class HeatShield extends AbstractPrimalCard {

    public static final String ID = DragonkinMod.makeID(HeatShield.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int POTENCY= 7;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC = 1;

    public HeatShield() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = POTENCY;
        block = baseBlock = POTENCY;
        heal = baseHeal = POTENCY;
        baseMagicNumber = magicNumber = MAGIC;
        BlockModifierManager.addModifier(this,new FireBlock(true));
        CardModifierManager.addModifier(this,new AddIconToDescriptionMod(AddIconToDescriptionMod.BLOCK, FireIcon.get()));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        returnToHand = false;
        AbstractCard card = this;
        addToBot(new GainBlockAction(p,block));
        addToBot(new SelectCardsInHandAction(1," Cycle",false,false,(cards)->true,(List)->{
            addToBot(new CycleAction(List.get(0),1));
            if (List.get(0).type == CardType.STATUS){
                card.returnToHand = true;
            }}));
        super.use(p,m);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}
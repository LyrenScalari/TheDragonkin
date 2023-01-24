package theDragonkin.cards.Dragonkin;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.CardMods.AddIconToDescriptionMod;
import theDragonkin.DamageModifiers.FireDamage;
import theDragonkin.DamageModifiers.Icons.FireIcon;
import theDragonkin.DragonkinMod;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.Dragonkin.DivineConvictionpower;

import static theDragonkin.DragonkinMod.makeCardPath;

public class DivineEmber extends AbstractHolyCard {

    public static final String ID = DragonkinMod.makeID(DivineEmber.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.STATUS;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int POTENCY = 8;
    private static final int UPGRADE_PLUS_POTENCY = 0;
    private static final int MAGIC = 5;
    private static final int UPGRADE_MAGIC = 1;

    public DivineEmber() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        if (p.hasPower(DivineConvictionpower.POWER_ID)) {
            if (p.getPower(DivineConvictionpower.POWER_ID).amount >= defaultSecondMagicNumber){
                addToBot(new ReducePowerAction(p,p,p.getPower(DivineConvictionpower.POWER_ID),defaultSecondMagicNumber));
                addToBot(new ExhaustSpecificCardAction(this,AbstractDungeon.player.discardPile));
                addToBot(new ExhaustSpecificCardAction(this,AbstractDungeon.player.drawPile));
                addToBot(new ExhaustSpecificCardAction(this,AbstractDungeon.player.hand));
            }
        }
        super.use(p,m);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(-1);
            upgradeDefaultSecondMagicNumber(-1);
            initializeDescription();
        }
    }
}
package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ConstrictedPower;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheDefault;

import static theDragonkin.DefaultMod.makeCardPath;

public class BindingSlime extends AbstractDragonkinCard {

    public static final String ID = DefaultMod.makeID(BindingSlime.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int POTENCY = 5;
    private static final int UPGRADE_PLUS_POTENCY = 3;
    private static final int MAGIC = 0;
    private static final int UPGRADE_MAGIC = 0;

    public BindingSlime() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = POTENCY;
        block = baseBlock = POTENCY;
        heal = baseHeal = POTENCY;
        baseMagicNumber = magicNumber = POTENCY;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(m,p,new ConstrictedPower(m,p,magicNumber)));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_POTENCY);
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}
package theDragonkin.cards.Dragonkin;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.HolyBarrierpower;

import static theDragonkin.DefaultMod.makeCardPath;

public class HolyBarrier extends AbstractHolyBonusCard {

    public static final String ID = DefaultMod.makeID(HolyBarrier.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int POTENCY = 4;
    private static final int UPGRADE_PLUS_POTENCY= 2;
    private static final int MAGIC = 2;
    private static final int UPGRADE_MAGIC = 2;

    public HolyBarrier() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = POTENCY;
        block = baseBlock = POTENCY;
        heal = baseHeal = POTENCY;
        tags.add(CustomTags.HOLY_CARD);
        ExhaustiveVariable.setBaseValue(this, 3);
        baseMagicNumber = magicNumber = MAGIC;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p, new HolyBarrierpower(p,p,magicNumber)));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_POTENCY);
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }
}
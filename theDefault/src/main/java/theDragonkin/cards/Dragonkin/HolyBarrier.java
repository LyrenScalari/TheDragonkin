package theDragonkin.cards.Dragonkin;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.actions.TwinRoadsDiscardAction;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.Dragonkin.HolyBarrierpower;

import static theDragonkin.DefaultMod.makeCardPath;

public class HolyBarrier extends AbstractHolyBonusCard {

    public static final String ID = DefaultMod.makeID(HolyBarrier.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;

    private static final int COST = -1;
    private static final int UPGRADED_COST = 1;

    private static final int POTENCY = 3;
    private static final int UPGRADE_PLUS_POTENCY= 2;
    private static final int MAGIC = 2;
    private static final int UPGRADE_MAGIC = 1;
    public static int repeats = 0;
    public HolyBarrier() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = POTENCY;
        block = baseBlock = POTENCY;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = POTENCY;
        tags.add(CustomTags.HOLY_CARD);
        ExhaustiveVariable.setBaseValue(this, 3);
        baseMagicNumber = magicNumber = MAGIC;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.player.hasRelic("Chemical X")) {
            repeats += 2;
            AbstractDungeon.player.getRelic("Chemical X").flash();
        }
        repeats += EnergyPanel.totalCount;
        for (int i = 0; i < this.magicNumber + repeats; ++i) {
           addToBot(new AddTemporaryHPAction(p,p,defaultSecondMagicNumber));
        }
        EnergyPanel.useEnergy(EnergyPanel.totalCount);
        repeats = 0;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDefaultSecondMagicNumber(UPGRADE_PLUS_POTENCY);
            initializeDescription();
        }
    }
}
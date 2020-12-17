package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheDefault;

import static theDragonkin.DefaultMod.makeCardPath;

public class ObsidianRite extends AbstractDragonkinCard {

    public static final String ID = DefaultMod.makeID(ObsidianRite.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 2;

    private static final int POTENCY = 13;
    private static final int UPGRADE_PLUS_POTENCY = 3;
    private static final int MAGIC = 3;
    private static final int UPGRADE_MAGIC = 1;

    public ObsidianRite() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = POTENCY;
        block = baseBlock = POTENCY;
        heal = baseHeal = POTENCY;
        baseMagicNumber = magicNumber = MAGIC;
        this.exhaust = true;
        this.isMultiDamage = true;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new StrengthPower(p,magicNumber),magicNumber));
        addToBot(new ExhaustAction(magicNumber,true,false,false));
        for (int i = 0; i < this.magicNumber; ++i) {
            addToBot(new DamageRandomEnemyAction(new DamageInfo(p,damage), AbstractGameAction.AttackEffect.FIRE));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_POTENCY);
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }
}
package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.DivineConvictionpower;

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

    private static final int POTENCY = 15;
    private static final int UPGRADE_PLUS_POTENCY = 5;
    private static final int MAGIC = 2;
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
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p,baseDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE));
        addToBot(new ApplyPowerAction(p,p,new StrengthPower(p,magicNumber),magicNumber));
        addToBot(new LoseHPAction(p,p,5, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
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
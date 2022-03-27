package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.DragonkinMod;
import theDragonkin.characters.TheDefault;

import static theDragonkin.DragonkinMod.makeCardPath;

public class EduranceStrike extends AbstractDragonkinCard {

    public static final String ID = DragonkinMod.makeID(EduranceStrike.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;

    private static final int POTENCY= 10;
    private static final int UPGRADE_PLUS_DMG = 4;
    private static final int MAGIC = 6;
    private static final int UPGRADE_MAGIC = 2;

    public EduranceStrike() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = 10;
        realBaseDamage = 10;
        magicNumber = baseMagicNumber = 3;
    }
    public void applyPowers() {
        realBaseDamage = baseDamage;
        for (AbstractPower p :AbstractDungeon.player.powers){
            if (p.type == AbstractPower.PowerType.DEBUFF){
                baseDamage += magicNumber;
            }
        }
        super.applyPowers();
        baseDamage = realBaseDamage;
    }

    public void calculateCardDamage(AbstractMonster mo) {
        realBaseDamage = baseDamage;
        for (AbstractPower p :AbstractDungeon.player.powers){
            if (p.type == AbstractPower.PowerType.DEBUFF){
                baseDamage += magicNumber;
            }
        }
        for (AbstractPower p : mo.powers){
            if (p.type == AbstractPower.PowerType.DEBUFF){
                baseDamage += magicNumber;
            }
        }
        super.calculateCardDamage(mo);
        baseDamage = realBaseDamage;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m,new DamageInfo(p,damage)));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(2);
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}

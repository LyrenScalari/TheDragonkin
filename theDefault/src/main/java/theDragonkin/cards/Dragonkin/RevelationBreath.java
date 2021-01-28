package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.Dragonkin.DragonBreaths.RevelationBreathEffect;

import static theDragonkin.DefaultMod.makeCardPath;

public class RevelationBreath extends AbstractHolyCard {


    public static final String ID = DefaultMod.makeID(RevelationBreath.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath("Attack.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;

    private static final int COST = 2;
    private static final int DAMAGE = 12;
    private static final int UPGRADE_PLUS_DMG = 2;

    // /STAT DECLARATION/

    public RevelationBreath() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        // Damage + Temp HP

        damage = baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = 5;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new TalkAction(true,cardStrings.EXTENDED_DESCRIPTION[1],(float) 0.5,(float) 2.0));
        addToBot(new ApplyPowerAction(p,p,new RevelationBreathEffect(damage,magicNumber,this)));
    }
    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        int realBaseMagic = this.baseMagicNumber;
        int diviser = (int)AbstractDungeon.getCurrRoom().monsters.monsters.stream().filter(it -> !it.isDeadOrEscaped()).count();
        if (AbstractDungeon.getCurrRoom().monsters.monsters.stream().filter(it -> !it.isDeadOrEscaped()).count() > 1){
            isMultiDamage = true;
            this.baseDamage =  (int)Math.ceil(((float)realBaseDamage / diviser));
            super.calculateCardDamage(mo);
            this.baseDamage = realBaseDamage;
            this.isDamageModified = this.damage != this.baseDamage;
            rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
            initializeDescription();
        } else {
            isMultiDamage = false;
            super.calculateCardDamage(mo);
            this.baseDamage = realBaseDamage;
            this.isDamageModified = this.damage != this.baseDamage;
            rawDescription = cardStrings.DESCRIPTION;
            initializeDescription();
        }
    }
    @Override
    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        int realBaseMagic = this.baseMagicNumber;
        int diviser = (int)AbstractDungeon.getCurrRoom().monsters.monsters.stream().filter(it -> !it.isDeadOrEscaped()).count();
        if (AbstractDungeon.getCurrRoom().monsters.monsters.stream().filter(it -> !it.isDeadOrEscaped()).count() > 1){
            isMultiDamage = true;
            this.baseDamage =  (int)Math.ceil(((float)realBaseDamage / diviser));
            super.applyPowers();
            this.baseDamage = realBaseDamage;
            this.isDamageModified = this.damage != this.baseDamage;
            rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
            initializeDescription();
        } else {
            isMultiDamage = false;
            super.applyPowers();
            this.baseDamage = realBaseDamage;
            this.isDamageModified = this.damage != this.baseDamage;
            rawDescription = cardStrings.DESCRIPTION;
            initializeDescription();
        }
    }
    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(3);
            initializeDescription();
        }
    }
}

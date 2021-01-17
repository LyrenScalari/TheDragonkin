package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.Dragonkin.DragonBreaths.SandBreathEffect;

import static theDragonkin.DefaultMod.makeCardPath;

public class SandBreath extends AbstractHolyCard {


    public static final String ID = DefaultMod.makeID(SandBreath.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath("Skill.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;

    private static final int COST = 1;

    // /STAT DECLARATION/

    public SandBreath() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        // Temp HP + Weak
        this.magicNumber = this.baseMagicNumber = 6;
        this.defaultSecondMagicNumber = this.defaultBaseSecondMagicNumber = 4;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new SandBreathEffect(defaultSecondMagicNumber,magicNumber)));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
            upgradeDefaultSecondMagicNumber(2);
            initializeDescription();
        }
    }
}
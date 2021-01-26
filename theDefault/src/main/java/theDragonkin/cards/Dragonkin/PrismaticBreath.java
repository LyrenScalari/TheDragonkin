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
import theDragonkin.powers.Dragonkin.DragonBreaths.PrismaticBreathEffect;

import static theDragonkin.DefaultMod.makeCardPath;

public class PrismaticBreath extends AbstractHolyCard {


    public static final String ID = DefaultMod.makeID(PrismaticBreath.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath("Skill.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;

    private static final int COST = 1;

    // /STAT DECLARATION/

    public PrismaticBreath() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        //Temp HP + Zeal.
        this.exhaust = true;
        this.magicNumber = this.baseMagicNumber = 6;
        this.defaultSecondMagicNumber = this.defaultBaseSecondMagicNumber = 1;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new TalkAction(true,cardStrings.EXTENDED_DESCRIPTION[0],(float) 0.5,(float) 2.0));
        addToBot(new ApplyPowerAction(p,p,new PrismaticBreathEffect(magicNumber,defaultSecondMagicNumber,this)));
    }
    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
            upgradeDefaultSecondMagicNumber(1);
            initializeDescription();
        }
    }
}
package theDragonkin.cards.Gremory.Powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.DefaultMod;
import theDragonkin.cards.Gremory.AbstractGremoryCard;
import theDragonkin.characters.TheGremory;
import theDragonkin.CustomTags;

import static theDragonkin.DefaultMod.makeCardPath;

public class SwiftasWind extends AbstractGremoryCard {


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(SwiftasWind.class.getSimpleName());
    public static final String IMG = makeCardPath("Power.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheGremory.Enums.Gremory_Purple_Color;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int UPGRADE_PLUS_BLOCK = 3;


    // /STAT DECLARATION/


    public SwiftasWind() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        magicNumber = baseMagicNumber = 1;
        this.tags.add(CustomTags.Physical);
        this.tags.add(CustomTags.Relic);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new theDragonkin.powers.Gremory.SwiftasWind(p,p,1)));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            isInnate = true;
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0] + cardStrings.DESCRIPTION;
            initializeDescription();
        }
    }
}

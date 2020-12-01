package theDragonkin.cards.Gremory.Choices;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.cards.Gremory.AbstractMagicGremoryCard;
import theDragonkin.cards.Gremory.Attacks.Magic.Icicle;
import theDragonkin.util.CustomTags;
import theDragonkin.DefaultMod;

import static theDragonkin.DefaultMod.makeCardPath;

public class SchoolIce extends AbstractMagicGremoryCard {
    public static final String ID = DefaultMod.makeID(SchoolIce.class.getSimpleName());
    public static final String IMG = makeCardPath("Power.png");


    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = CardColor.COLORLESS;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = -2;

    public SchoolIce() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        cardsToPreview = new Icicle();
        this.tags.add(CustomTags.School);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }
    @Override
    public void upgrade() {
    }
}

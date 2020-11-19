package theDragonkin.cards.Gremory;

import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheGremory;
import theDragonkin.powers.FiendishBlowPower;
import theDragonkin.powers.KillerBlowPower;

import static theDragonkin.DefaultMod.makeCardPath;

public class KillerBlow extends AbstractGremoryCard {


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(KillerBlow.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    public static final AbstractCard.CardColor COLOR = TheGremory.Enums.Gremory_Purple_Color;

    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int UPGRADE_PLUS_BLOCK = 3;


    // /STAT DECLARATION/


    public KillerBlow() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        baseMagicNumber = 2;
        ExhaustiveVariable.setBaseValue(this, 2);
        this.tags.add(AbstractCard.CardTags.STARTER_DEFEND); //Tag your strike, defend and form (Wraith form, Demon form, Echo form, etc.) cards so that they function correctly.
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new KillerBlowPower(p,p,magicNumber),2));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            ExhaustiveVariable.upgrade(this,1);
            initializeDescription();
        }
    }
}
package theDragonkin.cards.Gremory.Skills;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.DefaultMod;
import theDragonkin.cards.Gremory.AbstractGremoryCard;
import theDragonkin.cards.Gremory.AbstractInvokeCard;
import theDragonkin.cards.Gremory.Choices.FlowersAmbition;
import theDragonkin.cards.Gremory.Choices.ImmaculateSnow;
import theDragonkin.cards.Gremory.Choices.MoonsMarch;
import theDragonkin.cards.Gremory.Choices.WindsSong;
import theDragonkin.cards.Gremory.Powers.HorizonEdge;
import theDragonkin.characters.TheGremory;
import theDragonkin.util.CustomTags;

import static theDragonkin.DefaultMod.makeCardPath;

public class BeyondTheClouds extends AbstractInvokeCard {


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(BeyondTheClouds .class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheGremory.Enums.Gremory_Purple_Color;

    private static final int COST = 3;
    private static final int BLOCK = 5;
    private static final int UPGRADE_PLUS_BLOCK = 3;


    // /STAT DECLARATION/


    public BeyondTheClouds() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        magicNumber = baseMagicNumber = 2;
        CardPreview1 = new ImmaculateSnow();
        CardPreview2 = new WindsSong();
        CardPreview3 = new FlowersAmbition();
        CardPreview4 = new MoonsMarch();
        this.exhaust = true;
        this.tags.add(CustomTags.Physical);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            initializeDescription();
        }
    }
}

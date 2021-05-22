package theDragonkin.cards.GroveKeeper.Skills;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import theDragonkin.CustomTags;
import theDragonkin.DragonkinMod;
import theDragonkin.cards.GroveKeeper.AbstractGroveKeeperCard;
import theDragonkin.characters.TheGroveKeeper;
import theDragonkin.orbs.ThornBloom;

import static theDragonkin.DragonkinMod.makeCardPath;

public class HealingSun extends AbstractGroveKeeperCard {


    // TEXT DECLARATION

    public static final String ID = DragonkinMod.makeID(HealingSun.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheGroveKeeper.Enums.GroveKeeper_Forest_Color;

    private static final int COST = 1;
    private static final int BLOCK = 8;
    private static final int UPGRADE_PLUS_BLOCK = 2;


    // /STAT DECLARATION/


    public HealingSun() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        magicNumber = baseMagicNumber = 10;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 3;
        this.tags.add(CustomTags.Solar);
        this.setOrbTexture(DragonkinMod.Solar_SMALL_ORB, DragonkinMod.Solar_LARGE_ORB);
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AddTemporaryHPAction(p,p,magicNumber));
        for (AbstractOrb orb : p.orbs){
            if (orb instanceof ThornBloom){
                ((ThornBloom) orb).onHarvest(defaultSecondMagicNumber);
                addToBot(new MakeTempCardInDiscardAction(this.makeStatEquivalentCopy(),1));
                break;
            }
        }
    }
    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeDefaultSecondMagicNumber(-1);
            upgradeMagicNumber(3);
            upgradeName();
            initializeDescription();
        }
    }
}

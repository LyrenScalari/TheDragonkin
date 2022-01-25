package theDragonkin.cards.WindWalker;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.DragonkinMod;
import theDragonkin.characters.TheWindWalker;

import static theDragonkin.DragonkinMod.makeCardPath;

public class WindShielding extends AbstractWindWalkerCard {


    // TEXT DECLARATION

    public static final String ID = DragonkinMod.makeID(WindShielding.class.getSimpleName());
    public static final String IMG = makeCardPath("WindWalkerDefend.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheWindWalker.Enums.WindWalker_Jade_COLOR;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final int COST = 1;
    private static final int BLOCK = 6;
    private static final int UPGRADE_PLUS_BLOCK = 3;


    // /STAT DECLARATION/


    public WindShielding() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        magicNumber = baseMagicNumber = 5;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        addToBot(new SelectCardsInHandAction(1,cardStrings.EXTENDED_DESCRIPTION[0],card ->  card.hasTag(CardTags.STARTER_STRIKE),list->{
            for (AbstractCard card : list) {
            }
        }));

    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }
}
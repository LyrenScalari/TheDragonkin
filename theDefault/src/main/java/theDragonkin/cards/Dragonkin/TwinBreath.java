package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.Dragonkin.AcidMarkPower;
import theDragonkin.powers.Dragonkin.DragonBreaths.TwinBreathEffect;
import theDragonkin.powers.Dragonkin.Scorchpower;

import static theDragonkin.DefaultMod.makeCardPath;

public class TwinBreath extends AbstractPrimalCard {

    public static final String ID = DefaultMod.makeID(TwinBreath.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 1;
    private static final int UPGRADED_COST = 2;

    private static final int POTENCY = 10;
    private static final int UPGRADE_PLUS_POTENCY = 2;
    private static final int MAGIC = 10;
    private static final int UPGRADE_MAGIC = 2;

    public TwinBreath() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = POTENCY;
        baseMagicNumber = magicNumber = MAGIC;
        cardsToPreview = new CorrosiveBreath();

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new TwinBreathEffect(damage,magicNumber)));
        if (upgraded){
            cardsToPreview.upgrade();
            addToBot(new MakeTempCardInHandAction(cardsToPreview));
        } else addToBot(new MakeTempCardInHandAction(cardsToPreview));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_POTENCY);
            upgradeMagicNumber(UPGRADE_MAGIC);
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
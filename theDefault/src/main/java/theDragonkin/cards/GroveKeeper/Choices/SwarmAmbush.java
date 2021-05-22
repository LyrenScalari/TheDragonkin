package theDragonkin.cards.GroveKeeper.Choices;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import theDragonkin.CustomTags;
import theDragonkin.DragonkinMod;
import theDragonkin.cards.GroveKeeper.AbstractChooseOneCard;
import theDragonkin.cards.GroveKeeper.AbstractGroveKeeperCard;
import theDragonkin.cards.GroveKeeper.Attacks.SwarmStrike;
import theDragonkin.characters.TheGroveKeeper;

import static theDragonkin.DragonkinMod.makeCardPath;
@AutoAdd.Ignore
public class SwarmAmbush extends AbstractGroveKeeperCard {
    public static final String ID = DragonkinMod.makeID(SwarmAmbush.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.SPECIAL;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;
    public static final AbstractCard.CardColor COLOR = TheGroveKeeper.Enums.GroveKeeper_Forest_Color;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public AbstractMonster Target;
    public DamageInfo.DamageType dmgType = DamageInfo.DamageType.NORMAL;
    private static final int COST = 1;
    private static final int DAMAGE = 2;    // DAMAGE = 6
    private static final int UPGRADE_PLUS_DMG = 1;  // UPGRADE_PLUS_DMG = 4
    public static int swarmcount = 0;
    public SwarmAmbush() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = 2;
        this.tags.add(CustomTags.Neutral);
    }
    public SwarmAmbush(int magic, AbstractMonster enemy) {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(CustomTags.Neutral);
        magicNumber = baseMagicNumber = magic;
        Target = enemy;
    }
    public void onChoseThisOption() {
        AbstractChooseOneCard.lastchoice.addToBottom(new SwarmAmbush());
        AbstractChooseOneCard.Roadsuntraveled.addToBottom(new SwarmAssault());
        AbstractChooseOneCard.lastchoices.addToBottom(new SwarmAmbush());
        AbstractChooseOneCard.lastchoices.addToBottom(new SwarmAssault());
        addToBot(new ApplyPowerAction(Target,AbstractDungeon.player,new PoisonPower(Target,AbstractDungeon.player,magicNumber)));
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m,AbstractDungeon.player,new PoisonPower(Target,AbstractDungeon.player,magicNumber)));
    }

    @Override
    public void upgrade() {
        upgradeDamage(UPGRADE_PLUS_DMG);
    }

    public static int countCards() {
        int count = 0;
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (isStrike(c)) {
                ++count;
            }
        }

        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (isStrike(c)) {
                ++count;
            }
        }

        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (isStrike(c)) {
                ++count;
            }
        }

        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (isStrike(c)) {
                ++count;
            }
        }
        swarmcount = count;
        return swarmcount;
    }

    public static boolean isStrike(AbstractCard c) {
        return c instanceof SwarmStrike;
    }
    public void calculateCardDamage(AbstractMonster mo) {
        countCards();
        int realBaseMagic = this.baseMagicNumber;
        this.baseMagicNumber = realBaseMagic * countCards();
        magicNumber = baseMagicNumber;
        super.calculateCardDamage(mo);
        this.baseMagicNumber = realBaseMagic;
        this.isMagicNumberModified = this.magicNumber != this.baseMagicNumber;
    }

    public void applyPowers() {
        countCards();
        int realBaseMagic = this.baseMagicNumber;
        this.baseMagicNumber = realBaseMagic * countCards();
        magicNumber = baseMagicNumber;
        super.applyPowers();
        this.baseMagicNumber = realBaseMagic;
        this.isMagicNumberModified = this.magicNumber != this.baseMagicNumber;
    }
}

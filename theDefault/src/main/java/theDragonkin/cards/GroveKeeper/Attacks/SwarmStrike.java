package theDragonkin.cards.GroveKeeper.Attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.PerfectedStrike;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.DefaultMod;
import theDragonkin.cards.GroveKeeper.AbstractChooseOneCard;
import theDragonkin.cards.GroveKeeper.Choices.Starlord;
import theDragonkin.cards.GroveKeeper.Choices.StellarDrift;
import theDragonkin.cards.GroveKeeper.Choices.SwarmAmbush;
import theDragonkin.cards.GroveKeeper.Choices.SwarmAssault;
import theDragonkin.characters.TheGroveKeeper;

import java.util.ArrayList;
import java.util.Iterator;

import static theDragonkin.DefaultMod.makeCardPath;

public class SwarmStrike extends AbstractChooseOneCard {
    public static final String ID = DefaultMod.makeID(SwarmStrike .class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Power.png");// "public static final String IMG = makeCardPath("Strike.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheGroveKeeper.Enums.GroveKeeper_Forest_Color;

    private static final int COST = 1;  // COST = 1
    private static final int UPGRADED_COST = 1; // UPGRADED_COST = 1

    private static final int DAMAGE = 2;    // DAMAGE = 6
    private static final int UPGRADE_PLUS_DMG = 1;  // UPGRADE_PLUS_DMG = 4
    public static int swarmcount = 0;
    public SwarmStrike() {
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
        this.baseDamage = 2;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(CardTags.STRIKE);
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> NextChoices = new ArrayList<>();
        NextChoices.add(new SwarmAssault(damage,m));
        NextChoices.add(new SwarmAmbush(magicNumber,m));
        if (upgraded){
            for (AbstractCard c : NextChoices){
                c.upgrade();
            }
        }
        addToBot(new ChooseOneAction(NextChoices));
        addToBot(new MakeTempCardInDiscardAction(this.makeStatEquivalentCopy(),1));
    }

    public AbstractCard makeCopy() {
        return new SwarmStrike();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DMG);
            this.upgradeMagicNumber(UPGRADE_PLUS_DMG);
            this.initializeDescription();
        }

    }

    public int countCards() {
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

    public boolean isStrike(AbstractCard c) {
        return c instanceof SwarmStrike;
    }

    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        this.baseDamage = realBaseDamage * swarmcount;
        int realBaseMagic = this.baseMagicNumber;
        this.baseMagicNumber = realBaseMagic * swarmcount;
        magicNumber = baseMagicNumber;
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
        this.baseMagicNumber = realBaseMagic;
        this.isMagicNumberModified = this.magicNumber != this.baseMagicNumber;
    }

    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        this.baseDamage = realBaseDamage * swarmcount;
        int realBaseMagic = this.baseMagicNumber;
        this.baseMagicNumber = realBaseMagic * swarmcount;
        magicNumber = baseMagicNumber;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
        this.baseMagicNumber = realBaseMagic;
        this.isMagicNumberModified = this.magicNumber != this.baseMagicNumber;
    }
}

package theDragonkin.cards.GroveKeeper.Attacks;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.cards.GroveKeeper.AbstractGroveKeeperCard;
import theDragonkin.cards.GroveKeeper.Skills.InvigoratingSeeds;
import theDragonkin.characters.TheGroveKeeper;
import theDragonkin.orbs.InvigoratingBloom;
import theDragonkin.orbs.ThornBloom;
import theDragonkin.powers.GroveKeeper.NaturePower;

import static theDragonkin.DefaultMod.makeCardPath;

public class Thornwhip extends AbstractGroveKeeperCard {


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Thornwhip.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheGroveKeeper.Enums.GroveKeeper_Forest_Color;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 1;
    private static final int BLOCK = 7;
    private static final int UPGRADE_PLUS_BLOCK = 4;


    // /STAT DECLARATION/


    public Thornwhip() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        damage = baseDamage = BLOCK;
        magicNumber = baseMagicNumber = 1;
        this.tags.add(CustomTags.Neutral);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m,new DamageInfo(p,damage)));
        for (int i = 0; i < this.magicNumber; ++i) {
            addToBot(new ChannelAction(new ThornBloom()));
        }
    }
    public void triggerOnGlowCheck() {
        if (magicNumber > 1){
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        } else {
            rawDescription = cardStrings.DESCRIPTION;
            initializeDescription();
        }
    }
    public void calculateCardDamage(AbstractMonster mo) {
        if (AbstractDungeon.player.hasPower(NaturePower.POWER_ID)) {
            int realBaseMagic = this.baseMagicNumber;
            this.baseMagicNumber = realBaseMagic + ((TwoAmountPower)AbstractDungeon.player.getPower(NaturePower.POWER_ID)).amount2;
            magicNumber = baseMagicNumber;
            super.calculateCardDamage(mo);
            this.baseMagicNumber = realBaseMagic;
            this.isMagicNumberModified = this.magicNumber != this.baseMagicNumber;
        }
    }

    public void applyPowers() {
        if (AbstractDungeon.player.hasPower(NaturePower.POWER_ID)) {
            int realBaseMagic = this.baseMagicNumber;
            this.baseMagicNumber = realBaseMagic + ((TwoAmountPower)AbstractDungeon.player.getPower(NaturePower.POWER_ID)).amount2;
            magicNumber = baseMagicNumber;
            super.applyPowers();
            this.baseMagicNumber = realBaseMagic;
            this.isMagicNumberModified = this.magicNumber != this.baseMagicNumber;
        }
    }
    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_BLOCK);
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}
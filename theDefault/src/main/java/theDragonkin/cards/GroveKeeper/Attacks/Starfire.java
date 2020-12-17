package theDragonkin.cards.GroveKeeper.Attacks;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.cards.GroveKeeper.AbstractGroveKeeperCard;
import theDragonkin.characters.TheGroveKeeper;
import theDragonkin.powers.GroveKeeper.AlignmentPower;
import theDragonkin.powers.GroveKeeper.Moonstruck;

import static theDragonkin.DefaultMod.makeCardPath;

public class Starfire extends AbstractGroveKeeperCard {


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Starfire.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("Strike.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;  //   since they don't change much.
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;       //
    public static final AbstractCard.CardColor COLOR = TheGroveKeeper.Enums.GroveKeeper_Forest_Color;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 2;  // COST = 1
    private static final int UPGRADED_COST = 0; // UPGRADED_COST = 1

    private static final int DAMAGE = 18;    // DAMAGE = 6
    private static final int UPGRADE_PLUS_DMG = 5;  // UPGRADE_PLUS_DMG = 4

    // /STAT DECLARATION/


    public Starfire(){
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = 2;
        this.tags.add(CustomTags.Lunar);
        this.setOrbTexture(DefaultMod.Lunar_SMALL_ORB,DefaultMod.Lunar_LARGE_ORB);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.player.hasPower(AlignmentPower.POWER_ID) && ((TwoAmountPower)AbstractDungeon.player.getPower(AlignmentPower.POWER_ID)).amount2 >= 4) {
            addToBot(new DamageAction(m, new DamageInfo(p, damage*2)));
        } else {
            addToBot(new DamageAction(m, new DamageInfo(p, damage)));
        }
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (AbstractDungeon.player.hasPower(AlignmentPower.POWER_ID) && ((TwoAmountPower)AbstractDungeon.player.getPower(AlignmentPower.POWER_ID)).amount2 >= 4) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }
    public void calculateCardDamage(AbstractMonster mo) {
        if (AbstractDungeon.player.hasPower(AlignmentPower.POWER_ID) && ((TwoAmountPower)AbstractDungeon.player.getPower(AlignmentPower.POWER_ID)).amount2 >= 4) {
            int realBaseDamage = this.baseDamage;
            this.baseDamage = realBaseDamage * 2;
            magicNumber = baseMagicNumber;
            super.calculateCardDamage(mo);
            this.baseDamage = realBaseDamage;
            this.isDamageModified = this.damage != this.baseDamage;
        }
    }

    public void applyPowers() {
        if (AbstractDungeon.player.hasPower(AlignmentPower.POWER_ID) && ((TwoAmountPower)AbstractDungeon.player.getPower(AlignmentPower.POWER_ID)).amount2 >= 4) {
            int realBaseDamage = this.baseDamage;
            this.baseDamage = realBaseDamage * 2;
            magicNumber = baseMagicNumber;
            super.applyPowers();
            this.baseDamage = realBaseDamage;
            this.isDamageModified = this.damage != this.baseDamage;
        }
    }
    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}

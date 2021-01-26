package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.Dragonkin.DragonBreaths.AkatirnsDecreeEffect;

import static theDragonkin.DefaultMod.makeCardPath;

public class AkatirnsDecree extends AbstractHolyCard {


    public static final String ID = DefaultMod.makeID(AkatirnsDecree.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath("Attack.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;

    private static final int COST = 2;
    private static final int DAMAGE = 14;
    private static final int UPGRADE_PLUS_DMG = 4;

    // /STAT DECLARATION/

    public AkatirnsDecree() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        // Damage + Weak + Vuln + Remove own Debuffs.
        // Just type this.base and let intelliJ auto complete for you, or, go read up AbstractCard

        damage = baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = 2;
        tags.add(CustomTags.Dragon_Breath);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new TalkAction(true,cardStrings.EXTENDED_DESCRIPTION[1],(float) 0.5,(float) 2.0));
        addToBot(new ApplyPowerAction(p,p, new AkatirnsDecreeEffect(baseDamage,baseMagicNumber,this)));
    }
    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        int realBaseMagic = this.baseMagicNumber;
        int diviser = (int)AbstractDungeon.getCurrRoom().monsters.monsters.stream().filter(it -> !it.isDeadOrEscaped()).count();
        if (AbstractDungeon.getCurrRoom().monsters.monsters.stream().filter(it -> !it.isDeadOrEscaped()).count() > 1){
            isMultiDamage = true;
            this.baseDamage =  (int)Math.ceil(((float)realBaseDamage / diviser));
            this.baseMagicNumber = (int) Math.ceil(((float)realBaseMagic / diviser));
            super.calculateCardDamage(mo);
            this.baseDamage = realBaseDamage;
            this.isDamageModified = this.damage != this.baseDamage;
            this.baseMagicNumber = realBaseMagic;
            this.isMagicNumberModified = this.magicNumber != this.baseMagicNumber;
            rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
            initializeDescription();
        } else {
            isMultiDamage = false;
            super.calculateCardDamage(mo);
            this.baseDamage = realBaseDamage;
            this.isDamageModified = this.damage != this.baseDamage;
            this.baseMagicNumber = realBaseMagic;
            this.isMagicNumberModified = this.magicNumber != this.baseMagicNumber;
            rawDescription = cardStrings.DESCRIPTION;
            initializeDescription();
        }
    }
    @Override
    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        int realBaseMagic = this.baseMagicNumber;
        int diviser = (int)AbstractDungeon.getCurrRoom().monsters.monsters.stream().filter(it -> !it.isDeadOrEscaped()).count();
        if (AbstractDungeon.getCurrRoom().monsters.monsters.stream().filter(it -> !it.isDeadOrEscaped()).count() > 1){
            isMultiDamage = true;
            this.baseDamage =  (int)Math.ceil(((float)realBaseDamage / diviser));
            this.baseMagicNumber = (int) Math.ceil(((float)realBaseMagic / diviser));
            super.applyPowers();
            this.baseDamage = realBaseDamage;
            this.isDamageModified = this.damage != this.baseDamage;
            this.baseMagicNumber = realBaseMagic;
            this.isMagicNumberModified = this.magicNumber != this.baseMagicNumber;
            rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
            initializeDescription();
        } else {
            isMultiDamage = false;
            super.applyPowers();
            this.baseDamage = realBaseDamage;
            this.isDamageModified = this.damage != this.baseDamage;
            this.baseMagicNumber = realBaseMagic;
            this.isMagicNumberModified = this.magicNumber != this.baseMagicNumber;
            rawDescription = cardStrings.DESCRIPTION;
            initializeDescription();
        }
    }
    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(2);
            initializeDescription();
        }
    }
}
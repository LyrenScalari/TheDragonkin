package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.CustomTags;
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
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 1;
    private static final int UPGRADED_COST = 2;

    private static final int POTENCY = 6;
    private static final int UPGRADE_PLUS_POTENCY = 2;
    private static final int MAGIC = 4;
    private static final int UPGRADE_MAGIC = 1;

    public TwinBreath() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = POTENCY;
        baseMagicNumber = magicNumber = MAGIC;
        cardsToPreview = new CorrosiveBreath();
        tags.add(CustomTags.Dragon_Breath);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new TalkAction(true,cardStrings.EXTENDED_DESCRIPTION[2],(float) 0.5,(float) 2.0));
        addToBot(new ApplyPowerAction(p,p,new TwinBreathEffect(damage,magicNumber,this)));
        if (upgraded){
            cardsToPreview.upgrade();
            addToBot(new MakeTempCardInHandAction(cardsToPreview));
        } else addToBot(new MakeTempCardInHandAction(cardsToPreview));
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
            if (!upgraded) {
                rawDescription = cardStrings.DESCRIPTION;
            } else  rawDescription = cardStrings.UPGRADE_DESCRIPTION;
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
            if (!upgraded) {
                rawDescription = cardStrings.DESCRIPTION;
            } else  rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
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
package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.Dragonkin.DeepBreathPower;
import theDragonkin.powers.Dragonkin.DragonBreaths.AbstractDragonBreathPower;
import theDragonkin.powers.Dragonkin.Scorchpower;

import java.util.Iterator;

import static theDragonkin.DefaultMod.makeCardPath;

public class DeepBreath extends AbstractPrimalCard {

    public static final String ID = DefaultMod.makeID(DeepBreath.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;

    private static final int MAGIC = 3;

    public DeepBreath() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        block = baseBlock = 0;
        baseMagicNumber = magicNumber = MAGIC;
        exhaust = true;

    }
    public void applyPowers() {
        int count = 0;
        for (AbstractPower p : AbstractDungeon.player.powers){
            if (p instanceof AbstractDragonBreathPower){
                count++;
            }
        }
        this.baseBlock = count * this.magicNumber;
        super.applyPowers();
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.applyPowers();
        addToBot(new GainBlockAction(p,block));
        addToBot(new ApplyPowerAction(p,p,new DeepBreathPower(p,p,1)));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}
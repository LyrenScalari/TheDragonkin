package theDragonkin.cards.Gremory.Skills.Magic;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.cards.Gremory.AbstractMagicGremoryCard;
import theDragonkin.cards.Gremory.FollowUps.FollowUpIcicle;
import theDragonkin.characters.TheGremory;
import theDragonkin.powers.BurstingWardPower;
import theDragonkin.powers.ChillPower;

import static theDragonkin.DefaultMod.makeCardPath;

public class IceBlock extends AbstractMagicGremoryCard implements BranchingUpgradesCard {
    public static final String ID = DefaultMod.makeID(IceBlock.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheGremory.Enums.Gremory_Purple_Color;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 2;

    public IceBlock() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(CustomTags.Ice);
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[5] + cardStrings.DESCRIPTION;
        initializeDescription();
        isMultiDamage = true;
        MagDamage = baseMagDamage = 35;
        magicNumber = baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.timesUpgraded % 2 == 0) {
            addToBot(new GainBlockAction(p,MagDamage));
            addToBot(new ApplyPowerAction(p,p,new EnergizedPower(p,-p.energy.energyMaster)));
            addToBot(new ApplyPowerAction(p,p,new BlurPower(p,magicNumber)));

        } else {
            addToBot(new GainBlockAction(p,MagDamage));
            addToBot(new ApplyPowerAction(p,p,new BurstingWardPower(p,p,MagDamage)));
        }
        super.use(p, m);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            if (isBranchUpgrade()) {
                branchUpgrade();
            } else {
                baseUpgrade();
            }
        }
    }

    public void baseUpgrade() {
        name = cardStrings.EXTENDED_DESCRIPTION[0];
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[5] + UPGRADE_DESCRIPTION;
        upgradeMagicNumber(1);
        MagDamageUpgraded = true;
        initializeDescription();
    }

    public void branchUpgrade() {
        name = cardStrings.EXTENDED_DESCRIPTION[2];
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[4] + cardStrings.EXTENDED_DESCRIPTION[3];
        tags.remove(CustomTags.Ice);
        tags.add(CustomTags.Fire);
        baseMagDamage -= 20;
        MagDamageUpgraded = true;
        initializeDescription();

    }
}

package theDragonkin.cards.Gremory.Skills.Magic;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.cards.Gremory.AbstractMagicGremoryCard;
import theDragonkin.characters.TheGremory;
import theDragonkin.powers.Gremory.BurstingWardPower;

import java.util.ArrayList;
import java.util.List;

import static theDragonkin.DefaultMod.makeCardPath;

public class BurstingGlyph extends AbstractMagicGremoryCard implements BranchingUpgradesCard {
    public static final String ID = DefaultMod.makeID(BurstingGlyph.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheGremory.Enums.Gremory_Purple_Color;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 2;
    private static ArrayList<TooltipInfo> TrapTooltip;
    @Override
    public List<TooltipInfo> getCustomTooltipsTop() {
        return TrapTooltip;
    }
    public BurstingGlyph() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(CustomTags.Fire);
        TrapTooltip = new ArrayList<>();
        TrapTooltip.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Fire"), BaseMod.getKeywordDescription("thedragonkin:Fire")));
        TrapTooltip.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Hot_Streak"), BaseMod.getKeywordDescription("thedragonkin:Hot_Streak")));
        setOrbTexture(DefaultMod.Fire_SMALL_ORB,DefaultMod.Fire_LARGE_ORB);
        getCustomTooltips();
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[4] + cardStrings.DESCRIPTION;
        initializeDescription();
        isMultiDamage = true;
        MagDamage = baseMagDamage = 15;
        magicNumber = baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.name.equals(cardStrings.NAME) || this.name.equals(cardStrings.EXTENDED_DESCRIPTION[0])) {
            addToBot(new GainBlockAction(p,MagDamage));
            addToBot(new ApplyPowerAction(p,p,new BurstingWardPower(p,p,MagDamage)));
        } else {
            addToBot(new GainBlockAction(p,MagDamage));
            addToBot(new ApplyPowerAction(p,p,new EnergizedPower(p,-p.energy.energyMaster)));
            addToBot(new ApplyPowerAction(p,p,new BlurPower(p,magicNumber)));
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
        this.rawDescription = UPGRADE_DESCRIPTION;
        upgradeMagicNumber(1);
        MagDamageUpgraded = true;
        baseMagDamage += 10;
        initializeDescription();
    }

    public void branchUpgrade() {
        name = cardStrings.EXTENDED_DESCRIPTION[2];
        this.rawDescription =cardStrings.EXTENDED_DESCRIPTION[3];
        tags.remove(CustomTags.Fire);
        tags.add(CustomTags.Ice);
        setOrbTexture(DefaultMod.Ice_SMALL_ORB,DefaultMod.Ice_LARGE_ORB);
        TrapTooltip.clear();
        TrapTooltip.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Ice"), BaseMod.getKeywordDescription("thedragonkin:Ice")));
        TrapTooltip.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Chill"), BaseMod.getKeywordDescription("thedragonkin:Chill")));
        TrapTooltip.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Freeze"), BaseMod.getKeywordDescription("thedragonkin:Freeze")));
        getCustomTooltips();
        baseMagDamage += 30;
        MagDamageUpgraded = true;
        initializeDescription();

    }
}


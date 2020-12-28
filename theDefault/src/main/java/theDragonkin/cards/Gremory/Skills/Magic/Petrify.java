package theDragonkin.cards.Gremory.Skills.Magic;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.cards.Gremory.AbstractMagicGremoryCard;
import theDragonkin.characters.TheGremory;
import theDragonkin.powers.Gremory.ChargedUp;
import theDragonkin.powers.Gremory.JoltedPower;

import java.util.ArrayList;
import java.util.List;

import static theDragonkin.DefaultMod.makeCardPath;

public class Petrify extends AbstractMagicGremoryCard implements BranchingUpgradesCard {
    public static final String ID = DefaultMod.makeID(Petrify.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheGremory.Enums.Gremory_Purple_Color;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static ArrayList<TooltipInfo> TrapTooltip;
    @Override
    public List<TooltipInfo> getCustomTooltipsTop() {
        return TrapTooltip;
    }
    public Petrify() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(CustomTags.Thunder);
        TrapTooltip = new ArrayList<>();
        setOrbTexture(DefaultMod.Thunder_SMALL_ORB,DefaultMod.Thunder_LARGE_ORB);
        TrapTooltip.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Thunder"), BaseMod.getKeywordDescription("thedragonkin:Thunder")));
        TrapTooltip.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Jolted"), BaseMod.getKeywordDescription("thedragonkin:Jolted")));
        getCustomTooltips();
        this.exhaust = true;
        this.rawDescription =  cardStrings.DESCRIPTION;
        initializeDescription();
        magicNumber = baseMagicNumber = 2;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.name.equals(cardStrings.NAME) || this.name.equals(cardStrings.EXTENDED_DESCRIPTION[0])){
            if (!m.hasPower(JoltedPower.POWER_ID)){
                addToBot(new StunMonsterAction(m,p));
            }else {
                addToBot(new ApplyPowerAction(p,p,new ChargedUp(p,magicNumber)));
            }

        }
        else {
            addToBot(new ApplyPowerAction(p,p,new DexterityPower(p,magicNumber)));
            addToBot(new ApplyPowerAction(p,p,new LoseDexterityPower(p,magicNumber)));
            if (this.cost < 1){
                addToBot(new GainEnergyAction(2));
            }
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
        initializeDescription();
    }

    public void branchUpgrade() {
        name = cardStrings.EXTENDED_DESCRIPTION[2];
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[3];
        tags.remove(CustomTags.Thunder);
        this.target = CardTarget.SELF;
        this.exhaust = false;
        upgradeMagicNumber(1);
        tags.add(CustomTags.Wind);
        setOrbTexture(DefaultMod.Wind_SMALL_ORB,DefaultMod.Wind_LARGE_ORB);
        TrapTooltip.clear();
        TrapTooltip.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Wind"), BaseMod.getKeywordDescription("thedragonkin:Wind")));
        TrapTooltip.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Tailwind"), BaseMod.getKeywordDescription("thedragonkin:Tailwind")));
        getCustomTooltips();
        MagDamageUpgraded = true;
        initializeDescription();
    }
}


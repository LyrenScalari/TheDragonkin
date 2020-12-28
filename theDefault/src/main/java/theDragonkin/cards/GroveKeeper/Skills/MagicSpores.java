package theDragonkin.cards.GroveKeeper.Skills;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.cards.GroveKeeper.AbstractGroveKeeperCard;
import theDragonkin.characters.TheGroveKeeper;
import theDragonkin.orbs.AbstractGrovekeeperOrb;
import theDragonkin.powers.GroveKeeper.EnergyShieldPower;
import theDragonkin.powers.GroveKeeper.NatrualSpikesPower;
import theDragonkin.powers.GroveKeeper.NaturePower;

import java.util.ArrayList;

import static theDragonkin.DefaultMod.makeCardPath;

public class MagicSpores extends AbstractGroveKeeperCard {


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(MagicSpores.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ALL;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    public static final AbstractCard.CardColor COLOR = TheGroveKeeper.Enums.GroveKeeper_Forest_Color;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    public static ArrayList<String> enemyeffects = new ArrayList<>();
    public static ArrayList<String> playereffects = new ArrayList<>();
    public String DebufftoApply;
    public String BufftoApply;

    // /STAT DECLARATION/


    public MagicSpores() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        magicNumber = baseMagicNumber = 4;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 1;
        this.tags.add(CustomTags.Neutral);
        enemyeffects.add(PoisonPower.POWER_ID);
        enemyeffects.add(WeakPower.POWER_ID);
        enemyeffects.add(VulnerablePower.POWER_ID);
        playereffects.add(NatrualSpikesPower.POWER_ID);
        playereffects.add(EnergyShieldPower.POWER_ID);
        playereffects.add(VigorPower.POWER_ID);

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        BufftoApply = playereffects.get((AbstractDungeon.cardRandomRng.random(playereffects.size() - 1)));
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            DebufftoApply = enemyeffects.get((AbstractDungeon.cardRandomRng.random(enemyeffects.size() - 1)));
            if (DebufftoApply.equals(PoisonPower.POWER_ID)) {
                addToBot(new ApplyPowerAction(mo, p, new PoisonPower(mo, p, magicNumber)));
            } else if (DebufftoApply.equals(WeakPower.POWER_ID)) {
                addToBot(new ApplyPowerAction(mo, p, new WeakPower(mo, magicNumber, false)));
            } else if (DebufftoApply.equals(VulnerablePower.POWER_ID)) {
                addToBot(new ApplyPowerAction(mo, p, new VulnerablePower(mo, magicNumber, false)));
            }
        }
        if (BufftoApply.equals(NatrualSpikesPower.POWER_ID)) {
            addToBot(new ApplyPowerAction(p, p, new NatrualSpikesPower(p, p, magicNumber, 1)));
        } else if (BufftoApply.equals(WeakPower.POWER_ID)) {
            addToBot(new ApplyPowerAction(p, p, new EnergyShieldPower(p, p, magicNumber)));
        } else if (BufftoApply.equals(VulnerablePower.POWER_ID)) {
            addToBot(new ApplyPowerAction(p, p, new VigorPower(p, magicNumber)));
        }
        if (upgraded){
            BufftoApply = playereffects.get((AbstractDungeon.cardRandomRng.random(playereffects.size() - 1)));
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                DebufftoApply = enemyeffects.get((AbstractDungeon.cardRandomRng.random(enemyeffects.size() - 1)));
                if (DebufftoApply.equals(PoisonPower.POWER_ID)) {
                    addToBot(new ApplyPowerAction(mo, p, new PoisonPower(mo, p, magicNumber)));
                } else if (DebufftoApply.equals(WeakPower.POWER_ID)) {
                    addToBot(new ApplyPowerAction(mo, p, new WeakPower(mo, magicNumber, false)));
                } else if (DebufftoApply.equals(VulnerablePower.POWER_ID)) {
                    addToBot(new ApplyPowerAction(mo, p, new VulnerablePower(mo, magicNumber, false)));
                }
            }
            if (BufftoApply.equals(NatrualSpikesPower.POWER_ID)) {
                addToBot(new ApplyPowerAction(p, p, new NatrualSpikesPower(p, p, magicNumber, 1)));
            } else if (BufftoApply.equals(WeakPower.POWER_ID)) {
                addToBot(new ApplyPowerAction(p, p, new EnergyShieldPower(p, p, magicNumber)));
            } else if (BufftoApply.equals(VulnerablePower.POWER_ID)) {
                addToBot(new ApplyPowerAction(p, p, new VigorPower(p, magicNumber)));
            }
        }
    }


    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            upgradeMagicNumber(1);
            upgradeDefaultSecondMagicNumber(1);
            initializeDescription();
        }
    }
}

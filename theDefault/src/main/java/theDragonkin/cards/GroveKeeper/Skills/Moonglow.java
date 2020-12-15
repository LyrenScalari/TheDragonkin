package theDragonkin.cards.GroveKeeper.Skills;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.cards.GroveKeeper.AbstractGroveKeeperCard;
import theDragonkin.characters.TheGroveKeeper;
import theDragonkin.powers.GroveKeeper.AlignmentPower;

import static theDragonkin.DefaultMod.makeCardPath;

public class Moonglow extends AbstractGroveKeeperCard {


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Moonglow.class.getSimpleName());
    public static final String IMG = makeCardPath("Defend.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheGroveKeeper.Enums.GroveKeeper_Forest_Color;

    private static final int COST = 1;
    private static final int BLOCK = 6;
    private static final int UPGRADE_PLUS_BLOCK = 3;


    // /STAT DECLARATION/


    public Moonglow() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        magicNumber = baseMagicNumber = 2;
        this.tags.add(CustomTags.Lunar);
        this.setOrbTexture(DefaultMod.Lunar_SMALL_ORB,DefaultMod.Lunar_LARGE_ORB);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        if (p.hasPower(AlignmentPower.POWER_ID)) {
            if (((TwoAmountPower)p.getPower(AlignmentPower.POWER_ID)).amount2 >= 3) {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
            }
            if (((TwoAmountPower)p.getPower(AlignmentPower.POWER_ID)).amount2 >= 2) {
                addToBot(new ApplyPowerAction(p,p,new DexterityPower(p,magicNumber)));
                addToBot(new ApplyPowerAction(p,p,new LoseDexterityPower(p,magicNumber)));
            }
        }
    }
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (AbstractDungeon.player.hasPower(AlignmentPower.POWER_ID) && ((TwoAmountPower)AbstractDungeon.player.getPower(AlignmentPower.POWER_ID)).amount2 >= 4) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }
    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}

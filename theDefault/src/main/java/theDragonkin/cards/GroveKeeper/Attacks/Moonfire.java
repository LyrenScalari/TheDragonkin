package theDragonkin.cards.GroveKeeper.Attacks;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.CustomTags;
import theDragonkin.DragonkinMod;
import theDragonkin.cards.GroveKeeper.AbstractGroveKeeperCard;
import theDragonkin.characters.TheGroveKeeper;
import theDragonkin.powers.GroveKeeper.AlignmentPower;
import theDragonkin.powers.GroveKeeper.Moonstruck;

import static theDragonkin.DragonkinMod.makeCardPath;

public class Moonfire extends AbstractGroveKeeperCard {


    // TEXT DECLARATION

    public static final String ID = DragonkinMod.makeID(Moonfire.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("FlameweaverStrike.png");// "public static final String IMG = makeCardPath("FlameweaverStrike.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.BASIC; //  Up to you, I like auto-complete on these
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;  //   since they don't change much.
    private static final AbstractCard.CardType TYPE = CardType.SKILL;       //
    public static final AbstractCard.CardColor COLOR = TheGroveKeeper.Enums.GroveKeeper_Forest_Color;

    private static final int COST = 1;  // COST = 1
    private static final int UPGRADED_COST = 0; // UPGRADED_COST = 1

    private static final int DAMAGE = 5;    // DAMAGE = 6
    private static final int UPGRADE_PLUS_DMG = 3;  // UPGRADE_PLUS_DMG = 4

    // /STAT DECLARATION/


    public Moonfire(){
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = 2;
        this.tags.add(CustomTags.Lunar);
        this.setOrbTexture(DragonkinMod.Lunar_SMALL_ORB, DragonkinMod.Lunar_LARGE_ORB);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m,p,new Moonstruck(m,p,magicNumber),magicNumber));
        if (p.hasPower(AlignmentPower.POWER_ID)) {
            if (((TwoAmountPower)p.getPower(AlignmentPower.POWER_ID)).amount2 >= 4) {
                addToBot(new GainEnergyAction(1));
            }
        }
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (AbstractDungeon.player.hasPower(AlignmentPower.POWER_ID) && ((TwoAmountPower)AbstractDungeon.player.getPower(AlignmentPower.POWER_ID)).amount2 >= 4) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }
    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}

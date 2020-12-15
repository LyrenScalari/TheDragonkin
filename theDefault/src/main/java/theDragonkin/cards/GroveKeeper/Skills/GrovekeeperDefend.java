package theDragonkin.cards.GroveKeeper.Skills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.cards.GroveKeeper.AbstractGroveKeeperCard;
import theDragonkin.characters.TheGremory;
import theDragonkin.characters.TheGroveKeeper;
import theDragonkin.powers.GroveKeeper.AlignmentPower;

import static theDragonkin.DefaultMod.makeCardPath;

public class GrovekeeperDefend extends AbstractGroveKeeperCard {


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(GrovekeeperDefend.class.getSimpleName());
    public static final String IMG = makeCardPath("Defend.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheGroveKeeper.Enums.GroveKeeper_Forest_Color;

    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int UPGRADE_PLUS_BLOCK = 3;


    // /STAT DECLARATION/


    public GrovekeeperDefend() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        magicNumber = baseMagicNumber = 1;
        this.tags.add(CustomTags.Neutral);
        this.tags.add(CardTags.STARTER_DEFEND); //Tag your strike, defend and form (Wraith form, Demon form, Echo form, etc.) cards so that they function correctly.
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        if (p.hasPower(AlignmentPower.POWER_ID)) {
            if (p.getPower(AlignmentPower.POWER_ID).amount >= 2) {
                for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    addToBot(new ApplyPowerAction(mo, p, new WeakPower(mo, magicNumber, false)));
                }
            }
        }
    }
    public void triggerOnGlowCheck() {
        if (AbstractDungeon.player.hasPower(AlignmentPower.POWER_ID) && AbstractDungeon.player.getPower(AlignmentPower.POWER_ID).amount >= 2) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
            this.setOrbTexture(DefaultMod.Solar_SMALL_ORB,DefaultMod.Solar_LARGE_ORB);
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
            this.setOrbTexture(DefaultMod.Neutral_SMALL_ORB,DefaultMod.Neutral_LARGE_ORB);
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

package theDragonkin.cards.GroveKeeper.Powers;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.cards.GroveKeeper.AbstractGroveKeeperCard;
import theDragonkin.cards.GroveKeeper.Attacks.GrovekeeperStrike;
import theDragonkin.characters.TheGroveKeeper;
import theDragonkin.powers.GroveKeeper.AlignmentPower;
import theDragonkin.powers.GroveKeeper.SwarmPower;

import static theDragonkin.DefaultMod.makeCardPath;

public class StingingSwarm extends AbstractGroveKeeperCard {


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(StingingSwarm.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Power.png");// "public static final String IMG = makeCardPath("Strike.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.POWER;       //
    public static final CardColor COLOR = TheGroveKeeper.Enums.GroveKeeper_Forest_Color;

    private static final int COST = 2;  // COST = 1
    private static final int UPGRADED_COST = 1; // UPGRADED_COST = 1

    private static final int DAMAGE = 5;    // DAMAGE = 6
    private static final int UPGRADE_PLUS_DMG = 3;  // UPGRADE_PLUS_DMG = 4

    // /STAT DECLARATION/


    public StingingSwarm(){
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = 2;
        this.tags.add(CustomTags.Neutral);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p,p,new SwarmPower(p,p,magicNumber)));
    }

    public void triggerOnGlowCheck() {
}
    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}

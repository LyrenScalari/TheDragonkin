package theDragonkin.cards.GroveKeeper.Powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.cards.Gremory.Choices.FlowersAmbition;
import theDragonkin.cards.Gremory.Choices.ImmaculateSnow;
import theDragonkin.cards.Gremory.Choices.MoonsMarch;
import theDragonkin.cards.Gremory.Choices.WindsSong;
import theDragonkin.cards.GroveKeeper.AbstractGroveKeeperCard;
import theDragonkin.cards.GroveKeeper.Choices.EverbloomInvig;
import theDragonkin.cards.GroveKeeper.Choices.EverbloomPrimal;
import theDragonkin.cards.GroveKeeper.Choices.EverbloomThorn;
import theDragonkin.cards.GroveKeeper.Choices.EverbloomToxic;
import theDragonkin.characters.TheGroveKeeper;
import theDragonkin.powers.GroveKeeper.NaturePower;
import theDragonkin.powers.GroveKeeper.SwarmPower;

import java.util.ArrayList;

import static theDragonkin.DefaultMod.makeCardPath;

public class Everbloom extends AbstractGroveKeeperCard {


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Everbloom.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Power.png");// "public static final String IMG = makeCardPath("Strike.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.POWER;       //
    public static final CardColor COLOR = TheGroveKeeper.Enums.GroveKeeper_Forest_Color;

    private static final int COST = 2;  // COST = 1
    private static final int UPGRADED_COST = 1; // UPGRADED_COST = 1

    private static final int DAMAGE = 5;    // DAMAGE = 6
    private static final int UPGRADE_PLUS_DMG = 3;  // UPGRADE_PLUS_DMG = 4

    // /STAT DECLARATION/


    public Everbloom(){
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = 3;
        this.tags.add(CustomTags.Neutral);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> stanceChoices = new ArrayList();
        stanceChoices.add(new EverbloomInvig());
        stanceChoices.add(new EverbloomThorn());
        stanceChoices.add(new EverbloomPrimal());
        stanceChoices.add(new EverbloomToxic());
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p,p,new NaturePower(p,p,magicNumber)));
        addToBot(new ChooseOneAction(stanceChoices));
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
package theDragonkin.cards.GroveKeeper.Attacks;

import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.actions.CustomChooseOne;
import theDragonkin.cards.GroveKeeper.AbstractChooseOneCard;
import theDragonkin.cards.GroveKeeper.Choices.Starlord;
import theDragonkin.cards.GroveKeeper.Choices.StellarDrift;
import theDragonkin.characters.TheGroveKeeper;

import java.util.ArrayList;

import static theDragonkin.DefaultMod.makeCardPath;

public class Starfall extends AbstractChooseOneCard {
    public static final String ID = DefaultMod.makeID(Starfall .class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;
    public static final AbstractCard.CardColor COLOR = TheGroveKeeper.Enums.GroveKeeper_Forest_Color;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 2;  // COST = 1
    private static final int UPGRADED_COST = 0; // UPGRADED_COST = 1
    private static final int DAMAGE = 10;    // DAMAGE = 6
    private static final int UPGRADE_PLUS_DMG = 4;  // UPGRADE_PLUS_DMG = 4
    private static final int DAMAGE2 = 22;
    public Starfall() {
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
        this.tags.add(CustomTags.Lunar);
        damage = baseDamage = DAMAGE;
        GrovekeeperSecondDamage = BaseGrovekeeperSecondDamage = DAMAGE2;
        this.setOrbTexture(DefaultMod.Lunar_SMALL_ORB,DefaultMod.Lunar_LARGE_ORB);
        isMultiDamage = true;

    }

    @Override
    public void upgrade() {
        upgradeGrovekeeperSecondDamage(UPGRADE_PLUS_DMG);
        upgradeDamage(UPGRADE_PLUS_DMG);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        ArrayList<AbstractCard> NextChoices = new ArrayList<>();
        NextChoices.add(new StellarDrift(multiDamage));
        NextChoices.add(new Starlord(GrovekeeperSecondDamage,abstractMonster));
        if (upgraded){
            for (AbstractCard c : NextChoices){
                c.upgrade();
            }
        }
        addToBot(new ChooseOneAction(NextChoices));
    }
}

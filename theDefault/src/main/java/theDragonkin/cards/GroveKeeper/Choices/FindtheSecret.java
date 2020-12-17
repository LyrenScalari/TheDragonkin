package theDragonkin.cards.GroveKeeper.Choices;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.cards.GroveKeeper.AbstractChooseOneCard;
import theDragonkin.cards.GroveKeeper.AbstractGroveKeeperCard;
import theDragonkin.characters.TheGroveKeeper;

import static theDragonkin.DefaultMod.makeCardPath;
@AutoAdd.Ignore
public class FindtheSecret extends AbstractGroveKeeperCard {
    public static final String ID = DefaultMod.makeID(FindtheSecret.class.getSimpleName());
    public static final String IMG = makeCardPath("Power.png");


    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.SPECIAL;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = CardType.POWER;
    public static final AbstractCard.CardColor COLOR = TheGroveKeeper.Enums.GroveKeeper_Forest_Color;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int DAMAGE = 1;    // DAMAGE = 6
    private static final int UPGRADE_PLUS_DMG = 0;  // UPGRADE_PLUS_DMG = 4
    public FindtheSecret() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = DAMAGE;
        purgeOnUse = true;
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0] +
        this.tags.add(CustomTags.Neutral);
    }
    public FindtheSecret(int magic) {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(CustomTags.Neutral);
        magicNumber = baseMagicNumber = magic;
    }
    public void onChoseThisOption() {
        AbstractChooseOneCard.lastchoice.addToBottom(new FindtheSecret());
        AbstractChooseOneCard.Roadsuntraveled.addToBottom(new KickintheDoor());
        AbstractChooseOneCard.Roadsuntraveled.addToBottom(new LoottheRoom());
        AbstractChooseOneCard.lastchoices.addToBottom(new KickintheDoor());
        AbstractChooseOneCard.lastchoices.addToBottom(new LoottheRoom());
        AbstractChooseOneCard.lastchoice.addToBottom(new FindtheSecret());
        addToTop(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new StrengthPower(AbstractDungeon.player,magicNumber)));
        addToTop(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new DexterityPower(AbstractDungeon.player,magicNumber)));
        addToTop(new WaitAction((float) .02));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new StrengthPower(AbstractDungeon.player,magicNumber)));
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new DexterityPower(AbstractDungeon.player,magicNumber)));
    }

    @Override
    public void upgrade() {
        upgradeDamage(UPGRADE_PLUS_DMG);
    }
}

package theDragonkin.cards.Gremory.Choices;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.DefaultMod;
import theDragonkin.cards.Gremory.AbstractMagicGremoryCard;
import theDragonkin.characters.TheGremory;

import static theDragonkin.DefaultMod.makeCardPath;
@AutoAdd.Ignore
public class ImmaculateSnow extends AbstractMagicGremoryCard {
    public static final String ID = DefaultMod.makeID(ImmaculateSnow.class.getSimpleName());
    public static final String IMG = makeCardPath("Power.png");


    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheGremory.Enums.Gremory_Purple_Color;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = -2;

    public ImmaculateSnow() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        setBackgroundTexture(DefaultMod.WINTER_SMALL_POWER_FRAME,DefaultMod.WINTER_LARGE_POWER_FRAME);
        setOrbTexture(DefaultMod.WINTER_SMALL_ORB,DefaultMod.WINTER_LARGE_ORB);
    }

    public void onChoseThisOption() {
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,
                new theDragonkin.powers.Gremory.ImmaculateSnow(AbstractDungeon.player,AbstractDungeon.player)));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }
    @Override
    public void upgrade() {
    }
}
package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.DragonkinMod;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.Dragonkin.UnbrokenPower;
import theDragonkin.util.AbstractDragonMark;

import static theDragonkin.DragonkinMod.makeCardPath;

public class Unbroken extends AbstractDragonkinCard {

    public static final String ID = DragonkinMod.makeID(Unbroken.class.getSimpleName());
    public static final String IMG = makeCardPath("AcidArmor.png");


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;

    private static final int POTENCY = 3;
    private static final int UPGRADE_PLUS_DMG = 1;
    private static final int MAGIC = 10;
    private static final int UPGRADE_MAGIC = 10;

    public Unbroken() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = POTENCY;
        block = baseBlock = POTENCY;
        heal = baseHeal = POTENCY;
        baseMagicNumber = magicNumber = MAGIC;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new UnbrokenPower(p,magicNumber,magicNumber+10)));
        super.use(p,m);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(5);
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
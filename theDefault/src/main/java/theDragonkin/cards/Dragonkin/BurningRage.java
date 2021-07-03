package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import theDragonkin.DragonkinMod;
import theDragonkin.cards.Dragonkin.interfaces.ReciveDamageEffect;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.Dragonkin.FuryPower;
import theDragonkin.powers.Dragonkin.Scorchpower;

import static theDragonkin.DragonkinMod.makeCardPath;

public class BurningRage extends AbstractHolyCard implements ReciveDamageEffect {

    public static final String ID = DragonkinMod.makeID(BurningRage.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;

    private static final int DAMAGE = 14;
    private static final int UPGRADE_PLUS_DMG = 2;
    private static final int MAGIC = 6;
    private static final int UPGRADE_MAGIC = 0;

    public BurningRage() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = 2;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 1;
        isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(p,p,new DexterityPower(p,magicNumber)));
        super.use(p,m);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDefaultSecondMagicNumber(1);
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }

    @Override
    public void onReciveDamage(int damage) {
        if (AbstractDungeon.player.hand.contains(this)) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, defaultSecondMagicNumber)));
        }
    }
}
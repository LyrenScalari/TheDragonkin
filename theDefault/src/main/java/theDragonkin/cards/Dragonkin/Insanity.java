package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ConfusionPower;
import com.megacrit.cardcrawl.powers.DrawPower;
import com.megacrit.cardcrawl.powers.watcher.EnergyDownPower;
import theDragonkin.DefaultMod;

import static theDragonkin.DefaultMod.makeCardPath;

public class Insanity extends AbstractDragonkinCard {

    public static final String ID = DefaultMod.makeID(Insanity.class.getSimpleName());
    public static final String IMG = makeCardPath("Insanity.png");


    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.CURSE;
    public static final CardColor COLOR = CardColor.CURSE;

    private static final int COST = -2;
    private static final int UPGRADED_COST = -2;

    private static final int POTENCY = 0;
    private static final int UPGRADE_PLUS_POTENCY = 0;
    private static final int MAGIC = 0;
    private static final int UPGRADE_MAGIC = 0;

    public Insanity() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = POTENCY;
        block = baseBlock = POTENCY;
        heal = baseHeal = POTENCY;
        baseMagicNumber = magicNumber = MAGIC;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void triggerOnExhaust() {
        this.addToBot(new MakeTempCardInHandAction(this.makeCopy()));
    }

    @Override
    public void triggerWhenDrawn() {
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new ConfusionPower(AbstractDungeon.player)));
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new EnergyDownPower(AbstractDungeon.player,1,false)));
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new DrawPower(AbstractDungeon.player,-1)));
    }

    @Override
    public void upgrade() {
    }
}
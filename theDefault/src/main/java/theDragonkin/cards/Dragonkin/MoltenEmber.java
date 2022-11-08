package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.DragonkinMod;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.Dragonkin.AshenArmorpower;
import theDragonkin.util.TriggerOnCycleEffect;

import static theDragonkin.DragonkinMod.makeCardPath;

public class MoltenEmber extends AbstractDragonkinCard implements TriggerOnCycleEffect {

    public static final String ID = DragonkinMod.makeID(MoltenEmber.class.getSimpleName());
    public static final String IMG = makeCardPath("MoltenEmber.png");


    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.COLORLESS;
    private static int realBlock = 8;
    private static final int COST = 0;
    private static final int UPGRADED_COST = 1;

    private static final int POTENCY = 8;
    private static final int UPGRADE_PLUS_DMG = 1;
    private static final int MAGIC = 4;
    private static final int UPGRADE_MAGIC = 2;

    public MoltenEmber() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        block = baseBlock = POTENCY;
        baseMagicNumber = magicNumber = MAGIC;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 2;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p,block));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                baseBlock = realBlock;
                isDone = true;
            }
        });
        super.use(p,m);
    }

    @Override
    public void triggerWhenDrawn(){
        if (DragonkinMod.StatusesCycledThisTurn < 1){
            baseBlock = realBlock;
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(2);
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }

    @Override
    public void TriggerOnCycle(AbstractCard ca) {
        baseBlock += magicNumber;
        addToBot(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player,magicNumber, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
    }
}
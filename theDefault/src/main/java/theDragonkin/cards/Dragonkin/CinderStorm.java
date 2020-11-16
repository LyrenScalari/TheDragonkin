package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheDefault;

import static theDragonkin.DefaultMod.makeCardPath;

public class CinderStorm extends AbstractDragonkinCard {

    public static final String ID = DefaultMod.makeID(CinderStorm.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int DAMAGE = 15;
    private static final int UPGRADE_PLUS_DMG = 5;
    private static final int MAGIC = 0;
    private static final int UPGRADE_MAGIC = 0;

    public CinderStorm() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
            for (AbstractCard c : AbstractDungeon.player.hand.group){
                if (c.type == CardType.STATUS) {
                    AbstractDungeon.actionManager.addToBottom(new
                            DamageRandomEnemyAction(new DamageInfo(p, baseDamage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
                    AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
                }
            }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeBaseCost(UPGRADED_COST);
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }
}
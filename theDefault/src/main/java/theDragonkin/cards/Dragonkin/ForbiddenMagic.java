package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.unique.SkewerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.actions.ForbiddenMagicAction;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.DivineConvictionpower;

import static theDragonkin.DefaultMod.makeCardPath;

public class ForbiddenMagic extends AbstractDragonkinCard {

    public static final String ID = DefaultMod.makeID(ForbiddenMagic.class.getSimpleName());
    public static final String IMG = makeCardPath("ForbiddenMagics.png");


    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;

    private static final int COST = -1;
    private static final int UPGRADED_COST = -1;

    private static final int POTENCY = 10;
    private static final int UPGRADE_PLUS_POTENCY = 4;
    private static final int MAGIC = 0;
    private static final int UPGRADE_MAGIC = 0;

    public ForbiddenMagic() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = POTENCY;
        block = baseBlock = POTENCY;
        heal = baseHeal = POTENCY;
        baseMagicNumber = magicNumber = MAGIC;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ForbiddenMagicAction(p, m, this.damage, this.damageTypeForTurn, this.freeToPlayOnce, this.energyOnUse));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_POTENCY);
            initializeDescription();
        }
    }
}
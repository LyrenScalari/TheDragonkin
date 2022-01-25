package theDragonkin.cards.Dragonkin;

import IconsAddon.cardmods.AddIconToDescriptionMod;
import IconsAddon.icons.FireIcon;
import IconsAddon.util.DamageModifierManager;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.DamageModifiers.FireDamage;
import theDragonkin.DragonkinMod;
import theDragonkin.actions.InfernoWardAction;
import theDragonkin.characters.TheDefault;

import static theDragonkin.DragonkinMod.makeCardPath;

public class CinderStorm extends AbstractPrimalCard {

    public static final String ID = DragonkinMod.makeID(CinderStorm.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;

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
        DamageModifierManager.addModifier(this, new FireDamage(true,true));
        CardModifierManager.addModifier(this,new AddIconToDescriptionMod(AddIconToDescriptionMod.DAMAGE, FireIcon.get()));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
            addToBot(new DamageAction(m,new DamageInfo(p,damage,damageTypeForTurn)));
            addToBot(new InfernoWardAction(10,()->new DamageRandomEnemyAction(new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE)));
        super.use(p,m);
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
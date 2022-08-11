package theDragonkin.cards.Dragonkin;


import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.damagemods.BindingHelper;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theDragonkin.DamageModifiers.DivineDamage;
import theDragonkin.DamageModifiers.FireDamage;
import theDragonkin.DragonkinMod;
import theDragonkin.actions.SmiteAction;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.Dragonkin.Scorchpower;

import java.util.ArrayList;

import static theDragonkin.DragonkinMod.makeCardPath;

public class ThreePointStrike extends AbstractDragonkinCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * WindWalkerDefend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DragonkinMod.makeID(ThreePointStrike.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;

    private static final int COST = 3;
    private static final int BLOCK = 18;
    private static final int UPGRADE_PLUS_BLOCK = 4;
    private static final int MAGIC = 2;
    private ArrayList<AbstractDamageModifier> Fire = new ArrayList<>();
    // /STAT DECLARATION/
    private final ArrayList<AbstractDamageModifier> normalDamage = new ArrayList<>();

    public ThreePointStrike() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        ThirdDamage = baseThirdDamage = secondDamage = baseSecondDamage = damage = baseDamage = 9;
        magicNumber = baseMagicNumber = MAGIC;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 1;
        Fire.add(new FireDamage(true, false));
        DamageModifierManager.addModifier(this, new FireDamage(true,false));
    }
    @Override
    public void applyPowers() {
        int d2;
        DamageModifierManager.removeModifier(this,Fire.get(0));
        super.applyPowers();
        d2 = damage;
        DamageModifierManager.addModifier(this,Fire.get(0));
        super.applyPowers();
        secondDamage = d2;
        isSecondDamageModified = secondDamage != baseSecondDamage;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int d2;
        DamageModifierManager.removeModifier(this,Fire.get(0));
        super.calculateCardDamage(mo);
        d2 = damage;
        DamageModifierManager.addModifier(this,Fire.get(0));
        super.calculateCardDamage(mo);
        secondDamage = d2;
        isSecondDamageModified = secondDamage != baseSecondDamage;
    }
    // Actions the card should do.

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SmiteAction(m, BindingHelper.makeInfo(normalDamage,p,damage, DamageInfo.DamageType.NORMAL)));
        addToBot(new DamageAction(m,BindingHelper.makeInfo(normalDamage,p,damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        addToBot(new DamageAction(m, BindingHelper.makeInfo(Fire,p,secondDamage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToBot(new ApplyPowerAction(m,p,new Scorchpower(m,p,magicNumber)));
        addToBot(new ApplyPowerAction(m,p,new WeakPower(m,magicNumber,false)));
        addToBot(new ApplyPowerAction(p,p,new VulnerablePower(p,1,false)));

    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
            upgradeSecondDamage(3);
            initializeDescription();
        }
    }
}
package theDragonkin.cards.Dragonkin;

import IconsAddon.damageModifiers.AbstractDamageModifier;
import IconsAddon.util.DamageModifierHelper;
import IconsAddon.util.DamageModifierManager;
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
    private ArrayList<AbstractDamageModifier> Divine=new ArrayList<>();
    // /STAT DECLARATION/
    private final ArrayList<AbstractDamageModifier> normalDamage = new ArrayList<>();

    public ThreePointStrike() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        ThirdDamage = baseThirdDamage = secondDamage = baseSecondDamage = damage = baseDamage = 9;
        magicNumber = baseMagicNumber = MAGIC;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 1;
        Fire.add(new FireDamage(true, false));
        Divine.add( new DivineDamage(true, false));
        DamageModifierManager.addModifier(this, new FireDamage(true,false));
        DamageModifierManager.addModifier(this, new DivineDamage(true,false));
    }
    @Override
    public void applyPowers() {
        int d2, d3;
        DamageModifierManager.removeModifier(this,Fire.get(0));
        DamageModifierManager.removeModifier(this,Divine.get(0));
        super.applyPowers();
        d3 = damage;
        DamageModifierManager.addModifier(this,Divine.get(0));
        super.applyPowers();
        d2 = damage;
        DamageModifierManager.removeModifier(this,Divine.get(0));
        DamageModifierManager.addModifier(this,Fire.get(0));
        super.applyPowers();
        DamageModifierManager.addModifier(this,Divine.get(0));
        secondDamage = d2;
        isSecondDamageModified = secondDamage != baseSecondDamage;
        ThirdDamage = d3;
        isThirdDamageModified = ThirdDamage != baseThirdDamage;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int d2, d3;
        DamageModifierManager.removeModifier(this,Fire.get(0));
        DamageModifierManager.removeModifier(this,Divine.get(0));
        super.calculateCardDamage(mo);
        d3 = damage;
        DamageModifierManager.addModifier(this,Divine.get(0));
        super.calculateCardDamage(mo);
        d2 = damage;
        DamageModifierManager.removeModifier(this,Divine.get(0));
        DamageModifierManager.addModifier(this,Fire.get(0));
        super.calculateCardDamage(mo);
        DamageModifierManager.addModifier(this,Divine.get(0));
        secondDamage = d2;
        isSecondDamageModified = secondDamage != baseSecondDamage;
        ThirdDamage = d3;
        isThirdDamageModified = ThirdDamage != baseThirdDamage;
    }
    // Actions the card should do.

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, DamageModifierHelper.makeBoundDamageInfo(Fire,p,ThirdDamage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToBot(new SmiteAction(m, DamageModifierHelper.makeBoundDamageInfo(Divine,p,secondDamage, DamageInfo.DamageType.NORMAL)));
        addToBot(new DamageAction(m,DamageModifierHelper.makeBoundDamageInfo(normalDamage,p,damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        addToBot(new ApplyPowerAction(m,p,new Scorchpower(m,p,magicNumber)));
        addToBot(new ApplyPowerAction(p,p,new VulnerablePower(p,1,false)));

    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
            upgradeSecondDamage(3);
            upgradeThirdDamage(3);
            initializeDescription();
        }
    }
}
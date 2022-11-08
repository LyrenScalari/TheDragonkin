package theDragonkin.cards.Dragonkin;


import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.damagemods.BindingHelper;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theDragonkin.CustomTags;
import theDragonkin.DamageModifiers.DivineDamage;
import theDragonkin.DamageModifiers.FireDamage;
import theDragonkin.DragonkinMod;
import theDragonkin.actions.SmiteAction;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.Dragonkin.Scorchpower;
import theDragonkin.util.TriggerOnCycleEffect;

import java.util.ArrayList;

import static theDragonkin.DragonkinMod.makeCardPath;

public class ThreePointStrike extends AbstractHolyCard implements TriggerOnCycleEffect {

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

    private static final int COST = 0;
    private static final int BLOCK = 18;
    private static final int UPGRADE_PLUS_BLOCK = 4;
    private static final int MAGIC = 3;
    private ArrayList<AbstractDamageModifier> Fire = new ArrayList<>();
    // /STAT DECLARATION/
    private final ArrayList<AbstractDamageModifier> normalDamage = new ArrayList<>();

    public ThreePointStrike() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        ThirdDamage = baseThirdDamage = secondDamage = baseSecondDamage = damage = baseDamage = 8;
        magicNumber = baseMagicNumber = MAGIC;
        Fire.add(new FireDamage(true, false));
        DamageModifierManager.addModifier(this, new FireDamage(true,false));
        tags.add(CustomTags.Smite);
    }

    // Actions the card should do.

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SmiteAction(m, new DamageInfo(p, damage, damageTypeForTurn)));
        addToBot(new ApplyPowerAction(p,p,new Scorchpower(p,p,magicNumber)));
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

    @Override
    public void TriggerOnCycle(AbstractCard ca) {
        if (AbstractDungeon.player.discardPile.contains(this)){
            this.addToBot(new DiscardToHandAction(this));
        }
    }
}
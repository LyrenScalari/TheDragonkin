package theDragonkin.cards.Dragonkin;

import basemod.AutoAdd;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AutoplayField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.Dragonkin.DragonBreaths.AbstractDragonBreathPower;

import static theDragonkin.DefaultMod.makeCardPath;
@AutoAdd.Ignore
public class DivineWind extends AbstractHolyCard {

    public static final String ID = DefaultMod.makeID(DivineWind.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int POTENCY = 10;
    private static final int UPGRADE_PLUS_POTENCY = 2;
    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC = 1;

    public DivineWind() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = POTENCY;
        baseMagicNumber = magicNumber = MAGIC;
        this.exhaust = true;
        AutoplayField.autoplay.set(this,true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new WhirlwindEffect()));
        addToBot(new VFXAction(new CleaveEffect()));
        addToBot(new DamageAllEnemiesAction(p,damage, DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE));
        addToBot(new DrawCardAction(p,magicNumber));
    }
    public void applyPowers() {
        if (magicNumber > 1){
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            super.applyPowers();
            this.initializeDescription();
        }
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_POTENCY);
            upgradeMagicNumber(UPGRADE_MAGIC);
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
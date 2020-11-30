package theDragonkin.cards.Gremory;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.vfx.CollectorStakeEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import com.megacrit.cardcrawl.vfx.scene.LightFlareLEffect;
import theDragonkin.actions.CureandHealAction;
import theDragonkin.util.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheGremory;

import static theDragonkin.DefaultMod.makeCardPath;

public class Mire extends AbstractMagicGremoryCard implements BranchingUpgradesCard {
    public static final String ID = DefaultMod.makeID(Mire.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheGremory.Enums.Gremory_Purple_Color;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static float animx;
    private static float animy;


    public Mire() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(CustomTags.Dark);
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[6] +  cardStrings.DESCRIPTION;
        initializeDescription();
        magicNumber = baseMagicNumber = 4;
        MagDamage = baseMagDamage = 8;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        animx = m.drawX;
        animy = m.drawY;
        if (this.timesUpgraded % 2 == 0) {
            addToBot(new DamageAction(m, new DamageInfo(p, MagDamage, DamageInfo.DamageType.NORMAL)));
            addToBot(new ApplyPowerAction(m,p,new PoisonPower(m,p,magicNumber)));
            addToBot(new VFXAction(new CollectorStakeEffect(animx,animy)));
        }
        else {
            addToBot(new DamageAction(m, new DamageInfo(p, MagDamage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            addToBot(new CureandHealAction(p,magicNumber));
            addToBot(new VFXAction(new LightFlareLEffect(animx,animy)));
            addToBot(new VFXAction(new LightningEffect(animx,animy)));
        }
        super.use(p, m);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            if (isBranchUpgrade()) {
                branchUpgrade();
            } else {
                baseUpgrade();
            }
        }
    }

    public void baseUpgrade() {
        name = cardStrings.EXTENDED_DESCRIPTION[0];
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[6] + UPGRADE_DESCRIPTION;
        baseMagDamage += 2;
        upgradeMagicNumber(1);
        initializeDescription();
    }

    public void branchUpgrade() {
        name = cardStrings.EXTENDED_DESCRIPTION[2];
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[5] + cardStrings.EXTENDED_DESCRIPTION[3];
        tags.remove(CustomTags.Dark);
        tags.add(CustomTags.Light);
        baseMagDamage += 5;
        upgradeMagicNumber(-3);
        ExhaustiveVariable.upgrade(this,3);
        this.upgradeBaseCost(2);
        MagDamageUpgraded = true;
        initializeDescription();

    }
}

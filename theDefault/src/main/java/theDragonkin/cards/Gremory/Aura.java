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
import theDragonkin.DefaultMod;
import theDragonkin.actions.CureandBlockAction;
import theDragonkin.actions.CureandHealAction;
import theDragonkin.characters.TheGremory;
import theDragonkin.util.CustomTags;

import static theDragonkin.DefaultMod.makeCardPath;

public class Aura extends AbstractMagicGremoryCard implements BranchingUpgradesCard {
    public static final String ID = DefaultMod.makeID(Mire.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheGremory.Enums.Gremory_Purple_Color;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 2;
    private static float animx;
    private static float animy;


    public Aura() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(CustomTags.Light);
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[6] +  cardStrings.DESCRIPTION;
        initializeDescription();
        ExhaustiveVariable.setBaseValue(this,2);
        magicNumber = baseMagicNumber = 1;
        MagDamage = baseMagDamage = 13;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        animx = m.drawX;
        animy = m.drawY;
        if (this.timesUpgraded % 2 == 0) {
            addToBot(new DamageAction(m, new DamageInfo(p, MagDamage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            addToBot(new VFXAction(new LightFlareLEffect(animx,animy)));
            addToBot(new VFXAction(new LightningEffect(animx,animy)));
            if (upgraded) {
                addToBot(new CureandHealAction(p, magicNumber));
            }else addToBot(new CureandBlockAction(p, magicNumber));
        }
        else {
            addToBot(new DamageAction(m, new DamageInfo(p, MagDamage, DamageInfo.DamageType.NORMAL)));
            addToBot(new ApplyPowerAction(m,p,new PoisonPower(m,p,magicNumber)));
            addToBot(new VFXAction(new CollectorStakeEffect(animx,animy)));
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
        initializeDescription();
    }

    public void branchUpgrade() {
        name = cardStrings.EXTENDED_DESCRIPTION[2];
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[5] + cardStrings.EXTENDED_DESCRIPTION[3];
        tags.remove(CustomTags.Light);
        tags.add(CustomTags.Dark);
        upgradeBaseCost(1);
        upgradeMagicNumber(4);
        baseMagDamage -= 5;
        MagDamageUpgraded = true;
        initializeDescription();

    }
}

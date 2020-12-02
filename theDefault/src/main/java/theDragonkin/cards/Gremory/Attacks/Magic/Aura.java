package theDragonkin.cards.Gremory.Attacks.Magic;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.vfx.CollectorStakeEffect;
import com.megacrit.cardcrawl.vfx.combat.LightFlareParticleEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import com.megacrit.cardcrawl.vfx.scene.LightFlareLEffect;
import theDragonkin.DefaultMod;
import theDragonkin.actions.CureandBlockAction;
import theDragonkin.actions.CureandHealAction;
import theDragonkin.cards.Gremory.AbstractMagicGremoryCard;
import theDragonkin.characters.TheGremory;
import theDragonkin.CustomTags;

import static theDragonkin.DefaultMod.makeCardPath;

public class Aura extends AbstractMagicGremoryCard implements BranchingUpgradesCard {
    public static final String ID = DefaultMod.makeID(Aura.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
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
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[5] +  cardStrings.DESCRIPTION;
        initializeDescription();
        ExhaustiveVariable.setBaseValue(this,2);
        magicNumber = baseMagicNumber = 1;
        isMultiDamage = true;
        MagDamage = baseMagDamage = 13;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.timesUpgraded % 2 == 0) {
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters){
                if (!mo.isDeadOrEscaped()) {
                    addToBot(new VFXAction(new LightFlareLEffect(mo.drawX, mo.drawY)));
                    addToBot(new VFXAction(new LightningEffect(mo.drawX, mo.drawY)));
                    addToBot(new VFXAction(new ShockWaveEffect(mo.drawX, mo.drawY, Color.YELLOW, ShockWaveEffect.ShockWaveType.ADDITIVE)));
                    addToBot(new VFXAction(new LightFlareParticleEffect(mo.drawX, mo.drawY, Color.SKY)));
                    addToBot(new VFXAction(new ShockWaveEffect(mo.drawX, animy, Color.GOLD, ShockWaveEffect.ShockWaveType.ADDITIVE)));
                    addToBot(new VFXAction(new LightFlareLEffect(mo.drawX,mo.drawY)));
                    addToBot(new VFXAction(new LightningEffect(mo.drawX, mo.drawY)));
                    addToBot(new VFXAction(new ShockWaveEffect(mo.drawX, mo.drawY, Color.YELLOW, ShockWaveEffect.ShockWaveType.ADDITIVE)));
                    addToBot(new VFXAction(new LightFlareParticleEffect(mo.drawX, mo.drawY, Color.SKY)));
                    addToBot(new VFXAction(new ShockWaveEffect(mo.drawX, mo.drawY, Color.GOLD, ShockWaveEffect.ShockWaveType.ADDITIVE)));
                }
            }
            addToBot(new DamageAllEnemiesAction(p,MagDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE));
            if (upgraded) {
                addToBot(new CureandHealAction(p, magicNumber));
            }else addToBot(new CureandBlockAction(p, magicNumber));
        }
        else {
            animx = m.drawX;
            animy = m.drawY;
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
        name = cardStrings.EXTENDED_DESCRIPTION[2];
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[5] + cardStrings.EXTENDED_DESCRIPTION[3];
        initializeDescription();
    }

    public void branchUpgrade() {
        name = cardStrings.EXTENDED_DESCRIPTION[0];
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[6] + UPGRADE_DESCRIPTION;
        tags.remove(CustomTags.Light);
        tags.add(CustomTags.Dark);
        this.target = CardTarget.ENEMY;
        this.isMultiDamage = false;
        ExhaustiveField.ExhaustiveFields.isExhaustiveUpgraded.set(this,false);
        upgradeBaseCost(1);
        upgradeMagicNumber(4);
        baseMagDamage -= 5;
        MagDamageUpgraded = true;
        initializeDescription();

    }
}

package theDragonkin.cards.Dragonkin;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.SmokePuffEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import com.megacrit.cardcrawl.vfx.stance.DivinityParticleEffect;
import theDragonkin.DragonkinMod;
import theDragonkin.cards.Dragonkin.interfaces.ReciveDamageEffect;
import theDragonkin.characters.TheDefault;

import static theDragonkin.DragonkinMod.makeCardPath;

public class BlessingofMight extends AbstractHolyCard implements ReciveDamageEffect {

    public static final String ID = DragonkinMod.makeID(BlessingofMight.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int UPGRADE_PLUS_POTENCY = 0;
    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC = 0;

    public BlessingofMight() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        damage = baseDamage = 3;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = 2;
    }
    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        float runningbaseDamage = realBaseDamage;
        for (AbstractPower p : AbstractDungeon.player.powers){
            runningbaseDamage =  p.atDamageGive(runningbaseDamage, DamageInfo.DamageType.NORMAL);
            runningbaseDamage =  p.atDamageReceive(runningbaseDamage, DamageInfo.DamageType.NORMAL);
            runningbaseDamage =  p.atDamageFinalGive(runningbaseDamage, DamageInfo.DamageType.NORMAL);
            runningbaseDamage =  p.atDamageFinalReceive(runningbaseDamage, DamageInfo.DamageType.NORMAL);
        }
        this.baseDamage = (int)runningbaseDamage;
        damage = baseDamage;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;

    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.effectList.add(new LightningEffect(p.drawX,p.drawY));
        addToBot(new DamageAction(p,new DamageInfo(p,damage, DamageInfo.DamageType.NORMAL)));
        addToBot(new VFXAction(new InflameEffect(AbstractDungeon.player)));
        addToBot(new ApplyPowerAction(p,p,new StrengthPower(p,magicNumber)));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(-1);
            initializeDescription();
        }
    }
    @Override
    public void onReciveDamage(int damage) {
        if (AbstractDungeon.player.hand.contains(this)) {
            if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID)) {
                CardCrawlGame.sound.play("POWER_MANTRA", 0.05F);
                AbstractDungeon.effectList.add(new DivinityParticleEffect());
                AbstractDungeon.effectList.add(new DivinityParticleEffect());
                addToBot(new VFXAction(new InflameEffect(AbstractDungeon.player)));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount)));
                addToBot(new VFXAction(new SmokePuffEffect(AbstractDungeon.player.drawX, AbstractDungeon.player.drawY)));
            }
            addToBot(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
        }
    }
}
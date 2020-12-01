package theDragonkin.cards.Gremory.Attacks.Magic;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;
import theDragonkin.cards.Gremory.AbstractMagicGremoryCard;
import theDragonkin.cards.Gremory.FollowUps.FollowUpExcaliburY;
import theDragonkin.util.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheGremory;
import theDragonkin.powers.ChargedUpDuration;
import theDragonkin.powers.JoltedPower;

import static theDragonkin.DefaultMod.makeCardPath;

public class Bolting extends AbstractMagicGremoryCard implements BranchingUpgradesCard {
    public static final String ID = DefaultMod.makeID(Bolting.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheGremory.Enums.Gremory_Purple_Color;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int MAGIC = 2;
    private static AbstractCard FollowUp;

    public Bolting() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(CustomTags.Thunder);
        magicNumber = baseMagicNumber = MAGIC;
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[5] +  cardStrings.DESCRIPTION;
        initializeDescription();
        MagDamage = baseMagDamage = 16;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.timesUpgraded % 2 == 0){
            if (m.hasPower(JoltedPower.POWER_ID)){
                addToBot(new ApplyPowerAction(p,p,new ChargedUpDuration(p,magicNumber,1)));
            }
            addToBot(new VFXAction(new ShockWaveEffect(m.drawX,m.drawY, Color.YELLOW, ShockWaveEffect.ShockWaveType.CHAOTIC)));
            addToBot(new VFXAction(new ShockWaveEffect(m.drawX,m.drawY, Color.GOLD, ShockWaveEffect.ShockWaveType.CHAOTIC)));
            addToBot(new DamageAction(m,new DamageInfo(p,MagDamage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
        }
        else {
            addToBot(new VFXAction(new WhirlwindEffect()));
            addToBot(new DamageAllEnemiesAction(p,multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HEAVY));
            addToBot(new VFXAction(new CleaveEffect()));
            if (Tailwind){
                addToBot(new DamageAllEnemiesAction(p,multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HEAVY));
                addToBot(new VFXAction(new CleaveEffect()));
                if (this.upgraded){
                    FollowUp = new FollowUpExcaliburY();
                    addToBot(new MakeTempCardInHandAction(FollowUp));
                    AllCards.addToBottom(FollowUp);
                }
            }
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
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[5] +UPGRADE_DESCRIPTION;
        upgradeMagicNumber(2);
        initializeDescription();
    }

    public void branchUpgrade() {
        name = cardStrings.EXTENDED_DESCRIPTION[2];
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[6] + cardStrings.EXTENDED_DESCRIPTION[3];
        tags.remove(CustomTags.Thunder);
        tags.add(CustomTags.Wind);
        baseMagDamage -= 8;
        this.isMultiDamage = true;
        MagDamageUpgraded = true;
        initializeDescription();
    }
}


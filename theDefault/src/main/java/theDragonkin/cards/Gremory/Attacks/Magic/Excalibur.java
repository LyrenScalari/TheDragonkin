package theDragonkin.cards.Gremory.Attacks.Magic;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
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
import theDragonkin.cards.Gremory.FollowUps.FollowUpExcalibur;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheGremory;
import theDragonkin.powers.Gremory.ChargedUpDuration;
import theDragonkin.powers.Gremory.JoltedPower;

import java.util.ArrayList;
import java.util.List;

import static theDragonkin.DefaultMod.makeCardPath;

public class Excalibur extends AbstractMagicGremoryCard implements BranchingUpgradesCard {
    public static final String ID = DefaultMod.makeID(Excalibur.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheGremory.Enums.Gremory_Purple_Color;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 2;
    private static final int MAGIC = 2;
    private static AbstractCard FollowUp;
    private int windcounter;
    private static ArrayList<TooltipInfo> TrapTooltip;
    @Override
    public List<TooltipInfo> getCustomTooltipsTop() {
        return TrapTooltip;
    }

    public Excalibur() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(CustomTags.Wind);
        setOrbTexture(DefaultMod.Wind_SMALL_ORB,DefaultMod.Wind_LARGE_ORB);
        TrapTooltip = new ArrayList<>();
        TrapTooltip.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Wind"), BaseMod.getKeywordDescription("thedragonkin:Wind")));
        TrapTooltip.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Tailwind"), BaseMod.getKeywordDescription("thedragonkin:Tailwind")));
        magicNumber = baseMagicNumber = MAGIC;
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
        MagDamage = baseMagDamage = 10;
        isMultiDamage = true;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.name.equals(cardStrings.NAME) || this.name.equals(cardStrings.EXTENDED_DESCRIPTION[0])){
            addToBot(new VFXAction(new WhirlwindEffect()));
            addToBot(new DamageAllEnemiesAction(p,MagDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HEAVY));
            addToBot(new VFXAction(new CleaveEffect()));
            if (this.costForTurn < 2) {
                addToBot(new DamageAllEnemiesAction(p, MagDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HEAVY));
                addToBot(new VFXAction(new CleaveEffect()));
                if (Excalibur.this.upgraded) {
                    FollowUp = new FollowUpExcalibur();
                    addToBot(new MakeTempCardInHandAction(FollowUp));
                    AllCards.addToBottom(FollowUp);
                }
            }
        }
        else {
            if (m.hasPower(JoltedPower.POWER_ID)){
                addToBot(new ApplyPowerAction(p,p,new ChargedUpDuration(p,magicNumber,1)));
            }
            addToBot(new VFXAction(new ShockWaveEffect(m.hb_x,m.hb_y, Color.YELLOW, ShockWaveEffect.ShockWaveType.CHAOTIC)));
            addToBot(new VFXAction(new ShockWaveEffect(m.hb_x,m.hb_y, Color.GOLD, ShockWaveEffect.ShockWaveType.CHAOTIC)));
            addToBot(new DamageAction(m,new DamageInfo(p,MagDamage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
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
        tags.remove(CustomTags.Wind);
        tags.add(CustomTags.Thunder);
        setOrbTexture(DefaultMod.Thunder_SMALL_ORB,DefaultMod.Thunder_LARGE_ORB);
        this.isMultiDamage = false;
        this.target = CardTarget.ENEMY;
        TrapTooltip.clear();
        TrapTooltip.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Thunder"), BaseMod.getKeywordDescription("thedragonkin:Thunder")));
        TrapTooltip.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Jolted"), BaseMod.getKeywordDescription("thedragonkin:Jolted")));
        getCustomTooltips();
        baseMagDamage += 4;
        this.upgradeBaseCost(1);
        upgradeMagicNumber(1);
        this.isMultiDamage = false;
        MagDamageUpgraded = true;
        initializeDescription();
    }
}

package theDragonkin.cards.Gremory.Attacks.Magic;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;
import theDragonkin.cards.Gremory.AbstractMagicGremoryCard;
import theDragonkin.cards.Gremory.FollowUps.FollowUpThunder;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheGremory;
import theDragonkin.powers.Gremory.JoltedPower;

import java.util.ArrayList;
import java.util.List;

import static theDragonkin.DefaultMod.makeCardPath;

public class Thunder extends AbstractMagicGremoryCard implements BranchingUpgradesCard {
    public static final String ID = DefaultMod.makeID(Thunder.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheGremory.Enums.Gremory_Purple_Color;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static AbstractCard FollowUp;
    private static ArrayList<TooltipInfo> TrapTooltip;
    @Override
    public List<TooltipInfo> getCustomTooltipsTop() {
        return TrapTooltip;
    }
    public Thunder() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(CustomTags.Thunder);
        TrapTooltip = new ArrayList<>();
        TrapTooltip.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Thunder"), BaseMod.getKeywordDescription("thedragonkin:Thunder")));
        TrapTooltip.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Jolted"), BaseMod.getKeywordDescription("thedragonkin:Jolted")));
        getCustomTooltips();
        setOrbTexture(DefaultMod.Thunder_SMALL_ORB,DefaultMod.Thunder_LARGE_ORB);
        this.rawDescription =   cardStrings.DESCRIPTION;
        initializeDescription();
        MagDamage = baseMagDamage = 8;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.name.equals(cardStrings.NAME) || this.name.equals(cardStrings.EXTENDED_DESCRIPTION[0])){
            addToBot(new DamageAction(m, new DamageInfo(p,MagDamage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.LIGHTNING));
            if (!m.hasPower(JoltedPower.POWER_ID) && this.upgraded) {
                    addToBot(new ApplyPowerAction(m,p,new JoltedPower(m,p,3),3));
                    FollowUp = new FollowUpThunder();
                    addToBot(new MakeTempCardInHandAction(FollowUp));
                    AllCards.addToBottom(FollowUp);

            }
        }
        else {
            addToBot(new VFXAction(new WhirlwindEffect()));
            addToBot(new DamageAction(m, new DamageInfo(p,MagDamage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            if (this.upgraded && this.costForTurn < this.cost){
                addToBot(new DrawCardAction(p,2));
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
        this.rawDescription =  UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    public void branchUpgrade() {
        name = cardStrings.EXTENDED_DESCRIPTION[2];
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[3];
        tags.remove(CustomTags.Thunder);
        tags.add(CustomTags.Wind);
        setOrbTexture(DefaultMod.Wind_SMALL_ORB,DefaultMod.Wind_LARGE_ORB);
        TrapTooltip.clear();
        TrapTooltip.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Wind"), BaseMod.getKeywordDescription("thedragonkin:Wind")));
        TrapTooltip.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Tailwind"), BaseMod.getKeywordDescription("thedragonkin:Tailwind")));
        getCustomTooltips();
        MagDamageUpgraded = true;
        initializeDescription();
    }
}
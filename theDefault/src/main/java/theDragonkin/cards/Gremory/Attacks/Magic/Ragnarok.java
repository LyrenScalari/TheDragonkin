package theDragonkin.cards.Gremory.Attacks.Magic;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.cards.Gremory.AbstractMagicGremoryCard;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheGremory;
import theDragonkin.powers.Gremory.FreezePower;
import theDragonkin.powers.Gremory.RagnarokPower;

import java.util.ArrayList;
import java.util.List;

import static theDragonkin.DefaultMod.makeCardPath;

public class Ragnarok extends AbstractMagicGremoryCard implements BranchingUpgradesCard {
    public static final String ID = DefaultMod.makeID(Ragnarok.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheGremory.Enums.Gremory_Purple_Color;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 2;
    private static ArrayList<TooltipInfo> TrapTooltip;
    @Override
    public List<TooltipInfo> getCustomTooltipsTop() {
        return TrapTooltip;
    }
    public Ragnarok() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(CustomTags.Fire);
        MagDamage = baseMagDamage = 17;
        this.rawDescription =  cardStrings.DESCRIPTION;
        initializeDescription();
        TrapTooltip = new ArrayList<>();
        TrapTooltip.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Fire"), BaseMod.getKeywordDescription("thedragonkin:Fire")));
        TrapTooltip.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Hot_Streak"), BaseMod.getKeywordDescription("thedragonkin:Hot_Streak")));
        getCustomTooltips();
        setOrbTexture(DefaultMod.Fire_SMALL_ORB,DefaultMod.Fire_LARGE_ORB);
        magicNumber = baseMagicNumber = 5;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.name.equals(cardStrings.NAME) || this.name.equals(cardStrings.EXTENDED_DESCRIPTION[0])) {
            addToBot(new DamageAction(m, new DamageInfo(p, MagDamage, DamageInfo.DamageType.NORMAL)));
            if (AbstractDungeon.actionManager.cardsPlayedThisCombat.size() >= 2
                    && ((AbstractCard)AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 2)).hasTag(CustomTags.Fire)) {
                    addToBot(new ApplyPowerAction(p,p,new RagnarokPower(p,p,magicNumber)));

            } else {
                addToBot(new DamageAction(m, new DamageInfo(p, MagDamage, DamageInfo.DamageType.NORMAL)));
                if (m.hasPower(FreezePower.POWER_ID)) {
                    addToBot(new LoseHPAction(m, p, MagDamage));
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
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[5] + UPGRADE_DESCRIPTION;
        upgradeMagicNumber(5);
        initializeDescription();
    }

    public void branchUpgrade() {
        name = cardStrings.EXTENDED_DESCRIPTION[2];
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[3];
        tags.remove(CustomTags.Fire);
        tags.add(CustomTags.Ice);
        setOrbTexture(DefaultMod.Ice_SMALL_ORB,DefaultMod.Ice_LARGE_ORB);
        TrapTooltip.clear();
        TrapTooltip.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Ice"), BaseMod.getKeywordDescription("thedragonkin:Ice")));
        TrapTooltip.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Chill"), BaseMod.getKeywordDescription("thedragonkin:Chill")));
        TrapTooltip.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Freeze"), BaseMod.getKeywordDescription("thedragonkin:Freeze")));
        getCustomTooltips();
        baseMagDamage -= 8;
        MagDamageUpgraded = true;
        initializeDescription();

    }
}

package theDragonkin.cards.Dragonkin;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.DefaultMod;
import theDragonkin.powers.Dragonkin.DivineConvictionpower;
import theDragonkin.powers.Dragonkin.FuryPower;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPrimalCard extends AbstractDragonkinCard {
    private static ArrayList<TooltipInfo> TrapTooltip;

    @Override
    public List<TooltipInfo> getCustomTooltipsTop() {
        if (TrapTooltip == null)
        {
            TrapTooltip = new ArrayList<>();
            TrapTooltip.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Primal"), BaseMod.getKeywordDescription("thedragonkin:Primal")));
            TrapTooltip.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Fury"), BaseMod.getKeywordDescription("thedragonkin:Fury")));
        }
        return TrapTooltip;
    }
    public AbstractPrimalCard(String id, String img, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, img, cost, type, color, rarity, target);
        setOrbTexture(DefaultMod.PRIMAL_SMALL_ORB,DefaultMod.PRIMAL_LARGE_ORB);
    }

    protected void applyPrimalBonusPower() {
        AbstractPower p = AbstractDungeon.player.getPower(FuryPower.POWER_ID);
        if (p != null) {
            this.baseMagicNumber += (float) (Math.floor(p.amount / 10));
            this.defaultBaseSecondMagicNumber += (float) (Math.floor(p.amount / 10));
        } else {
            this.isMagicNumberModified = false;
            this.magicNumber = this.baseMagicNumber;
            this.defaultSecondMagicNumber = this.defaultBaseSecondMagicNumber;


        }
    }
    protected void revertPrimalBonusPower() {
        AbstractPower p = AbstractDungeon.player.getPower(FuryPower.POWER_ID);
        if(p != null) {
            this.baseMagicNumber -= (float)(Math.floor(p.amount/10));
            this.defaultBaseSecondMagicNumber -= (float)(Math.floor(p.amount/10));
            this.isMagicNumberModified = true;
        }
    }
    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        this.applyPrimalBonusPower();
        super.calculateCardDamage(mo);
        this.revertPrimalBonusPower();
    }
    @Override
    public void applyPowers() {
        this.applyPrimalBonusPower();
        this.magicNumber = this.baseMagicNumber;
        this.defaultSecondMagicNumber = this.defaultBaseSecondMagicNumber;
        super.applyPowers();
        this.revertPrimalBonusPower();
    }
}
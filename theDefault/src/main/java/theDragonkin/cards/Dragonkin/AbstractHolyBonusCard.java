package theDragonkin.cards.Dragonkin;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import  com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.DefaultMod;
import theDragonkin.powers.Dragonkin.DivineConvictionpower;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractHolyBonusCard extends AbstractDragonkinCard {
    private static ArrayList<TooltipInfo> TrapTooltip;
    @Override
    public List<TooltipInfo> getCustomTooltipsTop() {
        if (TrapTooltip == null)
        {
            TrapTooltip = new ArrayList<>();
            TrapTooltip.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Holy"), BaseMod.getKeywordDescription("thedragonkin:Holy")));
            TrapTooltip.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Zeal"), BaseMod.getKeywordDescription("thedragonkin:Zeal")));
        }
        return TrapTooltip;
    }
    public AbstractHolyBonusCard(String id, String img, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, img, cost, type, color, rarity, target);
        setOrbTexture(DefaultMod.HOLY_SMALL_ORB,DefaultMod.HOLY_LARGE_ORB);
    }

    protected void applyHolyBonusPower() {
        AbstractPower p = AbstractDungeon.player.getPower(DivineConvictionpower.POWER_ID);
        if(p != null) {
            this.baseMagicNumber += p.amount;
            this.defaultBaseSecondMagicNumber += p.amount;
        } else {
            this.isMagicNumberModified = false;
            this.magicNumber = this.baseMagicNumber;
            this.defaultSecondMagicNumber = this.defaultBaseSecondMagicNumber;

        }
    }
    protected void revertHolyBonusPower() {
        AbstractPower p = AbstractDungeon.player.getPower(DivineConvictionpower.POWER_ID);
        if(p != null) {
            this.baseMagicNumber -= p.amount;
            this.defaultBaseSecondMagicNumber -= p.amount;
            this.isMagicNumberModified = true;
        }
    }
    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        this.applyHolyBonusPower();
        super.calculateCardDamage(mo);
        this.revertHolyBonusPower();
    }
   @Override
    public void applyPowers() {
       this.applyHolyBonusPower();
       this.magicNumber = this.baseMagicNumber;
       this.defaultSecondMagicNumber = this.defaultBaseSecondMagicNumber;
       super.applyPowers();
       this.revertHolyBonusPower();
    }
}

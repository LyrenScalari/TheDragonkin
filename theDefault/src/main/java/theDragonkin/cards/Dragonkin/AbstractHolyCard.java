package theDragonkin.cards.Dragonkin;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import  com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.CustomTags;
import theDragonkin.DragonkinMod;
import theDragonkin.powers.Dragonkin.DivineConvictionpower;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractHolyCard extends AbstractDragonkinCard {
    public int RadiantExchange = 5;
    public AbstractHolyCard(String id, String img, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, img, cost, type, color, rarity, target);
        setOrbTexture(DragonkinMod.HOLY_SMALL_ORB, DragonkinMod.HOLY_LARGE_ORB);
    }

    protected void applyHolyBonusPower() {
        AbstractPower p = AbstractDungeon.player.getPower(DivineConvictionpower.POWER_ID);
        if (this.hasTag(CustomTags.Radiant)) {
            if (p != null) {
                this.baseMagicNumber += (float) (Math.floor(p.amount / RadiantExchange));
                this.defaultBaseSecondMagicNumber += (float) (Math.floor(p.amount / RadiantExchange));
            } else {
                this.isMagicNumberModified = false;
                this.magicNumber = this.baseMagicNumber;
                this.defaultSecondMagicNumber = this.defaultBaseSecondMagicNumber;
            }
        }
    }
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Holy"),BaseMod.getKeywordDescription("thedragonkin:Holy")));
        return retVal;
    }
    @Override
    public List<String> getCardDescriptors() {

        List<String> tags = new ArrayList<>();
        tags.add(BaseMod.getKeywordTitle("thedragonkin:Holy"));
        tags.addAll(super.getCardDescriptors());
        return tags;
    }
    protected void revertHolyBonusPower() {
        AbstractPower p = AbstractDungeon.player.getPower(DivineConvictionpower.POWER_ID);
        if (this.hasTag(CustomTags.Radiant)) {
            if (p != null) {
                this.baseMagicNumber -= (float) (Math.floor(p.amount / RadiantExchange));
                this.defaultBaseSecondMagicNumber -= (float) (Math.floor(p.amount / RadiantExchange));
                this.isMagicNumberModified = true;
            }
        }
    }
    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        if (this.hasTag(CustomTags.Radiant)) {
            this.applyHolyBonusPower();
            super.calculateCardDamage(mo);
            this.revertHolyBonusPower();
        }
        super.calculateCardDamage(mo);
    }
   @Override
    public void applyPowers() {
       if (this.hasTag(CustomTags.Radiant)) {
           this.applyHolyBonusPower();
           this.magicNumber = this.baseMagicNumber;
           this.defaultSecondMagicNumber = this.defaultBaseSecondMagicNumber;
           this.revertHolyBonusPower();
       }
       super.applyPowers();
    }
}

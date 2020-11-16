package theDragonkin.cards.Dragonkin;

import  com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.powers.DivineConvictionpower;

public abstract class AbstractHolyBonusCard extends AbstractDragonkinCard {

    public AbstractHolyBonusCard(String id, String img, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, img, cost, type, color, rarity, target);
    }

    protected void applyHolyBonusPower() {
        AbstractPower p = AbstractDungeon.player.getPower(DivineConvictionpower.POWER_ID);
        if(p != null) {
            this.baseMagicNumber += p.amount;
            this.baseHeal += p.amount;
        } else {
            this.isMagicNumberModified = false;
            this.magicNumber = this.baseMagicNumber;
        }
    }
    protected void revertHolyBonusPower() {
        AbstractPower p = AbstractDungeon.player.getPower(DivineConvictionpower.POWER_ID);
        if(p != null) {
            this.baseMagicNumber -= p.amount;
            this.baseHeal -= p.amount;
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
       this.heal = this.baseHeal;
       super.applyPowers();
       this.revertHolyBonusPower();
    }
}

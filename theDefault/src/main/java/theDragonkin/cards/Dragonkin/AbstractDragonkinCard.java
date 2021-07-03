package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.FireBurstParticleEffect;
import theDragonkin.DragonkinMod;
import theDragonkin.cards.AbstractDefaultCard;
import theDragonkin.cards.Dragonkin.interfaces.StormCard;
import theDragonkin.powers.Dragonkin.FuryPower;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public abstract class AbstractDragonkinCard extends AbstractDefaultCard {

    // "How come BlazingBreath extends CustomCard and not DynamicCard like all the rest?"

    // Well every card, at the end of the day, extends CustomCard.
    // Abstract Default Card extends CustomCard and builds up on it, adding a second magic number. Your card can extend it and
    // bam - you can have a second magic number in that card (Learn Java inheritance if you want to know how that works).
    // Abstract Dynamic Card builds up on Abstract Default Card even more and makes it so that you don't need to add
    // the NAME and the DESCRIPTION into your card - it'll get it automatically. Of course, this functionality could have easily
    // Been added to the default card rather than creating a new Dynamic one, but was done so to deliberately.

    public AbstractDragonkinCard(final String id,
                                 final String img,
                                 final int cost,
                                 final CardType type,
                                 final CardColor color,
                                 final CardRarity rarity,
                                 final CardTarget target) {

        super(id, languagePack.getCardStrings(id).NAME, img, cost, languagePack.getCardStrings(id).DESCRIPTION, type, color, rarity, target);

    }
    public int realBaseDamage;
    public int realBaseMagic;
    public boolean Storm;
    public int StormRate = 999999999;
    public int secondDamage;
    public int baseSecondDamage;
    public boolean upgradedSecondDamage;
    public boolean isSecondDamageModified;
    @Override
    public void atTurnStart() {
        super.atTurnStart();
        DragonkinMod.WasDrained = false;
    }
    @Override
    public void applyPowers(){
        if (this instanceof StormCard){
            if (AbstractDungeon.player.hasPower(FuryPower.POWER_ID)){
                if (AbstractDungeon.player.getPower(FuryPower.POWER_ID).amount >= this.StormRate){
                    Storm = true;
                    AbstractDungeon.effectsQueue.add(new FireBurstParticleEffect(this.current_x,this.current_y));
                } else Storm = false;
            } else Storm = false;
        }
        if (baseSecondDamage > -1) {
            secondDamage = baseSecondDamage;

            int tmp = baseDamage;
            baseDamage = baseSecondDamage;

            super.applyPowers();

            secondDamage = damage;
            baseDamage = tmp;

            super.applyPowers();

            isSecondDamageModified = (secondDamage != baseSecondDamage);
        } else super.applyPowers();
    }
    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        if (baseSecondDamage > -1) {
            secondDamage = baseSecondDamage;

            int tmp = baseDamage;
            baseDamage = baseSecondDamage;

            super.calculateCardDamage(mo);

            secondDamage = damage;
            baseDamage = tmp;

            super.calculateCardDamage(mo);

            isSecondDamageModified = (secondDamage != baseSecondDamage);
        } else super.calculateCardDamage(mo);
    }
    public void resetAttributes() {
        super.resetAttributes();
        secondDamage = baseSecondDamage;
        isSecondDamageModified = false;
    }
    protected void upgradeSecondDamage(int amount) {
        baseSecondDamage += amount;
        secondDamage = baseSecondDamage;
        upgradedSecondDamage = true;
    }
    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedSecondDamage) {
            secondDamage = baseSecondDamage;
            isSecondDamageModified = true;
        }
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this instanceof StormCard && this.costForTurn == 0){
            ((StormCard) this).onStorm();
        }
    }
}
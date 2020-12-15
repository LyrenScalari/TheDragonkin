package theDragonkin.cards.GroveKeeper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.CollectorCurseEffect;
import theDragonkin.cards.AbstractDefaultCard;
import theDragonkin.powers.GroveKeeper.AlignmentPower;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static theDragonkin.characters.TheGroveKeeper.Enums.THE_GROVEKEEPER;

public abstract class AbstractGroveKeeperCard extends AbstractDefaultCard {
    public boolean isGrovekeeperSecondDamageModified;
    public int GrovekeeperSecondDamage;
    public int BaseGrovekeeperSecondDamage;
    public boolean upgradedGrovekeeperSecondDamage;

    public AbstractGroveKeeperCard(final String id,
                                   final String img,
                                   final int cost,
                                   final CardType type,
                                   final CardColor color,
                                   final CardRarity rarity,
                                   final CardTarget target) {

        super(id, languagePack.getCardStrings(id).NAME, img, cost, languagePack.getCardStrings(id).DESCRIPTION, type, color, rarity, target);

    }
    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedGrovekeeperSecondDamage) {
            GrovekeeperSecondDamage = BaseGrovekeeperSecondDamage;
            isGrovekeeperSecondDamageModified = true;
        }

    }

    public void upgradeGrovekeeperSecondDamage(int amount) {
        BaseGrovekeeperSecondDamage += amount;
        GrovekeeperSecondDamage = BaseGrovekeeperSecondDamage;
        isGrovekeeperSecondDamageModified = true;
    }

    public void onChoseThisOption(AbstractMonster target) {
    }

    @Override
    public void applyPowers()
    {
        GrovekeeperSecondDamage = BaseGrovekeeperSecondDamage;

        int tmp = baseDamage;
        baseDamage = BaseGrovekeeperSecondDamage;

        super.applyPowers();

        GrovekeeperSecondDamage = damage;
        baseDamage = tmp;

        super.applyPowers();

        isGrovekeeperSecondDamageModified = (GrovekeeperSecondDamage != BaseGrovekeeperSecondDamage);
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo)
    {
        GrovekeeperSecondDamage = BaseGrovekeeperSecondDamage;

        int tmp = baseDamage;
        baseDamage = BaseGrovekeeperSecondDamage;

        super.calculateCardDamage(mo);

        GrovekeeperSecondDamage = damage;
        baseDamage = tmp;

        super.calculateCardDamage(mo);

        isGrovekeeperSecondDamageModified = (GrovekeeperSecondDamage != BaseGrovekeeperSecondDamage);
    }
}

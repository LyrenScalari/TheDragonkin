package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.DragonkinMod;
import theDragonkin.powers.Dragonkin.FuryPower;

public abstract class AbstractPrimalCard extends AbstractDragonkinCard {


    public AbstractPrimalCard(String id, String img, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, img, cost, type, color, rarity, target);
        setOrbTexture(DragonkinMod.PRIMAL_SMALL_ORB, DragonkinMod.PRIMAL_LARGE_ORB);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p, new FuryPower(p,p,this.costForTurn*2)));
    }
}
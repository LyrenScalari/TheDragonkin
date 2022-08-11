package theDragonkin.cards.Dragonkin;

import basemod.BaseMod;
import basemod.devcommands.power.Power;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import theDragonkin.DragonkinMod;
import theDragonkin.powers.Dragonkin.FuryPower;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractPrimalCard extends AbstractDragonkinCard {


    public AbstractPrimalCard(String id, String img, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, img, cost, type, color, rarity, target);
        setOrbTexture(DragonkinMod.PRIMAL_SMALL_ORB, DragonkinMod.PRIMAL_LARGE_ORB);
    }
    @Override
    public List<String> getCardDescriptors() {
        List<String> tags = new ArrayList<>();
        tags.add(BaseMod.getKeywordTitle("thedragonkin:Primal"));
        tags.addAll(super.getCardDescriptors());
        return tags;
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.costForTurn > 0) {
            addToBot(new ApplyPowerAction(p, p, new VigorPower(p, this.costForTurn*2)));
        }
    }
}
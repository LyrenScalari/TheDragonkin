package theDragonkin.cards.Dragonkin;

import basemod.BaseMod;
import basemod.devcommands.power.Power;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import  com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theDragonkin.CustomTags;
import theDragonkin.DragonkinMod;
import theDragonkin.powers.Dragonkin.DivineConvictionpower;
import theDragonkin.powers.Dragonkin.FuryPower;
import theDragonkin.powers.Dragonkin.SacrificePower;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractHolyCard extends AbstractDragonkinCard {
    public int RadiantExchange = 5;
    public AbstractHolyCard(String id, String img, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, img, cost, type, color, rarity, target);
        setOrbTexture(DragonkinMod.HOLY_SMALL_ORB, DragonkinMod.HOLY_LARGE_ORB);
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        int bonus = 0;
        if (this.costForTurn > 0) {
            addToBot(new ApplyPowerAction(p, p, new SacrificePower(p, p, this.costForTurn*2)));
        }
    }
    @Override
    public List<String> getCardDescriptors() {
        List<String> tags = new ArrayList<>();
        tags.add(BaseMod.getKeywordTitle("thedragonkin:Holy"));
        tags.addAll(super.getCardDescriptors());
        return tags;
    }
}

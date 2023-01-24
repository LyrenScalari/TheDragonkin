package theDragonkin.cards.Dragonkin;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.DragonkinMod;
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

    private static final UIStrings holyTooltip = CardCrawlGame.languagePack.getUIString("theDragonkin:HolyTooltip");
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo(holyTooltip.TEXT[0], holyTooltip.TEXT[1]));
        return retVal;
    }
    @Override
    public List<String> getCardDescriptors() {
        List<String> tags = new ArrayList<>();
        tags.add(BaseMod.getKeywordTitle("thedragonkin:Holy"));
        tags.addAll(super.getCardDescriptors());
        return tags;
    }
}

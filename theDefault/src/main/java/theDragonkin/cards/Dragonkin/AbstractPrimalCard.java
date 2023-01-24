package theDragonkin.cards.Dragonkin;

import basemod.BaseMod;
import basemod.devcommands.power.Power;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import theDragonkin.DragonkinMod;
import theDragonkin.powers.Dragonkin.FuryPower;
import theDragonkin.powers.Dragonkin.Scorchpower;
import theDragonkin.util.Wiz;

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
    private static final UIStrings holyTooltip = CardCrawlGame.languagePack.getUIString("theDragonkin:PrimalTooltip");
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo(holyTooltip.TEXT[0], holyTooltip.TEXT[1]));
        return retVal;
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.costForTurn > 0) {
            for (int i = 0; i < costForTurn; i++){
                AbstractMonster target = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
                Wiz.applyToEnemy(target,new Scorchpower(target,p,1));
            }
        }
    }
}
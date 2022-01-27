package theDragonkin.orbs;

import IconsAddon.actions.GainCustomBlockAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.EquilibriumPower;
import theDragonkin.DragonkinMod;
import theDragonkin.util.AbstractSeal;

public class FortitudeSeal extends AbstractSeal {
    public static final String ORB_ID = DragonkinMod.makeID("Fortitude");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;
    public AbstractCard Source;
    public FortitudeSeal(int Pow, int Pain, AbstractCard source){
        super();
        name = orbString.NAME;
        PainAmount = Pain;
        BreakAmount = Pow;
        Source = source;
    }
    public void Break(){
        super.Break();
        AbstractDungeon.actionManager.addToBottom(new GainCustomBlockAction(Source,AbstractDungeon.player,BreakAmount));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new EquilibriumPower(AbstractDungeon.player,1)));
    }
    public void updateDescription() {
        description = DESCRIPTIONS[0] + PainAmount + DESCRIPTIONS[1] + DESCRIPTIONS[2] + BreakAmount + DESCRIPTIONS[3];
    }
}
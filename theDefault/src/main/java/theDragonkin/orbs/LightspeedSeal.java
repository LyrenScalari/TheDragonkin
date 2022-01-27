package theDragonkin.orbs;

import IconsAddon.actions.GainCustomBlockAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.powers.EquilibriumPower;
import theDragonkin.DragonkinMod;
import theDragonkin.actions.LightspeedFluxAction;
import theDragonkin.util.AbstractSeal;

public class LightspeedSeal extends AbstractSeal {
    public static final String ORB_ID = DragonkinMod.makeID("Lightspeed");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;
    public AbstractCard Source;
    public LightspeedSeal(int Pow, int Pain, AbstractCard source){
        super();
        name = orbString.NAME;
        PainAmount = Pain;
        BreakAmount = Pow;
        Source = source;
    }
    public void Break(){
        super.Break();
        AbstractDungeon.actionManager.addToBottom(new LightspeedFluxAction(5,()-> new AbstractGameAction() {
            @Override
            public void update() {
                addToTop(new GainEnergyAction(1));
                addToTop(new GainBlockAction(AbstractDungeon.player,BreakAmount));
                isDone = true;
            }
        },Source));
    }
    public void updateDescription() {
        description = DESCRIPTIONS[0] + PainAmount + DESCRIPTIONS[1] + DESCRIPTIONS[2] + BreakAmount + DESCRIPTIONS[3];
    }
}
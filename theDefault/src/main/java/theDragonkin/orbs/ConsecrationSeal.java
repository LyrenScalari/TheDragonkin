package theDragonkin.orbs;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import theDragonkin.DragonkinMod;
import theDragonkin.powers.Dragonkin.PenancePower;
import theDragonkin.util.AbstractSeal;

public class ConsecrationSeal extends AbstractSeal {
    public static final String ORB_ID = DragonkinMod.makeID("Consecration");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;
    public AbstractCard Source;
    public ConsecrationSeal(int Pow, int Pain){
        super();
        name = orbString.NAME;
        PainAmount = Pain;
        BreakAmount = Pow;
    }
    public void Break(){
        super.Break();
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                PenancePower.Power += BreakAmount;
                isDone = true;
            }
        });
    }
    public void updateDescription() {
        description = DESCRIPTIONS[0] + PainAmount + DESCRIPTIONS[1] + DESCRIPTIONS[2] + BreakAmount + DESCRIPTIONS[3];
    }
}
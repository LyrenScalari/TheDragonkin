package theDragonknight.orbs;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import theDragonknight.DragonkinMod;
import theDragonknight.util.AbstractSeal;

public class InfusionSeal extends AbstractSeal {
    public static final String ORB_ID = DragonkinMod.makeID("Infusion");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;
    public AbstractCard Source;
    public InfusionSeal(int Pow, int Pain, AbstractCard source){
        super();
        name = orbString.NAME;
        PainAmount = Pain;
        BreakAmount = Pow;
        Source = source;
    }
    public void Break(){
        super.Break();
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                Source.baseDamage += BreakAmount;
                Source.damage = Source.baseDamage;
                isDone = true;
            }
        });
    }
    public void updateDescription() {
        description = DESCRIPTIONS[0] + PainAmount + DESCRIPTIONS[1] + DESCRIPTIONS[2] + BreakAmount + DESCRIPTIONS[3];
    }
}

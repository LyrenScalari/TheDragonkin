package theDragonkin.orbs;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.powers.EquilibriumPower;
import theDragonkin.DragonkinMod;
import theDragonkin.util.AbstractSeal;

public class SanctuarySeal extends AbstractSeal {
    public static final String ORB_ID = DragonkinMod.makeID("Sanctuary");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;
    public AbstractCard Source;
    public SanctuarySeal(int Pow, int Pain, AbstractCard source){
        super();
        name = orbString.NAME;
        basePainAmount = PainAmount = Pain;
        BreakAmount = Pow;
        Source = source;
    }
    public void Break(){
        super.Break();
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                Source.baseBlock += BreakAmount;
                Source.block = Source.baseBlock;
                isDone = true;
            }
        });
    }
    public void updateDescription() {
        description = DESCRIPTIONS[0] + PainAmount + DESCRIPTIONS[1] + DESCRIPTIONS[2] + BreakAmount + DESCRIPTIONS[3];
    }
}

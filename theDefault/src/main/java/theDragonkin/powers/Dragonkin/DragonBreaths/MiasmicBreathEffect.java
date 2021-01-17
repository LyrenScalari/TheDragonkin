package theDragonkin.powers.Dragonkin.DragonBreaths;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.TriggerMarksAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.DefaultMod;
import theDragonkin.powers.Dragonkin.AcidMarkPower;

public class MiasmicBreathEffect extends AbstractDragonBreathPower{
    public AbstractCard CallingCard;
    public static final String POWER_ID = DefaultMod.makeID("MiasmicBreath");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public MiasmicBreathEffect (int Markamt , AbstractCard Source){
        name = NAME;
        ID = POWER_ID;
        CallingCard = Source;
        amount = (int) Math.ceil((float)Markamt / AbstractDungeon.getCurrRoom().monsters.monsters.stream().filter(it -> !it.isDeadOrEscaped()).count());
    }

    @Override
    public void onBreath(){
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
            addToBot(new ApplyPowerAction(m,owner,new AcidMarkPower(m,owner,amount+(BreathCount))));
        }
        addToBot(new TriggerMarksAction(CallingCard));
    }
}
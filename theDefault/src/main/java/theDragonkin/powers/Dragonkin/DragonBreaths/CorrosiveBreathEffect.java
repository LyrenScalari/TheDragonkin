package theDragonkin.powers.Dragonkin.DragonBreaths;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.TriggerMarksAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.DefaultMod;
import theDragonkin.cards.Dragonkin.CorrosiveBreath;
import theDragonkin.powers.Dragonkin.AcidMarkPower;
import theDragonkin.powers.Dragonkin.Scorchpower;

public class CorrosiveBreathEffect  extends AbstractDragonBreathPower{
    public AbstractCard CallingCard;
    public static final String POWER_ID = DefaultMod.makeID("CorrosiveBreath");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public CorrosiveBreathEffect (int Markamt , AbstractCard Source){
        sourcecard = Source;
        name = NAME;
        ID = POWER_ID;
        CallingCard = Source;
        amount =Markamt;
    }

    @Override
    public void onBreath(){
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
            addToBot(new ApplyPowerAction(m,owner,new AcidMarkPower(m,owner,amount+(BreathCount)),
                    amount+(BreathCount), AbstractGameAction.AttackEffect.POISON));
        }
        addToBot(new TriggerMarksAction(CallingCard));
    }
}
package theDragonkin.powers.Dragonkin.DragonBreaths;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import theDragonkin.DefaultMod;
import theDragonkin.powers.Dragonkin.Scorchpower;

public class BlazingBreathEffect extends AbstractDragonBreathPower {
    public int Scorchamt;
    public static final String POWER_ID = DefaultMod.makeID("BlazingBreath");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public BlazingBreathEffect(int Damage, int Bonus, AbstractCard source){
        sourcecard = source;
        name = NAME;
        ID = POWER_ID;
        amount = Damage;
        Scorchamt = Bonus;
    }

    @Override
    public void onBreath(){
        addToBot(new DamageAllEnemiesAction((AbstractPlayer) owner,amount+(BreathCount*2),
                DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
            addToBot(new ApplyPowerAction(m,owner,new Scorchpower(m,owner,Scorchamt+(BreathCount))));
        }
    }
}

package theDragonkin.powers.Dragonkin.DragonBreaths;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import theDragonkin.DefaultMod;
import theDragonkin.powers.Dragonkin.DivineConvictionpower;

public class RevelationBreathEffect extends AbstractDragonBreathPower{
    public int Zealamt;
    public static final String POWER_ID = DefaultMod.makeID("RevelationBreath");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public RevelationBreathEffect (int Damage , int Bonus){
        name = NAME;
        ID = POWER_ID;
        amount = (int) Math.ceil((float)Damage / AbstractDungeon.getCurrRoom().monsters.monsters.stream().filter(it -> !it.isDeadOrEscaped()).count());
        Zealamt = Bonus;
    }

    @Override
    public void onBreath() {
        addToBot(new DamageAllEnemiesAction((AbstractPlayer) owner, amount+(BreathCount*2), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE));
        addToBot(new AddTemporaryHPAction(owner, owner, Zealamt+(BreathCount)));
    }
}
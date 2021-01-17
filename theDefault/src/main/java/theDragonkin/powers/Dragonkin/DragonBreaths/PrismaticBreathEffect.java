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

public class PrismaticBreathEffect extends AbstractDragonBreathPower{
    public int Zealamt;
    public static final String POWER_ID = DefaultMod.makeID("PrismaticBreath");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public PrismaticBreathEffect (int TempHP , int Zeal){
        name = NAME;
        ID = POWER_ID;
        amount = (int) Math.ceil((float)TempHP / AbstractDungeon.getCurrRoom().monsters.monsters.stream().filter(it -> !it.isDeadOrEscaped()).count());
        Zealamt = Zeal;
    }

    @Override
    public void onBreath() {
        addToBot(new AddTemporaryHPAction(owner, owner, amount+(BreathCount)));
        addToBot(new ApplyPowerAction(owner, owner, new DivineConvictionpower(owner, owner, Zealamt+(BreathCount))));
    }
}
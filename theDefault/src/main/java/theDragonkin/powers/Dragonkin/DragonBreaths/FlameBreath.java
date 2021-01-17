package theDragonkin.powers.Dragonkin.DragonBreaths;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import theDragonkin.DefaultMod;

public class FlameBreath extends AbstractDragonBreathPower{
    public static final String POWER_ID = DefaultMod.makeID("FlameBreath");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public FlameBreath (int Damage){
        name = NAME;
        ID = POWER_ID;
        amount = (int) Math.ceil((float)Damage / AbstractDungeon.getCurrRoom().monsters.monsters.stream().filter(it -> !it.isDeadOrEscaped()).count());
    }

    @Override
    public void onBreath(){ ;
        addToBot(new DamageAllEnemiesAction((AbstractPlayer) owner,amount+(BreathCount*2), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE));
    }
}
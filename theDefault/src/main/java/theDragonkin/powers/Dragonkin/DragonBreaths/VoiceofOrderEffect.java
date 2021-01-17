package theDragonkin.powers.Dragonkin.DragonBreaths;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theDragonkin.DefaultMod;

public class VoiceofOrderEffect extends AbstractDragonBreathPower{
    public int Block;
    public int Bonus;
    public static final String POWER_ID = DefaultMod.makeID("VoiceofOrder");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public VoiceofOrderEffect (int Block, int Debuff){
        name = NAME;
        ID = POWER_ID;
        amount = (int) Math.ceil((float)Debuff / AbstractDungeon.getCurrRoom().monsters.monsters.stream().filter(it -> !it.isDeadOrEscaped()).count());
        this.Block = Block;
    }

    @Override
    public void onBreath() {
        addToBot(new GainBlockAction(owner,Block+(BreathCount)));
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
            addToBot(new ApplyPowerAction(m,owner,new VulnerablePower(m,amount+(BreathCount),false)));
            addToBot(new ApplyPowerAction(m,owner,new WeakPower(m,amount+(BreathCount),false)));
        }
    }
}

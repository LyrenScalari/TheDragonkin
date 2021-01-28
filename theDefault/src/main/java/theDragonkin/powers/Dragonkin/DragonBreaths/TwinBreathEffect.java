package theDragonkin.powers.Dragonkin.DragonBreaths;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theDragonkin.DefaultMod;
import theDragonkin.powers.Dragonkin.Scorchpower;

public class TwinBreathEffect extends AbstractDragonBreathPower {
    public static final String POWER_ID = DefaultMod.makeID("SmaugsSmog");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public TwinBreathEffect (int Damage , int Scorch, AbstractCard source){
        super();
        sourcecard = source;
        name = NAME;
        ID = POWER_ID;
        amount = Damage + ((BreathCount -1) * 2);
        amount4 = Scorch + ((BreathCount -1));
    }

    @Override
    public void onBreath() {
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
            addToBot(new DamageAction(m, new DamageInfo(AbstractDungeon.player, amount,
                    DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
            addToBot(new ApplyPowerAction(m,owner,new Scorchpower(m,owner,amount4 )));
        }
    }
}

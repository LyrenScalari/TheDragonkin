package theDragonkin.powers.Dragonkin.DragonBreaths;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.ScreenOnFireEffect;
import theDragonkin.DefaultMod;
import theDragonkin.powers.Dragonkin.Scorchpower;

public class SmaugsSmogEffect extends AbstractDragonBreathPower{
    public int Block;
    public int Bonus;
    public static final String POWER_ID = DefaultMod.makeID("SmaugsSmog");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public SmaugsSmogEffect (int Damage , int Block, int Vuln, AbstractCard source){
        sourcecard = source;
        name = NAME;
        ID = POWER_ID;
        amount = Damage;
        this.Block = Block;
        Bonus = Vuln;
    }

    @Override
    public void onBreath() {
        addToBot(new VFXAction(new ScreenOnFireEffect()));
        addToBot(new DamageAllEnemiesAction((AbstractPlayer) owner,amount+(BreathCount*2),
                DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
        addToBot(new GainBlockAction(owner, Block + (BreathCount)));
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            addToBot(new ApplyPowerAction(m, owner, new VulnerablePower(m,Bonus+BreathCount,false),Bonus+BreathCount));
        }
    }
}

package theDragonkin.powers.Dragonkin.DragonBreaths;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
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
import com.megacrit.cardcrawl.vfx.combat.ScreenOnFireEffect;
import com.megacrit.cardcrawl.vfx.combat.SweepingBeamEffect;
import theDragonkin.DefaultMod;
import theDragonkin.powers.Dragonkin.AcidMarkPower;
import theDragonkin.powers.Dragonkin.Scorchpower;

public class MoltenBreathEffect extends AbstractDragonBreathPower {
    public static final String POWER_ID = DefaultMod.makeID("MoltenBreath");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public MoltenBreathEffect (int Damage, int Block, int Scorch, AbstractCard source){
        super();
        sourcecard = source;
        name = NAME;
        ID = POWER_ID;
        amount = Damage+((BreathCount -1) * 2);
        amount4 = Scorch+((BreathCount -1));
        amount3 = Block+((BreathCount -1));
    }

    @Override
    public void onBreath(){
        addToBot(new VFXAction(new SweepingBeamEffect(owner.drawX,owner.drawY, AbstractDungeon.player.flipHorizontal), 0.4F));
        addToBot(new GainBlockAction(owner,amount3+(BreathCount)));
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            addToBot(new DamageAction(m, new DamageInfo(AbstractDungeon.player, amount,
                    DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
        }
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
            addToBot(new ApplyPowerAction(m,owner,new Scorchpower(m,owner,amount4)));
        }
    }
}

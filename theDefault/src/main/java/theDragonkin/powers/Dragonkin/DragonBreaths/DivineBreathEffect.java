package theDragonkin.powers.Dragonkin.DragonBreaths;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.watcher.TriggerMarksAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LaserBeamEffect;
import theDragonkin.DefaultMod;
import theDragonkin.powers.Dragonkin.AcidMarkPower;
import theDragonkin.powers.Dragonkin.DivineConvictionpower;

public class DivineBreathEffect extends AbstractDragonBreathPower{
public int Zealamt;
public static final String POWER_ID = DefaultMod.makeID("DivineBreath");
private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
public static final String NAME = powerStrings.NAME;
public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
public DivineBreathEffect (int Damage , int Bonus, AbstractCard source){
        sourcecard = source;
        name = NAME;
        ID = POWER_ID;
        amount = Damage;
        Zealamt = Bonus;
        }

@Override
public void onBreath(){
        addToBot(new VFXAction(new LaserBeamEffect(owner.drawX,owner.drawY)));
        addToBot(new DamageAllEnemiesAction((AbstractPlayer) owner,amount+(BreathCount*2),
                DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE));
        addToBot(new ApplyPowerAction(owner,owner,new DivineConvictionpower(owner,owner,Zealamt+(BreathCount))));
        }
}
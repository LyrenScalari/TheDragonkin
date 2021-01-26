package theDragonkin.powers.Dragonkin.DragonBreaths;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.unique.RemoveAllPowersAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.LightFlareParticleEffect;
import theDragonkin.DefaultMod;

public class AkatirnsDecreeEffect extends AbstractDragonBreathPower{
    public int Scorchamt;
    public static final String POWER_ID = DefaultMod.makeID("AkatirnsDecree");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public AkatirnsDecreeEffect (int Damage, int Bonus, AbstractCard source){
        sourcecard = source;
        name = NAME;
        ID = POWER_ID;
        amount =Damage;
        Scorchamt = (int) Math.ceil((float)Bonus /AbstractDungeon.getCurrRoom().monsters.monsters.stream().filter(it -> !it.isDeadOrEscaped()).count());
    }

    @Override
    public void onBreath(){
        addToBot(new VFXAction(new LightFlareParticleEffect(owner.drawX,owner.drawY, Color.GOLD.cpy())));
        addToBot(new DamageAllEnemiesAction((AbstractPlayer) owner,(int)amount+(BreathCount*2), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE));
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
            addToBot(new ApplyPowerAction(m,owner,new WeakPower(m,Scorchamt+(BreathCount),false)));
            addToBot(new ApplyPowerAction(m,owner,new VulnerablePower(m,(int) Scorchamt+(BreathCount),false)));
        }
        addToBot(new RemoveAllPowersAction(owner,true));
    }
}

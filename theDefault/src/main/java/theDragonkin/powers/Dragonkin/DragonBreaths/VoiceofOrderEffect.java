package theDragonkin.powers.Dragonkin.DragonBreaths;

import basemod.devcommands.power.Power;
import com.badlogic.gdx.graphics.Color;
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
import com.megacrit.cardcrawl.vfx.combat.InversionBeamEffect;
import com.megacrit.cardcrawl.vfx.combat.LightFlareParticleEffect;
import theDragonkin.DefaultMod;

public class VoiceofOrderEffect extends AbstractDragonBreathPower{
    public static final String POWER_ID = DefaultMod.makeID("VoiceofOrder");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public VoiceofOrderEffect (int Block, int Debuff, AbstractCard source){
        super();
        sourcecard = source;
        name = NAME;
        ID = POWER_ID;
        amount4 = Debuff + (BreathCount -1);
        amount3 = Block + (BreathCount -1);
    }

    @Override
    public void onBreath() {
        addToBot(new VFXAction(new LightFlareParticleEffect(owner.drawX,owner.drawY, Color.GOLD.cpy())));
        addToBot(new GainBlockAction(owner,amount3));
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
            addToBot(new ApplyPowerAction(m, owner, new WeakPower(m,amount4,false),amount4));
            addToBot(new ApplyPowerAction(m, owner, new VulnerablePower(m,amount4,false),amount4));
        }
    }
}

package theDragonkin.Stances;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.vfx.stance.CalmParticleEffect;
import com.megacrit.cardcrawl.vfx.stance.DivinityParticleEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import theDragonkin.orbs.ModifyOrbStance;
import theDragonkin.patches.RenderFloatyChi;
import theDragonkin.powers.WindWalker.InvisibleFocus;

public class Conduit extends AbstractStance implements ModifyOrbStance {
    static final String STANCE_ID ="theDragonkin:Conduit";
    static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString(STANCE_ID);
    public Conduit(){
        this.ID = STANCE_ID;
        this.name = stanceString.NAME;
        this.updateDescription();
        RenderFloatyChi.angleSpeed = 1.00f;
        RenderFloatyChi.amplitude = 100;
        RenderFloatyChi.dx = 0;
        RenderFloatyChi.dx2 = 0;
    }
    @Override
    public void updateDescription() {
        this.description = stanceString.DESCRIPTION[0];
    }
    public void updateAnimation() {
        RenderFloatyChi.bobX = (float) Math.abs(RenderFloatyChi.amplitude*Math.sin(RenderFloatyChi.bobTimer));
        if (!Settings.DISABLE_EFFECTS) {
            this.particleTimer -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer < 0.0F) {
                this.particleTimer = 0.05F;
                AbstractDungeon.effectsQueue.add(new CalmParticleEffect());
            }
        }


    }

    public void onExitStance() {
        for (AbstractOrb abstractOrb : AbstractDungeon.player.orbs){
            abstractOrb.passiveAmount -= 5;
            abstractOrb.evokeAmount -= 5;
            abstractOrb.updateDescription();
        }
    }
    @Override
    public void ModifyOrb(AbstractOrb abstractOrb) {
        abstractOrb.passiveAmount += 5;
        abstractOrb.evokeAmount += 5;
    }
}
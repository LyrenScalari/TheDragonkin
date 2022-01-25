package theDragonkin.Stances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.vfx.stance.CalmParticleEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import theDragonkin.orbs.ModifyOrbStance;
import theDragonkin.patches.RenderFloatyChi;
import theDragonkin.powers.WindWalker.InvisibleFocus;

public class Downdraft extends AbstractStance implements ModifyOrbStance, ModifyBlockStance {
    static final String STANCE_ID ="theDragonkin:Downdraft";
    static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString(STANCE_ID);
    public Downdraft(){
        this.ID = STANCE_ID;
        this.name = stanceString.NAME;
        this.updateDescription();
        RenderFloatyChi.angleSpeed = 0.50f;
        RenderFloatyChi.amplitude = 0;
        RenderFloatyChi.dx = -20;
        RenderFloatyChi.dx2 = -20;
    }
    @Override
    public void updateDescription() {
        this.description = stanceString.DESCRIPTION[0];
    }
    public void updateAnimation() {
        if (!Settings.DISABLE_EFFECTS) {
            this.particleTimer -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer < 0.0F) {
                this.particleTimer = 0.05F;
                AbstractDungeon.effectsQueue.add(new CalmParticleEffect());
            }
        }

        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer2 < 0.0F) {
            this.particleTimer2 = MathUtils.random(0.3F, 0.4F);
            AbstractDungeon.effectsQueue.add(new StanceAuraEffect("Calm"));
        }

    }
    public void onExitStance() {
        for (AbstractOrb abstractOrb : AbstractDungeon.player.orbs){
            abstractOrb.passiveAmount -= 3;
            abstractOrb.evokeAmount -= 3;
            abstractOrb.updateDescription();
        }
    }
    @Override
    public void ModifyOrb(AbstractOrb abstractOrb) {
        abstractOrb.passiveAmount += 3;
        abstractOrb.evokeAmount += 3;
    }

    @Override
    public float modifyBlock(float blockAmount) {
        return blockAmount += 3;
    }
}
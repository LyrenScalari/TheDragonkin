package theDragonknight.Stances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.vfx.stance.CalmParticleEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import theDragonknight.orbs.ModifyOrbStance;
import theDragonknight.orbs.Rain;
import theDragonknight.patches.RenderFloatyChi;
import theDragonknight.powers.WindWalker.InvisibleFocus;

public class Tempest extends AbstractStance implements ModifyOrbStance {
    static final String STANCE_ID ="theDragonkin:Tempest";
    static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString(STANCE_ID);
    public Tempest(){
        this.ID = STANCE_ID;
        this.name = stanceString.NAME;
        this.updateDescription();
        RenderFloatyChi.angleSpeed = 1.20f;
        RenderFloatyChi.amplitude = 70;
        RenderFloatyChi.dx = 0;
        RenderFloatyChi.dx2 = 0;
    }
    @Override
    public void updateDescription() {
        this.description = stanceString.DESCRIPTION[0];
    }
    public void updateAnimation() {
        RenderFloatyChi.bobX = (float) (RenderFloatyChi.amplitude*Math.sin(RenderFloatyChi.bobTimer));
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
            AbstractDungeon.effectsQueue.add(new StanceAuraEffect("Wrath"));
        }

    }
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        return type == DamageInfo.DamageType.NORMAL ? damage + 3 : damage;
    }
    public void onExitStance() {
        for (AbstractOrb abstractOrb : AbstractDungeon.player.orbs){
            abstractOrb.passiveAmount -= 3;
            abstractOrb.evokeAmount -= 3;
            abstractOrb.updateDescription();
        }
        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new Rain()));
    }
    @Override
    public void ModifyOrb(AbstractOrb abstractOrb) {
        abstractOrb.passiveAmount += 3;
        abstractOrb.evokeAmount += 3;
    }
}
package theDragonkin.Stances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.vfx.stance.CalmParticleEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import com.megacrit.cardcrawl.vfx.stance.WrathParticleEffect;
import theDragonkin.orbs.ModifyOrbStance;

public class Zephyr extends AbstractStance implements ModifyBlockStance{
    static final String STANCE_ID ="theDragonkin:Zephyr";
    static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString(STANCE_ID);
    public Zephyr(){
        this.ID = STANCE_ID;
        this.name = stanceString.NAME;
        this.updateDescription();
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
                AbstractDungeon.effectsQueue.add(new WrathParticleEffect());
            }
        }

        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer2 < 0.0F) {
            this.particleTimer2 = MathUtils.random(0.3F, 0.4F);
            AbstractDungeon.effectsQueue.add(new StanceAuraEffect("Divinity"));
        }

    }

    public void onExitStance() {
        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new Frost()));
    }

    @Override
    public float modifyBlock(float blockAmount) {
        return blockAmount += 5;
    }
}
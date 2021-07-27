package theDragonkin.Stances;

import basemod.devcommands.draw.Draw;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import com.megacrit.cardcrawl.vfx.stance.WrathParticleEffect;
import theDragonkin.DragonkinMod;

public class Whirlwind extends AbstractStance {
    static final String STANCE_ID = "theDragonkin:Whirlwind";
    static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString(STANCE_ID);
    public Whirlwind(){
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
            AbstractDungeon.effectsQueue.add(new StanceAuraEffect("Calm"));
        }

    }
    @Override
    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new StanceAuraEffect("Calm")));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new StanceAuraEffect("Wrath")));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new StanceAuraEffect("Calm")));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1));
    }
    @Override
    public void onEnterStance() {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1));
    }
}

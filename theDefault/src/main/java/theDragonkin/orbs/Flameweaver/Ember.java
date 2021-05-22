package theDragonkin.orbs.Flameweaver;

import basemod.abstracts.CustomOrb;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.actions.defect.EvokeSpecificOrbAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.FlameBallParticleEffect;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbActivateEffect;
import theDragonkin.DragonkinMod;

import static theDragonkin.DragonkinMod.makeOrbPath;

public class Ember extends CustomOrb {

    // Standard ID/Description
    public static final String ORB_ID = DragonkinMod.makeID("Ember");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;

    private static final int PASSIVE_AMOUNT = 3;
    private static final int EVOKE_AMOUNT = 3;

    // Animation Rendering Numbers - You can leave these at default, or play around with them and see what they change.
    private float vfxTimer = 1.0f;
    private float vfxIntervalMin = 0.1f;
    private float vfxIntervalMax = 0.4f;
    private static final float ORB_WAVY_DIST = 0.04f;
    private static final float PI_4 = 12.566371f;
    private AbstractMonster Target;

    public Ember(AbstractMonster target, int base) {
        // The passive/evoke description we pass in here, specifically, don't matter
        // You can ctrl+click on CustomOrb from the `extends CustomOrb` above.
        // You'll see below we override CustomOrb's updateDescription function with our own, and also, that's where the passiveDescription and evokeDescription
        // parameters are used. If your orb doesn't use any numbers/doesn't change e.g "Evoke: shuffle your draw pile."
        // then you don't need to override the update description method and can just pass in the parameters here.
        super(ORB_ID, orbString.NAME, PASSIVE_AMOUNT, EVOKE_AMOUNT, DESCRIPTIONS[1], DESCRIPTIONS[2], makeOrbPath("default_orb.png"));
        Target = target;
        updateDescription();
        baseEvokeAmount = base;
        basePassiveAmount = base;
        angle = MathUtils.random(360.0f); // More Animation-related Numbers
        channelAnimTimer = 0.5f;
    }

    @Override
    public void updateDescription() { // Set the on-hover description of the orb
        applyFocus(); // Apply Focus (Look at the next method)
        if (Target != null){
            description = DESCRIPTIONS[0] + DESCRIPTIONS[1] + evokeAmount + DESCRIPTIONS[2] + Target.name;
        }
    }

    public void applyFocus() {
        passiveAmount = basePassiveAmount;
        evokeAmount = baseEvokeAmount;
    }

    @Override
    public void onEvoke() { // 1.On Orb Evoke
        AbstractDungeon.actionManager.addToBottom(new DamageAction(Target,new DamageInfo(AbstractDungeon.player,evokeAmount, DamageInfo.DamageType.THORNS),
                AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public void updateAnimation() {// You can totally leave this as is.
        // If you want to create a whole new orb effect - take a look at conspire's Water Orb. It includes a custom sound, too!
        super.updateAnimation();
        angle += Gdx.graphics.getDeltaTime() * 45.0f;
        vfxTimer -= Gdx.graphics.getDeltaTime();
        if (vfxTimer < 0.0f) {
            AbstractDungeon.effectList.add(new FlameBallParticleEffect(cX, cY,evokeAmount));
        vfxTimer = MathUtils.random(vfxIntervalMin, vfxIntervalMax);
    }
}

    // Render the orb.
    @Override
    public void render(SpriteBatch sb) {
        renderText(sb);
    }


    @Override
    public void triggerEvokeAnimation() { // The evoke animation of this orb is the dark-orb activation effect.
        AbstractDungeon.effectsQueue.add(new DarkOrbActivateEffect(cX, cY));
    }

    @Override
    public void playChannelSFX() { // When you channel this orb, the ATTACK_FIRE effect plays ("Fwoom").
        CardCrawlGame.sound.play("ATTACK_FIRE", 0.1f);
    }
    @Override
    public void onEndOfTurn() {
        if (EnergyPanel.totalCount > 0) {
            AbstractDungeon.actionManager.addToBottom(new EvokeSpecificOrbAction(this));
        }
    }
    @Override
    public AbstractOrb makeCopy() {
        return new Ember(Target,evokeAmount);
    }

}

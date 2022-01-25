package theDragonkin.orbs.DragonShouts.Frostbreath;

import IconsAddon.util.BlockModifierManager;
import IconsAddon.util.DamageModifierManager;
import basemod.abstracts.CustomOrb;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.actions.defect.EvokeSpecificOrbAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.EvokeAllOrbsAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.BlizzardEffect;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbPassiveEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;
import theDragonkin.DamageModifiers.BlockModifiers.FrostBlock;
import theDragonkin.DamageModifiers.FrostDamage;
import theDragonkin.DragonkinMod;
import theDragonkin.orbs.DragonShouts.OnChannelOrb;
import theDragonkin.util.TexLoader;

import java.util.ArrayList;

import static theDragonkin.DragonkinMod.makeOrbPath;

public class Diin extends CustomOrb implements OnChannelOrb {
    public static final String ORB_ID = DragonkinMod.makeID("Diin");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;

    private static final int PASSIVE_AMOUNT = 2;
    private static final int EVOKE_AMOUNT = 2;
    private static final int SHOUT_BLOCK = 10;
    private static final int SHOUT_POWER = 8;

    // Animation Rendering Numbers - You can leave these at default, or play around with them and see what they change.
    private float vfxTimer = 1.0f;
    private float vfxIntervalMin = 0.1f;
    private float vfxIntervalMax = 0.4f;
    private static final float ORB_WAVY_DIST = 0.04f;
    private static final float PI_4 = 12.566371f;

    public Diin() {
        // The passive/evoke description we pass in here, specifically, don't matter
        // You can ctrl+click on CustomOrb from the `extends CustomOrb` above.
        // You'll see below we override CustomOrb's updateDescription function with our own, and also, that's where the passiveDescription and evokeDescription
        // parameters are used. If your orb doesn't use any numbers/doesn't change e.g "Evoke: shuffle your draw pile."
        // then you don't need to override the update description method and can just pass in the parameters here.
        super(ORB_ID, orbString.NAME, PASSIVE_AMOUNT, EVOKE_AMOUNT, DESCRIPTIONS[1], DESCRIPTIONS[3], makeOrbPath("default_orb.png"));
        img = TexLoader.getTexture(makeOrbPath("default_orb.png"));
        updateDescription();
        angle = MathUtils.random(360.0f); // More Animation-related Numbers
        channelAnimTimer = 0.5f;
    }
    @Override
    public void onEvoke(){
        AbstractCreature m = AbstractDungeon.getRandomMonster();
        if (m != null) {
            float speedTime = 0.2F / (float) AbstractDungeon.player.orbs.size();
            if (Settings.FAST_MODE) {
                speedTime = 0.0F;
            }
            for (AbstractMonster M : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!M.isDeadOrEscaped()) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(M,AbstractDungeon.player,new WeakPower(M,passiveAmount,false)));
                }
            }
        }
    }
    @Override
    public void updateDescription() { // Set the on-hover description of the orb
        applyFocus(); // Apply Focus (Look at the next method)
        description = DESCRIPTIONS[0] + passiveAmount + DESCRIPTIONS[1] + DESCRIPTIONS[2] + evokeAmount + DESCRIPTIONS[3];
    }
    @Override
    public void onEndOfTurn() {
        int MemberCount = 0;
        for (AbstractOrb orb : AbstractDungeon.player.orbs){
            if (orb instanceof Fo || orb instanceof Krah){
                MemberCount = 1;
            }
        }
        if (MemberCount > 0){
            AbstractCreature m = AbstractDungeon.getRandomMonster();
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m,new DamageInfo(AbstractDungeon.player,applyLockOn(m,passiveAmount))));
        }
    }

    @Override
    public AbstractOrb makeCopy() {
        return new Diin();
    }

    @Override
    public void playChannelSFX() {

    }
    @Override
    public void updateAnimation() {// You can totally leave this as is.
        // If you want to create a whole new orb effect - take a look at conspire's WaterDamage Orb. It includes a custom sound, too!
        super.updateAnimation();
        angle += Gdx.graphics.getDeltaTime() * 45.0f;
        vfxTimer -= Gdx.graphics.getDeltaTime();
        if (vfxTimer < 0.0f) {
            AbstractDungeon.effectList.add(new DarkOrbPassiveEffect(cX, cY)); // This is the purple-sparkles in the orb. You can change this to whatever fits your orb.
            vfxTimer = MathUtils.random(vfxIntervalMin, vfxIntervalMax);
        }
    }
    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(new Color(1.0f, 1.0f, 1.0f, c.a / 2.0f));
        sb.draw(img, cX - 48.0f, cY - 48.0f + bobEffect.y, 48.0f, 48.0f, 96.0f, 96.0f, scale + MathUtils.sin(angle / PI_4) * ORB_WAVY_DIST * Settings.scale, scale, angle, 0, 0, 96, 96, false, false);
        sb.setColor(new Color(1.0f, 1.0f, 1.0f, this.c.a / 2.0f));
        sb.setBlendFunction(770, 1);
        sb.draw(img, cX - 48.0f, cY - 48.0f + bobEffect.y, 48.0f, 48.0f, 96.0f, 96.0f, scale, scale + MathUtils.sin(angle / PI_4) * ORB_WAVY_DIST * Settings.scale, -angle, 0, 0, 96, 96, false, false);
        sb.setBlendFunction(770, 771);
        renderText(sb);
        hb.render(sb);
    }
    public void Shout(ArrayList<AbstractOrb> abstractOrbs){
        if (AbstractDungeon.player.maxOrbs == 3){
            AbstractDungeon.actionManager.addToTop(new EvokeAllOrbsAction());
        } else {
            for (AbstractOrb orb : abstractOrbs) {
                AbstractDungeon.actionManager.addToTop(new EvokeSpecificOrbAction(orb));
            }
        }
        AbstractDungeon.effectsQueue.add(new BlizzardEffect(8,false));
        AbstractDungeon.effectsQueue.add(new BlizzardEffect(8,false));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player,SHOUT_BLOCK));
        for (int i = 0; i < 4; i++){
            AbstractCreature m = AbstractDungeon.getRandomMonster();
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m,new DamageInfo(AbstractDungeon.player,applyLockOn(m,SHOUT_POWER))));
        }
    }

    @Override
    public void onChannel() {
        ArrayList<AbstractOrb> orbs = new ArrayList<>();
        AbstractOrb A = null;
        AbstractOrb B = null;
        AbstractOrb C;
        C = this;
        orbs.add(C);
        for (AbstractOrb a : AbstractDungeon.player.orbs) {
            if (a instanceof Fo) {
                A = a;
                orbs.add(A);
            }
            if (a instanceof Krah) {
                B = a;
                orbs.add(B);
            }
            if (orbs.contains(A) && orbs.contains(B) && orbs.contains(C)) {
                ((Diin) C).Shout(orbs);
            }
        }
    }
}

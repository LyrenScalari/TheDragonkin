package theDragonkin.orbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.actions.defect.EvokeSpecificOrbAction;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.vfx.combat.*;
import theDragonkin.DefaultMod;
import theDragonkin.powers.GroveKeeper.NaturePower;

import static com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_LIGHT;
import static theDragonkin.DefaultMod.makeOrbPath;

public class LifeBloom extends AbstractGrovekeeperOrb {

    // Standard ID/Description
    public static final String ORB_ID = DefaultMod.makeID("LifeBloom");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;

    private static int PASSIVE_AMOUNT = 4;
    private static int EVOKE_AMOUNT = 5;
    private static int BASEEVOKE_AMOUNT = 4;
    private static int CURRENTBONUS = 0;
    private static int Healcounter = 0;

    // Animation Rendering Numbers - You can leave these at default, or play around with them and see what they change.
    private float vfxTimer = 1.0f;
    private float vfxIntervalMin = 0.1f;
    private float vfxIntervalMax = 0.4f;
    private static final float ORB_WAVY_DIST = 0.04f;
    private static final float PI_4 = 12.566371f;
    public int realevokeamount;

    public LifeBloom() {
        super(ORB_ID, orbString.NAME, PASSIVE_AMOUNT, EVOKE_AMOUNT, DESCRIPTIONS[0], DESCRIPTIONS[2], makeOrbPath("default_orb.png"));
        updateDescription();
        baseEvokeAmount = evokeAmount = EVOKE_AMOUNT;
        realevokeamount = 6;
        basePassiveAmount = passiveAmount = PASSIVE_AMOUNT;
        angle = MathUtils.random(360.0f); // More Animation-related Numbers
        channelAnimTimer = 0.5f;
    }

    @Override
    public void updateDescription() { // Set the on-hover description of the orb
        super.applyFocus(); // Apply Focus (Look at the next method)
        StringBuilder sb = new StringBuilder();
        sb.append(DESCRIPTIONS[0]);
        sb.append(evokeAmount);
        sb.append(DESCRIPTIONS[1]);
        sb.append(DESCRIPTIONS[2]);
        sb.append(evokeAmount + 4);
        sb.append(DESCRIPTIONS[3]);
        sb.append(passiveAmount);
        this.description = sb.toString();
    }

    @Override
    public void onEvoke() { // 1.On Orb Evoke

        // The damage matrix is how orb damage all enemies actions have to be assigned. For regular cards that do damage to everyone, check out cleave or whirlwind - they are a bit simpler.
        AbstractDungeon.actionManager.addToBottom(new HealAction(AbstractDungeon.player,AbstractDungeon.player,evokeAmount + 4));
        AbstractDungeon.actionManager.addToBottom(new SFXAction("STANCE_ENTER_CALM")); // 3.And play a Jingle Sound.
        // For a list of sound effects you can use, look under com.megacrit.cardcrawl.audio.SoundMaster - you can see the list of keys you can use there. As far as previewing what they sound like, open desktop-1.0.jar with something like 7-Zip and go to audio. Reference the file names provided. (Thanks fiiiiilth)

    }

    @Override
    public void onHarvest(int HarvestRedux){
        AbstractDungeon.actionManager.addToBottom(new AddTemporaryHPAction(AbstractDungeon.player,AbstractDungeon.player,evokeAmount));
        this.passiveAmount -= HarvestRedux;
        if (this.passiveAmount <= 0){
            AbstractDungeon.actionManager.addToBottom(new EvokeSpecificOrbAction(this));
        }
        updateDescription();
    }

    @Override
    public void updateAnimation() {// You can totally leave this as is.
        // If you want to create a whole new orb effect - take a look at conspire's Water Orb. It includes a custom sound, too!
        super.updateAnimation();
        angle += Gdx.graphics.getDeltaTime() * 45.0f;
        vfxTimer -= Gdx.graphics.getDeltaTime();
        if (vfxTimer < 0.0f) {
            AbstractDungeon.effectList.add(new HealVerticalLineEffect(cX, cY)); // This is the purple-sparkles in the orb. You can change this to whatever fits your orb.
            vfxTimer = MathUtils.random(vfxIntervalMin, vfxIntervalMax);
        }
    }

    // Render the orb.
    @Override
    public void render(SpriteBatch sb) {
        renderText(sb);
        hb.render(sb);
    }
    protected void renderText(SpriteBatch sb) {
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.evokeAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET - 4.0F * Settings.scale, new Color(0.2F, 1.0F, 1.0F, this.c.a), this.fontScale);
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.passiveAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET + 20.0F * Settings.scale, this.c, this.fontScale);
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
    }
    @Override
    public AbstractOrb makeCopy() {
        return new InvigoratingBloom();
    }

}

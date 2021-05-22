package theDragonkin.powers.Dragonkin;

import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import com.megacrit.cardcrawl.vfx.stance.DivinityParticleEffect;
import theDragonkin.DragonkinMod;
import theDragonkin.cards.Dragonkin.interfaces.ReciveDamageEffect;

public class DivineRetributionPower extends AbstractPower implements CloneablePowerInterface, NonStackablePower, ReciveDamageEffect {
    public AbstractCreature source;

    public static final String POWER_ID = DragonkinMod.makeID("DivineRetribution");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.

    public DivineRetributionPower(final AbstractCreature owner, final AbstractCreature source) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = 0;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;
        this.loadRegion("defenseNext");

        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        if (amount > 0){
            CardCrawlGame.sound.play("POWER_MANTRA", 0.05F);
            for (int i = 0; i < Math.ceil(this.amount / 2); ++i) {
                addToBot(new VFXAction(new DivinityParticleEffect()));
                addToBot(new VFXAction(new DivinityParticleEffect()));
                addToBot(new VFXAction(new DivinityParticleEffect()));
            }
            AbstractMonster target = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
            AbstractDungeon.actionManager.addToBottom(new SFXAction("ORB_LIGHTNING_EVOKE"));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new LightningEffect(target.drawX,target.drawY)));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(target,new DamageInfo(AbstractDungeon.player,amount,
                    DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.NONE));
            amount = 0;
            updateDescription();
        }
    }


    // Note: If you want to apply an effect when a power is being applied you have 3 options:
    //onInitialApplication is "When THIS power is first applied for the very first time only."
    //onApplyPower is "When the owner applies a power to something else (only used by Sadistic Nature)."
    //onReceivePowerPower from StSlib is "When any (including this) power is applied to the owner."


    // At the end of the turn, remove gained Dexterity

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
    @Override
    public AbstractPower makeCopy() {
        return new DivineRetributionPower(owner, source);
    }

    @Override
    public void onReciveDamage(int damage) {
        amount += damage;
        updateDescription();
    }
}

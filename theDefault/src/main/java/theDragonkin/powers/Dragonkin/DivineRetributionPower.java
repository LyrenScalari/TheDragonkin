package theDragonkin.powers.Dragonkin;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.stance.DivinityParticleEffect;
import theDragonkin.CustomTags;
import theDragonkin.DragonkinMod;
import theDragonkin.powers.LosePowerPower;
import theDragonkin.util.Wiz;

public class DivineRetributionPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DragonkinMod.makeID("DivineRetribution");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public DivineRetributionPower(final AbstractCreature owner, final AbstractCreature source,int dmgamount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = dmgamount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;
        this.loadRegion("defenseNext");

        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        CardCrawlGame.sound.play("POWER_MANTRA", 0.05F);
        addToBot(new VFXAction(new DivinityParticleEffect()));
        addToBot(new VFXAction(new DivinityParticleEffect()));
        addToBot(new VFXAction(new DivinityParticleEffect()));
        addToBot(new DamageAction(owner,new DamageInfo(owner,amount, DamageInfo.DamageType.THORNS)));
        int blessingcount = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group){
            if (c.hasTag(CustomTags.Blessing)){
                blessingcount += 1;
            }
        }
        if (blessingcount > 0){
            Wiz.applyToSelfTemp(new DivineConvictionpower(owner,owner,blessingcount));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
    @Override
    public AbstractPower makeCopy() {
        return new DivineRetributionPower(owner, source,amount);
    }
}

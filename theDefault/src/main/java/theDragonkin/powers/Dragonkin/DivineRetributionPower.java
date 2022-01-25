package theDragonkin.powers.Dragonkin;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.stance.DivinityParticleEffect;
import theDragonkin.CustomTags;
import theDragonkin.DragonkinMod;
import theDragonkin.actions.SmiteAction;
import theDragonkin.cards.Dragonkin.interfaces.ReciveDamageEffect;
import theDragonkin.powers.LosePowerPower;
import theDragonkin.util.Wiz;

public class DivineRetributionPower extends AbstractPower implements CloneablePowerInterface, ReciveDamageEffect {
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
    public void atEndOfTurn(boolean isPlayer) {
        if (amount > 0) {
            CardCrawlGame.sound.play("POWER_MANTRA", 0.05F);
            AbstractMonster m = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster) null, true, AbstractDungeon.cardRandomRng);
            addToBot(new SmiteAction(m, new DamageInfo(owner, amount, DamageInfo.DamageType.THORNS)));
            amount = 0;
        }
    }
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(this.amount), x, y, this.fontScale, c);
    }
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
    @Override
    public AbstractPower makeCopy() {
        return new DivineRetributionPower(owner, source,amount);
    }

    @Override
    public void onReciveDamage(int damage) {
        if (!AbstractDungeon.actionManager.turnHasEnded){
         amount += damage*2;
        }
    }
}

package theDragonkin.powers.Dragonkin;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.DefaultMod;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DefaultMod.makePowerPath;

public class ShieldGlarepower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("ShieldGlare");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("ShieldGlare.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("ShieldGlare32.png"));

    public ShieldGlarepower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = true;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public int onAttacked(DamageInfo di, int d){
        if (di.type != DamageInfo.DamageType.THORNS && di.type != DamageInfo.DamageType.HP_LOSS && di.owner != null && di.owner != this.owner) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(di.owner,AbstractDungeon.player,new DelayStunpower(di.owner,AbstractDungeon.player,1)));
        }
        return d;
    }

    @Override
    public void atStartOfTurn (){
        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(AbstractDungeon.player,AbstractDungeon.player,this,amount));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new ShieldGlarepower(owner, source, amount);
    }
}

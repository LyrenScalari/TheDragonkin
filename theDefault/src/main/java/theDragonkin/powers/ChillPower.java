package theDragonkin.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.util.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.cards.Gremory.AbstractMagicGremoryCard;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DefaultMod.makePowerPath;

public class ChillPower extends AbstractPower implements OnReceivePowerPower , modifyMagicPower {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("Chill");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("Chill.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("Chill32.png"));

    public ChillPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }
    @Override
    public float modifyMagicCard(AbstractMagicGremoryCard c, float magicpower) {
        if (c.hasTag(CustomTags.Ice)) {
            return (magicpower + this.amount);
        }
        else return magicpower;
    }
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void onInitialApplication(){
        if (owner.hasPower(FreezePower.POWER_ID)){
            addToBot(new ReducePowerAction(owner,owner,ChillPower.POWER_ID,amount));
        }
    }

    @Override
    public boolean onReceivePower(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        if (abstractPower.ID.equals(ChillPower.POWER_ID) && abstractCreature == owner){
            if (owner.getPower(ChillPower.POWER_ID).amount >= 10){
                addToBot(new ReducePowerAction(owner,owner,ChillPower.POWER_ID,amount));
                addToBot(new ApplyPowerAction(owner,owner,new FreezePower(owner,owner)));
            }
        }
        return true;
    }
}

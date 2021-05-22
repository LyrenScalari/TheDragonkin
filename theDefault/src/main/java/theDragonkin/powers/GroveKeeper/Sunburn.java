package theDragonkin.powers.GroveKeeper;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import theDragonkin.CustomTags;
import theDragonkin.DragonkinMod;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DragonkinMod.makePowerPath;

public class Sunburn extends AbstractUpdatingTwoAmountPower {
    public AbstractCreature source;
    public static final String POWER_ID = DragonkinMod.makeID("Sunburn");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public Sunburn(final AbstractCreature owner, final AbstractCreature source,int amount) {
        super(owner,owner,amount);
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.source = source;
        type = PowerType.DEBUFF;
        isTurnBased = false;
        this.amount = amount;
        this.amount2 = (AbstractDungeon.player.getPower(AlignmentPower.POWER_ID).amount);
        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }
    @Override
    public void onUseCard(AbstractCard c, UseCardAction action){
        if (c.hasTag(CustomTags.Solar) && action.target == owner){
            addToBot(new GainBlockAction(AbstractDungeon.player,(AbstractDungeon.player.getPower(AlignmentPower.POWER_ID).amount)));
            addToBot(new ReducePowerAction(owner,owner,this,1));
        }
        updateDescription();
    }
    @Override
    public void UpdateAmount2 (){
        this.amount2 = (AbstractDungeon.player.getPower(AlignmentPower.POWER_ID).amount);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.amount2 = (AbstractDungeon.player.getPower(AlignmentPower.POWER_ID).amount);
        if (amount > 1) {
            description = DESCRIPTIONS[0] + AbstractDungeon.player.getPower(AlignmentPower.POWER_ID).amount + DESCRIPTIONS[1] +
                    amount + DESCRIPTIONS[3] + DESCRIPTIONS[4];
        } else {
            description = DESCRIPTIONS[0] + AbstractDungeon.player.getPower(AlignmentPower.POWER_ID).amount + DESCRIPTIONS[1] +
                    amount + DESCRIPTIONS[2] + DESCRIPTIONS[4];
        }
    }
}

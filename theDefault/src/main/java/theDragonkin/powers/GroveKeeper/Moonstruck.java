package theDragonkin.powers.GroveKeeper;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DefaultMod.makePowerPath;

public class Moonstruck extends AbstractUpdatingTwoAmountPower {
    public AbstractCreature source;
    public static final String POWER_ID = DefaultMod.makeID("Moonstruck");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public Moonstruck(final AbstractCreature owner, final AbstractCreature source,int amount) {
        super(owner,owner,amount);
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.source = source;
        type = PowerType.DEBUFF;
        isTurnBased = false;
        this.amount = amount;
        this.amount2 = (((TwoAmountPower)AbstractDungeon.player.getPower(AlignmentPower.POWER_ID)).amount2);
        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }
    @Override
    public void onUseCard(AbstractCard c, UseCardAction action){
        if (c.hasTag(CustomTags.Lunar)){
            addToBot(new LoseHPAction(owner,owner,((TwoAmountPower)AbstractDungeon.player.getPower(AlignmentPower.POWER_ID)).amount2));
            addToBot(new ReducePowerAction(owner,owner,this,1));
        }
        updateDescription();
    }
    @Override
    public void UpdateAmount2 (){
        this.amount2 = (((TwoAmountPower)AbstractDungeon.player.getPower(AlignmentPower.POWER_ID)).amount2);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.amount2 = (((TwoAmountPower)AbstractDungeon.player.getPower(AlignmentPower.POWER_ID)).amount2);
        if (amount > 1){
            description = DESCRIPTIONS[0] + (((TwoAmountPower)AbstractDungeon.player.getPower(AlignmentPower.POWER_ID)).amount2) + DESCRIPTIONS[1] +
                    amount + DESCRIPTIONS[3] + DESCRIPTIONS[4];
        } else {
            description = DESCRIPTIONS[0] + (((TwoAmountPower)AbstractDungeon.player.getPower(AlignmentPower.POWER_ID)).amount2) + DESCRIPTIONS[1] +
                    amount + DESCRIPTIONS[2] + DESCRIPTIONS[4];
        }

    }
}

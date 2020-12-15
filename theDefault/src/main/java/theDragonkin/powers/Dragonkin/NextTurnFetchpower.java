package theDragonkin.powers.Dragonkin;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.DefaultMod;
import theDragonkin.powers.Dragonkin.DivineConvictionpower;
import theDragonkin.util.TextureLoader;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.*;
import static theDragonkin.DefaultMod.makePowerPath;


public class NextTurnFetchpower extends AbstractPower implements NonStackablePower {
    public AbstractCreature source;
    AbstractPower p = AbstractDungeon.player.getPower(DivineConvictionpower.POWER_ID);
    public static final String POWER_ID = DefaultMod.makeID("NextTurnFetch");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public boolean F = false;
    public static AbstractCard.CardType targettype = null;
    private boolean R;
    private boolean Ro;


    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("NextTurnFetch.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("NextTurnFetch32.png"));

    public NextTurnFetchpower(final AbstractCreature owner, final AbstractCreature source, final int amount, final AbstractCard.CardType passedType, final boolean rand, final boolean remove) {
        name = NAME;
        ID = POWER_ID;
        targettype = passedType;
        R = rand;
        Ro = remove;
        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = AbstractPower.PowerType.BUFF;
        isTurnBased = false;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public NextTurnFetchpower(final  AbstractCreature owner, final AbstractCreature source,final int amount,final boolean rand,final boolean remove){
        name = NAME;
        ID = POWER_ID;
        R = rand;
        Ro = remove;
        F = true;
        this.owner = owner;
        this.amount = amount;
        this.source = source;
        type = AbstractPower.PowerType.BUFF;
        isTurnBased = false;
        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);


        updateDescription();

        if (AbstractDungeon.cardRandomRng.randomBoolean()){
            targettype = ATTACK;
        } else {
            targettype = SKILL;
        }

    }



    @Override
    public void atStartOfTurnPostDraw() {
        this.flash();
        if (R) {
            this.addToBot(new DrawPileToHandAction(this.amount, targettype));
        } else if (!F) {
            this.addToBot(new FetchAction(AbstractDungeon.player.drawPile, c -> {return c.type == targettype;} , this.amount));
        }
        else if (F) {
            this.addToBot(new FetchAction(AbstractDungeon.player.drawPile, c -> {return c.type == ATTACK || c.type == SKILL;} , this.amount));
        }
        if (Ro) {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }



    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount;
        if (R) description += DESCRIPTIONS[1];

        if (F) {
            if (this.amount == 1) {
                description += DESCRIPTIONS[6];
            } else {
                description += DESCRIPTIONS[7];
            }
        }
        else if (targettype == ATTACK) {
            if (this.amount == 1) {
                description += DESCRIPTIONS[2];
            } else {
                description += DESCRIPTIONS[3];
            }
        } else if (targettype == SKILL) {
            if (this.amount == 1) {
                description += DESCRIPTIONS[4];
            } else {
                description += DESCRIPTIONS[5];
            }
        } else if (targettype == POWER){
            if (this.amount == 1) {
                description += DESCRIPTIONS[9];
            } else {
                description += DESCRIPTIONS[10];
            }
        }

        description += DESCRIPTIONS[8];
    }
}

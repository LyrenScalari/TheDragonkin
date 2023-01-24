package theDragonkin.powers.Dragonkin;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.util.TextureLoader;
import static theDragonkin.DragonkinMod.makeID;
import static theDragonkin.DragonkinMod.makePowerPath;

public class BorrowedTimePower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("BorrowedTime");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    //The only thing this controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if they're a buff or debuff.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("CrusaderForm.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("CrusaderForm32.png"));
    public BorrowedTimePower(AbstractCreature owner, int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;

        type = TYPE;
        isTurnBased = false;

        // We load those textures here.
        this.loadRegion("time");
    }

    @Override
    public void renderIcons(SpriteBatch sb, float x, float y, Color c) {
        super.renderIcons(sb, x, y, Color.RED.cpy());
    }
    public void updateDescription() {
        this.description = DESCRIPTIONS[0]+amount+DESCRIPTIONS[1];
    }
    public void atStartOfTurnPostDraw() {
        addToBot(new RemoveSpecificPowerAction(owner,owner,this));
    }
    public void onInitialApplication() {
        AbstractDungeon.player.gameHandSize -= this.amount;
    }
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        AbstractDungeon.player.gameHandSize -= this.amount;
    }
    public void onRemove() {
        AbstractDungeon.player.gameHandSize += this.amount;
    }
    //Optional, for CloneablePowerInterface.
    @Override
    public AbstractPower makeCopy() {
        return new BorrowedTimePower(owner, amount);
    }
}
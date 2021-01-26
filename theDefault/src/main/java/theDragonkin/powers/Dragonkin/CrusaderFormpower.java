package theDragonkin.powers.Dragonkin;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.cards.Dragonkin.AbstractHolyCard;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DefaultMod.makePowerPath;

public class CrusaderFormpower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;
    AbstractPower p = AbstractDungeon.player.getPower(DivineConvictionpower.POWER_ID);
    public static final String POWER_ID = DefaultMod.makeID("CrusaderFormpower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public int MinimumZeal = 0;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("CrusaderForm.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("CrusaderForm32.png"));

    public CrusaderFormpower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if (!(card instanceof AbstractHolyCard)) {
            this.flash();
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner,
                        new DivineConvictionpower(owner, owner, amount), amount));
            }

        }

    @Override
    public void onInitialApplication(){
    }


    @Override
    public void atStartOfTurn(){
        if (p != null){
            MinimumZeal = AbstractDungeon.player.getPower(DivineConvictionpower.POWER_ID).amount;}
    }
    @Override
    public void atEndOfTurn(final boolean isPlayer) {
        int count = 0;
        for (final AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
            if (!(c instanceof AbstractHolyCard)) {
                ++count; // At the end of your turn, increase the count by 1 for each card that gave Zeal played this turn
            }
        }

        if (count  > 0) {
            flash(); // Makes the power icon flash.
            for (int i = 0; i < count ; ++i) {
                AbstractDungeon.actionManager.addToBottom(
                        new ReducePowerAction(owner, owner, DivineConvictionpower.POWER_ID, amount));
            }
        }
    }

    @Override
    public void updateDescription() {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];

    }

    @Override
    public AbstractPower makeCopy() {
        return new CrusaderFormpower(owner, source, amount);
    }
}

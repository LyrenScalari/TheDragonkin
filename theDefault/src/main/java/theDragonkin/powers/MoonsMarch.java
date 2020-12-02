package theDragonkin.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theDragonkin.DefaultMod;
import theDragonkin.cards.Gremory.AbstractMagicGremoryCard;
import theDragonkin.CustomTags;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DefaultMod.makePowerPath;
import static theDragonkin.cards.Gremory.AbstractMagicGremoryCard.AllCards;

public class MoonsMarch extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("MoonsMarch");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("Agony.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("Agony32.png"));

    public MoonsMarch(final AbstractCreature owner, final AbstractCreature source) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.source = source;
        this.amount = -1;
        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }
    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card instanceof AbstractMagicGremoryCard){
            if (card.misc <= 1){
                addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,
                        new StrengthPower(AbstractDungeon.player,3),3));
            }
        }
    }

    @Override
    public void onRemove(){
        for (AbstractCard c : AllCards.group) {
            if (c.hasTag(CustomTags.Physical)) {
                c.cost += 1;
            }
        }
    }

    @Override
    public void onInitialApplication() {
        for (AbstractCard c : AllCards.group) {
            if (c.hasTag(CustomTags.Physical)) {
                c.cost -= 1;
            }
        }
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p.ID.equals(MoonsMarch.POWER_ID) && p != this) {
                addToBot(new RemoveSpecificPowerAction(owner, owner, p));
            }
            if (p.ID.equals(WindsSong.POWER_ID)) {
                addToBot(new RemoveSpecificPowerAction(owner, owner, p));
            }
            if (p.ID.equals(FlowersAmbition.POWER_ID)) {
                addToBot(new RemoveSpecificPowerAction(owner, owner, p));
            }
            if (p.ID.equals(ImmaculateSnow.POWER_ID)) {
                addToBot(new RemoveSpecificPowerAction(owner, owner, p));
            }
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}

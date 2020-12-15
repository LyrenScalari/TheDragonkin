package theDragonkin.powers.Gremory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.DefaultMod;
import theDragonkin.CustomTags;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DefaultMod.makePowerPath;
import static theDragonkin.cards.Gremory.AbstractMagicGremoryCard.AllCards;

public class WindsSong extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("WindsSong");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("Agony.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("Agony32.png"));

    public WindsSong(final AbstractCreature owner, final AbstractCreature source) {
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
    public void onRemove(){
        for (AbstractCard c : AllCards.group) {
            if (c.hasTag(CustomTags.Wind)) {
                c.cost += 1;
            }
        }
    }

    @Override
    public void onInitialApplication() {
        for (AbstractCard c : AllCards.group) {
            if (c.hasTag(CustomTags.Wind) && c.cost > 0) {
                c.cost -= 1;
            }
        }
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p.ID.equals(MoonsMarch.POWER_ID)) {
                addToBot(new RemoveSpecificPowerAction(owner, owner, p));
            }
            if (p.ID.equals(WindsSong.POWER_ID) && p != this) {
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

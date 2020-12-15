package theDragonkin.powers.Gremory;

import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.CardMods.BlutgangEnchantment;
import theDragonkin.DefaultMod;
import theDragonkin.cards.Gremory.AbstractMagicGremoryCard;
import theDragonkin.CustomTags;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DefaultMod.makePowerPath;

public class Blutgang extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("Blutgang");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("Blutgang.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("Blutgang32.png"));

    public Blutgang(final AbstractCreature owner, final AbstractCreature source) {
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
    public void atStartOfTurnPostDraw() {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() { addToBot(new SelectCardsInHandAction(1,"Enchant",false,true, c -> !(c.hasTag(CustomTags.Enchanted) || c instanceof AbstractMagicGremoryCard),
            list -> list.forEach(Blutgang.this::enchant)));
            isDone = true;
            }
        });
    }

    public AbstractCard enchant(AbstractCard c){

        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                CardModifierManager.addModifier(c, new BlutgangEnchantment());
                isDone = true;
            }
        });
        if (StSLib.getMasterDeckEquivalent(c) != null) {
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    CardModifierManager.addModifier(StSLib.getMasterDeckEquivalent(c), new BlutgangEnchantment());
                    isDone = true;
                }
            });
        }
        return c;
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}

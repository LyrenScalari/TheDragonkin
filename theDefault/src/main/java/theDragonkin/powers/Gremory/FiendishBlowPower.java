package theDragonkin.powers.Gremory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.cards.Gremory.AbstractMagicGremoryCard;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DefaultMod.makePowerPath;

public class FiendishBlowPower extends AbstractPower implements modifyMagicPower {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("FiendishBlow");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("FiendishBlow.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("FiendishBlow32.png"));

    public FiendishBlowPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
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
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if (card instanceof AbstractMagicGremoryCard && card.type != AbstractCard.CardType.POWER) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner,this.source,this));
        }
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard c) {
        if (c.hasTag(CustomTags.Enchanted)) {
            return type == DamageInfo.DamageType.NORMAL ? damage + (float) this.amount : damage;
        }
        else return damage;
    }
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[3];
    }

    @Override
    public float modifyMagicCard(AbstractMagicGremoryCard c, float magicpower) {
            return magicpower + this.amount;
    }
}

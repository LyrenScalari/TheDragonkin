package theDragonkin.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.cards.Gremory.AbstractMagicGremoryCard;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DefaultMod.makePowerPath;

public class FreezePower extends AbstractPower implements modifyMagicPower{
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("Freeze");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("Freeze.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("Freeze32.png"));

    public FreezePower(final com.megacrit.cardcrawl.core.AbstractCreature owner, final AbstractCreature source) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = 0;
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
            if (c.damageTypeForTurn == DamageInfo.DamageType.NORMAL){
                return magicpower * 0;
            }
            else if(c.upgraded){
                return magicpower * 2;
            }
            else {return magicpower;}

        }
        else {return magicpower;}
    }
    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType dt, AbstractCard card) {
        if (!(card instanceof AbstractMagicGremoryCard)) {
            addToBot(new ReducePowerAction(owner,owner,this.ID,amount));
            return damage * 3;
        }
        else return damage;
    }
}

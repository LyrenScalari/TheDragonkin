package theDragonkin.powers.GroveKeeper;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.DragonkinMod;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DragonkinMod.makePowerPath;

public class EntangledPower  extends AbstractPower {
    public AbstractCreature source;
    public static final String POWER_ID = DragonkinMod.makeID("EntangledPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public EntangledPower(final AbstractCreature owner, final AbstractCreature source,int amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.source = source;
        type = PowerType.DEBUFF;
        isTurnBased = false;
        this.amount = amount;
        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        updateDescription();
    }

    @Override
    public void atStartOfTurn(){
        if (((AbstractMonster) owner).getIntentBaseDmg() >= 0){
            addToBot(new StunMonsterAction(((AbstractMonster) owner),owner,1));
        }
        addToBot(new ReducePowerAction(owner,owner,this,1));
    }

    @Override
    public void updateDescription() {
    }
}

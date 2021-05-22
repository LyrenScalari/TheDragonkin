package theDragonkin.powers.Dragonkin;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theDragonkin.DragonkinMod;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DragonkinMod.makePowerPath;

public class NecroticAura extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = DragonkinMod.makeID("NecroticAura");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("Agony.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("Agony32.png"));

    public NecroticAura(final AbstractCreature owner, final AbstractCreature source, final int amount) {
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
    public void onApplyPower(AbstractPower p, AbstractCreature t, AbstractCreature s){
        if (p.type == PowerType.DEBUFF && !p.ID.equals("Shackled") && source == this.owner && t!= this.owner && !t.hasPower("Artifact")) {
            addToBot(new ApplyPowerAction(t,t,new StrengthPower(t,-p.amount)));
            addToBot(new ApplyPowerAction(t,t,new GainStrengthPower(t,p.amount)));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}

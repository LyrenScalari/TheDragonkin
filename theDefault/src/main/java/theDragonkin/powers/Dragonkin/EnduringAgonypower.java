package theDragonkin.powers.Dragonkin;


import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.DefaultMod;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DefaultMod.makePowerPath;

public class EnduringAgonypower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("Agony");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("Agony.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("Agony32.png"));

    public EnduringAgonypower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
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
            if (p.ID == Scorchpower.POWER_ID){
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(t,t,new AcidMarkPower(t,t,amount)));
            }
            if (p.ID == AcidMarkPower.POWER_ID){
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(t,t,new Scorchpower(t,t,amount)));
            }
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[3];
    }
    @Override
    public AbstractPower makeCopy() {
        return new EnduringAgonypower(owner, source, amount);
    }
}


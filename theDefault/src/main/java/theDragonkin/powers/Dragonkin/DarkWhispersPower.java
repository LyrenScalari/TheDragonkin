package theDragonkin.powers.Dragonkin;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.DefaultMod;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DefaultMod.makePowerPath;

public class DarkWhispersPower extends TwoAmountPower implements OnReceivePowerPower {
    public AbstractCreature source;
    AbstractPower p = AbstractDungeon.player.getPower(DivineConvictionpower.POWER_ID);
    public static final String POWER_ID = DefaultMod.makeID("DarkWhispers");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public int delay = 2;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("Stagger.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("Stagger32.png"));

    public DarkWhispersPower(final AbstractCreature owner, final AbstractCreature source, final int amount1, int amount2) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount1;
        this.amount2 = amount2;
        this.source = source;
        this.loadRegion("corruption");
        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }
    public void onExhaust(AbstractCard card) {
        addToBot(new GainEnergyAction(amount));
        this.flash();
    }
    public void atStartOfTurnPostDraw() {
        amount2 -= 1;
        if (amount2 < 1){
            addToBot(new RemoveSpecificPowerAction(owner,owner,this));
        }
    }
    @Override
    public void updateDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(powerStrings.DESCRIPTIONS[0]);
        sb.append(amount2);
       if (amount2 < 2){
           sb.append(DESCRIPTIONS[2]);
       } else {
           sb.append(DESCRIPTIONS[1]);
       }
        sb.append(DESCRIPTIONS[3]);
        for(int i = 0; i < this.amount; ++i) {
            sb.append("[E] ");
        }
        this.description = sb.toString();
    }

    @Override
    public boolean onReceivePower(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        if (abstractPower.ID.equals(this.ID)){
            amount2 += ((TwoAmountPower)abstractPower).amount2;
        }
        return true;
    }
}

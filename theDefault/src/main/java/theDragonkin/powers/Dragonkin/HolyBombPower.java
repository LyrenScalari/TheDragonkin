package theDragonkin.powers.Dragonkin;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.DragonkinMod;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DragonkinMod.makePowerPath;

public class HolyBombPower extends TwoAmountPower implements NonStackablePower {
    public AbstractCreature source;

    public static final String POWER_ID = DragonkinMod.makeID("HolyInfusion");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("AcidArmor.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("AcidArmor32.png"));

    public HolyBombPower(final AbstractCreature owner, final AbstractCreature source, final int Delay, final int Potency) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = Delay;
        this.amount2 = Potency;
        this.source = source;

        type = AbstractPower.PowerType.BUFF;
        isTurnBased = true;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }
    @Override
    public void onApplyPower(AbstractPower p, AbstractCreature t, AbstractCreature s){
    }
    @Override
    public void atEndOfTurn(final boolean isPlayer){
        if (this.amount == 1){
            addToBot(new DamageAllEnemiesAction(owner, DamageInfo.createDamageMatrix(amount2,true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
        }
       addToBot(new ReducePowerAction(AbstractDungeon.player,AbstractDungeon.player,this,1));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount2 + DESCRIPTIONS[2];

    }
}


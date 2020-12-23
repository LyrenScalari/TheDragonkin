package theDragonkin.powers.Dragonkin;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.IceShatterEffect;
import theDragonkin.DefaultMod;
import theDragonkin.cards.Dragonkin.ObsidianShard;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DefaultMod.makePowerPath;

public class DarkConvictionPower extends AbstractVoidPower {

    public AbstractCreature source;
    AbstractPower p = AbstractDungeon.player.getPower(DivineConvictionpower.POWER_ID);
    public static final String POWER_ID = DefaultMod.makeID("DarkConviction");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public int MinimumZeal = 0;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("CrusaderForm.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("CrusaderForm32.png"));

    public DarkConvictionPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = AbstractPower.PowerType.BUFF;
        isTurnBased = false;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {

    }

    @Override
    public void onInitialApplication(){
    }


    @Override
    public void atStartOfTurn(){
    }

    @Override
    public void atEndOfTurn(final boolean isPlayer) {
    }

    @Override
    public void onExhaust(AbstractCard card) {
        this.flash();
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new StrengthPower(AbstractDungeon.player,amount),amount));
    }
    @Override
    public void onEnergyChanged(int e) {
        if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID)) {
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
                addToBot(new LoseHPAction(m,m,AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount, AbstractGameAction.AttackEffect.FIRE));
            }
        } else {
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
                addToBot(new LoseHPAction(m,m,1, AbstractGameAction.AttackEffect.FIRE));
            }
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];

    }
}



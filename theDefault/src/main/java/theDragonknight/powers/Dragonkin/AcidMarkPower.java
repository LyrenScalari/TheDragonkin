package theDragonknight.powers.Dragonkin;


import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonknight.CustomTags;
import theDragonknight.DragonkinMod;
import theDragonknight.relics.Dragonkin.MukySludge;
import theDragonknight.util.TextureLoader;

import static theDragonknight.DragonkinMod.makePowerPath;

public class AcidMarkPower extends AbstractPower implements CloneablePowerInterface, HealthBarRenderPower {
    public AbstractCreature source;

    public static final String POWER_ID = DragonkinMod.makeID("Dissolve");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("Dissolve.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("Dissolve32.png"));

    public AcidMarkPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.DEBUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void triggerMarks(AbstractCard card) {
        if (card.hasTag(CustomTags.Acid_Activator)) {
            AbstractDungeon.actionManager.addToTop(new LoseHPAction(this.owner, (AbstractCreature) null, this.amount));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
    @Override
    public AbstractPower makeCopy() {
        return new AcidMarkPower(owner, source, amount);
    }

    @Override
    public int getHealthBarAmount() {
        int eotGlow = 0;
        boolean showEoTGlow = false;
        if (AbstractDungeon.player.hasRelic(MukySludge.ID)){
            showEoTGlow = true;
        }
        if (AbstractDungeon.player.hoveredCard != null) {
            if (AbstractDungeon.player.hoveredCard.hasTag(CustomTags.Acid_Activator)) {
                if (!AbstractDungeon.player.hoveredCard.hasTag(CustomTags.Acid_Applicator)) {
                    if (AbstractDungeon.player.hoveredCard.target == AbstractCard.CardTarget.ENEMY && !(owner.hb.hovered)) {
                        if (showEoTGlow) {
                            return eotGlow + amount;
                        } else return 0;
                    } else return amount;
                } else if (AbstractDungeon.player.hoveredCard.target == AbstractCard.CardTarget.ENEMY && !(owner.hb.hovered)) {
                    if (showEoTGlow) {
                        return eotGlow + amount;
                    } else return 0;
                } else return amount + AbstractDungeon.player.hoveredCard.magicNumber;
            }
        }
        if (showEoTGlow) {
            return eotGlow + amount;
        } else return 0;
    }

    @Override
    public Color getColor() {
        return CardHelper.getColor(154,219,22);
    }
}

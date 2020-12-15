package theDragonkin.powers.GroveKeeper;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.util.TextureLoader;

import static theDragonkin.DefaultMod.makePowerPath;

public class AlignmentPower extends TwoAmountPower {
        public AbstractCreature source;
        public static final String POWER_ID = DefaultMod.makeID("AlignmentPower");
        private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        public static final String NAME = powerStrings.NAME;
        public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

        private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
        private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

        public AlignmentPower(final AbstractCreature owner, final AbstractCreature source,int amount, boolean Solar) {
            name = NAME;
            ID = POWER_ID;
            this.owner = owner;
            this.source = source;
            type = PowerType.BUFF;
            isTurnBased = false;
            if (Solar){
                this.amount = amount;
                this.amount2 = 0;
            } else this.amount2 = amount; this.amount = 0;
            // We load those txtures here.
            this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
            this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

            updateDescription();
        }

    public void ConvertAlignment(boolean half){
        if (amount < amount2 && half){
            amount = amount2 / 2;
            amount2 = 0;
        } else {
            amount2 = amount / 2;
            amount = 0;
        }
        if (amount < amount2 && !half){
            amount = amount2;
            amount2 = 0;
        } else {
            amount2 = amount;
            amount = 0;
        }
    }

    @Override
    public void onUseCard(AbstractCard c, UseCardAction action){
            if (c.hasTag(CustomTags.Solar)){
                if (amount < amount2){
                    amount2 -= 1;
                } else {
                    amount += 1;
                }
            } else if (c.hasTag(CustomTags.Lunar)){
                if (amount2 < amount){
                    amount -= 1;
                } else {
                    amount2 += 1;
                }
            }
            updateDescription();
    }
        @Override
        public void updateDescription() {
            if (amount >= 2){
                description = DESCRIPTIONS[0] + DESCRIPTIONS[2] + amount;
            }else if (amount2 >= 2){
                description = DESCRIPTIONS[0] + DESCRIPTIONS[3] + amount2;
            }else if (amount > amount2){
                description = DESCRIPTIONS[0] + DESCRIPTIONS[1] + amount;
            } else if (amount < amount2) {
                description = DESCRIPTIONS[0] + DESCRIPTIONS[4] + amount2;
            } else description = DESCRIPTIONS[0] + DESCRIPTIONS[5];
        }
}

package theDragonknight.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.optionCards.BecomeAlmighty;
import com.megacrit.cardcrawl.cards.optionCards.FameAndFortune;
import com.megacrit.cardcrawl.cards.optionCards.LiveForever;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import theDragonknight.DragonknightMod;
import theDragonknight.cards.Dragonknight.Token.*;
import theDragonknight.util.TextureLoader;

import java.util.ArrayList;

import static theDragonknight.DragonknightMod.makePowerPath;

public class ProfaneDivitation extends TwoAmountPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DragonknightMod.makeID("ProfaneDivitation");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static ArrayList<AbstractCard> DivinedType = new ArrayList<>();

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public ProfaneDivitation(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        amount2 = 1;
        this.source = source;
        priority = 0;
        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.loadRegion("mantra");

        updateDescription();
    }
    public void atStartOfTurnPostDraw() {
        DivinedType.clear();
        ArrayList<AbstractCard> stanceChoices = new ArrayList();
        stanceChoices.add(new DivinationAttack());
        stanceChoices.add(new DivinationSkill());
        stanceChoices.add(new DivinationPower());
        stanceChoices.add(new DivinationStatus());
        stanceChoices.add(new DivinationCurse());
        addToBot(new ChooseOneAction(stanceChoices));

        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                    AbstractCard DivinedCard = AbstractDungeon.player.drawPile.getTopCard();
                    AbstractDungeon.player.draw(1);
                    if (DivinedCard.type == DivinedType.get(0).type) {
                        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                            if (!m.isDeadOrEscaped()) {
                                addToBot(new DamageAction(m, new DamageInfo(owner, amount, DamageInfo.DamageType.THORNS), AttackEffect.POISON));
                            }
                        }
                    } else {
                        addToBot(new ExhaustSpecificCardAction(DivinedCard, AbstractDungeon.player.hand));
                    }
                    isDone = true;
            }
        });
    }
    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        if (amount2 > 1){
            description = DESCRIPTIONS[0] + DESCRIPTIONS[2] + DESCRIPTIONS[3] + amount + DESCRIPTIONS[4];
        } else description = DESCRIPTIONS[0] + DESCRIPTIONS[1] + DESCRIPTIONS[3] + amount + DESCRIPTIONS[4];

    }

    @Override
    public AbstractPower makeCopy() {
        return new RotPower(owner, source, amount);
    }
}
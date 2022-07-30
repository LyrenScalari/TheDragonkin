package theDragonkin.orbs;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.powers.NoDrawPower;
import theDragonkin.CustomTags;
import theDragonkin.DragonkinMod;
import theDragonkin.util.AbstractRune;

public class DestructionGlyph extends AbstractRune {
    public static final String ORB_ID = DragonkinMod.makeID("Destruction");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;
    public DestructionGlyph(int Pow, int Pain){
        super();
        Sealstrings = orbString;
        PainAmount = Pain;
        name = orbString.NAME;
        RuneText = DESCRIPTIONS[5];
        BreakAmount = Pow;
        updateAnimation();
    }
    public void onManualDiscard() {
        PainAmount -= 1;
        AbstractRune that = this;
        if (PainAmount <= 0){
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(BreakAmount,true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
            AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                @Override
                public void update() {
                    DragonkinMod.Seals.remove(that);
                    isDone = true;
                }
            });
        }
    }
    public void updateDescription() {
        description = DESCRIPTIONS[0] + PainAmount + DESCRIPTIONS[1] + DESCRIPTIONS[2] + PainAmount + DESCRIPTIONS[3] + BreakAmount + DESCRIPTIONS[4];
    }
}
package theDragonkin.CardMods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.FlyingOrbEffect;

import static theDragonkin.cards.Gremory.AbstractMagicGremoryCard.AllCards;

public class AbraxasCardMod extends AfterglowCardMod {
    private int uses;
    private int stacks;
    private AbstractMonster Target;

    public AbraxasCardMod(int uses, int bonus, AbstractMonster target) {
        super(uses, bonus);
        stacks = bonus;
        this.Target = target;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target,AbstractDungeon.player,new StrengthPower(Target,-stacks)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target,AbstractDungeon.player,new GainStrengthPower(Target,stacks)));
        for (AbstractCard c : AllCards.group) {
            for (AbstractCardModifier m : CardModifierManager.modifiers(c)) {
                AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                    @Override
                    public void update() {
                        if (m instanceof AfterglowCardMod) {
                            CardModifierManager.removeSpecificModifier(c, m, true);
                            AbraxasCardMod.super.removeOnCardPlayed(c);
                            isDone = true;
                        }
                        isDone = true;
                    }
                });
            }
        }
    }
}

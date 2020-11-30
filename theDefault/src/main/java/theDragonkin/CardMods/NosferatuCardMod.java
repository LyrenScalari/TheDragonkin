package theDragonkin.CardMods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlyingOrbEffect;

import static theDragonkin.cards.Gremory.AbstractMagicGremoryCard.AllCards;

public class NosferatuCardMod extends AfterglowCardMod {
    private int uses;
    private int stacks;

    public NosferatuCardMod(int uses, int bonus) {
        super(uses, bonus);
        stacks = bonus;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        AbstractDungeon.actionManager.addToBottom(new VFXAction( new FlyingOrbEffect(target.hb_x,target.hb_y)));
        AbstractDungeon.actionManager.addToBottom(new HealAction(AbstractDungeon.player, AbstractDungeon.player, stacks));
        for (AbstractCard c : AllCards.group) {
            for (AbstractCardModifier m : CardModifierManager.modifiers(c)) {
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                        if (m instanceof AfterglowCardMod) {
                            CardModifierManager.removeSpecificModifier(c, m, true);
                            NosferatuCardMod.super.removeOnCardPlayed(c);
                            isDone = true;
                        }
                        isDone = true;
                    }
                });
            }
        }
    }
}



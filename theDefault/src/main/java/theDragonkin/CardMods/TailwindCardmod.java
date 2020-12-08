package theDragonkin.CardMods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theDragonkin.cards.Gremory.AbstractMagicGremoryCard;

public class TailwindCardmod extends AbstractCardModifier {
    private final int cost;
    private int uses;
    private int bonus;

    public TailwindCardmod(int uses, int cost, int GaleforceBonus) {
        this.uses = uses;
        this.cost = cost;
        this.bonus = GaleforceBonus;
    }


    @Override
    public boolean removeOnCardPlayed(AbstractCard card) {
        --uses;
        return uses == 0;
    }

    @Override
    public void onInitialApplication(AbstractCard card){
        card.setCostForTurn(card.costForTurn - this.cost);
    }

    @Override
    public void onRemove(AbstractCard card){
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                card.setCostForTurn(card.costForTurn + TailwindCardmod.this.cost);
                isDone = true;
            }
        });
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            for (AbstractCardModifier m : CardModifierManager.modifiers(c)) {
                AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                    @Override
                    public void update() {
                        if (m instanceof TailwindCardmod) {
                            CardModifierManager.removeSpecificModifier(c, m, true);
                            isDone = true;
                        }
                        isDone = true;
                    }
                });
            }
        }
    }

    public void atTurnStart(AbstractCard card, CardGroup group) {
        card.setCostForTurn(card.costForTurn - this.cost);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new TailwindCardmod(uses, cost, bonus);
    }
}

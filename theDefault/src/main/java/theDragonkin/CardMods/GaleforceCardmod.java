package theDragonkin.CardMods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.CustomTags;
import theDragonkin.cards.Gremory.AbstractMagicGremoryCard;

public class GaleforceCardmod extends AbstractCardModifier {
    private int uses;
    private int damage;
    private int bonus;

    public GaleforceCardmod(int uses, int damage, int bonus) {
        this.uses = uses;
        this.damage = damage;
        this.bonus = bonus;
    }

    @Override
    public boolean removeOnCardPlayed(AbstractCard card) {
        --uses;
        return uses == 0;
    }

    public void onInitialApplication(AbstractCard card) {
        ((AbstractMagicGremoryCard) card).baseMagDamage += bonus;

        card.initializeDescription();
    }

    @Override
    public void onRemove(AbstractCard card) {
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                ((AbstractMagicGremoryCard) card).baseMagDamage -= bonus;
                card.initializeDescription();
                isDone = true;
            }
        });
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                @Override
                public void update() {
                    for (AbstractCardModifier m : CardModifierManager.modifiers(c)) {
                        if (m instanceof GaleforceCardmod) {
                            CardModifierManager.removeSpecificModifier(c, m, true);
                            isDone = true;
                        }
                    }
                    isDone=true;
                }
            });
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new GaleforceCardmod(uses, damage, bonus);
    }

    @Override
    public void atEndOfTurn(AbstractCard card, CardGroup group) {
    }
}


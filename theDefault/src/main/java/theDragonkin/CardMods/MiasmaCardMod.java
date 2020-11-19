package theDragonkin.CardMods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theDragonkin.powers.ResistancePower;

import static theDragonkin.cards.Gremory.AbstractMagicGremoryCard.AllCards;

public class MiasmaCardMod extends DarkenCardMod {
    private int uses;
    private int stacks;
    private int time;
    public MiasmaCardMod(int uses,int bonus,int time) {
        super(uses,bonus);
        this.uses = uses;
        this.time = time;
        this.stacks = bonus;
    }
    @Override
    public boolean removeOnCardPlayed(AbstractCard card) {
        --uses;
        return uses == 0;
    }
    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, AbstractDungeon.player, new ResistancePower(target, stacks, time), stacks));
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractCard c : AllCards.group) {
                    for (AbstractCardModifier m : CardModifierManager.modifiers(c)) {
                        if (m instanceof AfterglowCardMod) {
                            CardModifierManager.removeSpecificModifier(c, m, true);
                            MiasmaCardMod.super.removeOnCardPlayed(c);
                            isDone = true;
                        }
                        isDone = true;
                    }
                }
                isDone= true;
            }
        });
    }
}

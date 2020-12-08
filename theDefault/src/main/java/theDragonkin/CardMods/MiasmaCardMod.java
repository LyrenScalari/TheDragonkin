package theDragonkin.CardMods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.CollectorCurseEffect;
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
        if (card.target != AbstractCard.CardTarget.ALL_ENEMY) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, AbstractDungeon.player, new ResistancePower(target, stacks, time), stacks));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new CollectorCurseEffect(target.drawX, target.drawY)));
            for (AbstractCard c : AllCards.group) {
                for (AbstractCardModifier m : CardModifierManager.modifiers(c)) {
                    AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                        @Override
                        public void update() {
                            if (m instanceof DarkenCardMod) {
                                CardModifierManager.removeSpecificModifier(c, m, true);
                                MiasmaCardMod.super.removeOnCardPlayed(c);
                                isDone = true;
                            }
                            isDone = true;
                        }
                    });
                }
            }
        }
        else {
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new ResistancePower(m, stacks, time), stacks));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new CollectorCurseEffect(m.drawX, m.drawY)));
            }
            for (AbstractCard c : AllCards.group) {
                for (AbstractCardModifier mo : CardModifierManager.modifiers(c)) {
                    AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                        @Override
                        public void update() {
                            if (mo instanceof DarkenCardMod) {
                                CardModifierManager.removeSpecificModifier(c, mo, true);
                                MiasmaCardMod.super.removeOnCardPlayed(c);
                                isDone = true;
                            }
                            isDone = true;
                        }
                    });
                }
            }
        }
    }
}

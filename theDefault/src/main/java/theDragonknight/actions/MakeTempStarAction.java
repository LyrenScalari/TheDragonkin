package theDragonknight.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theDragonknight.DragonknightMod;

public class MakeTempStarAction extends AbstractGameAction {
    int amt;
    public MakeTempStarAction(int Amt) {
        amt = Amt;
    }

    @Override
    public void update() {
        for (int i = 0; i < amt; i++) {
            AbstractCard StarToGenerate = DragonknightMod.Stars.get(AbstractDungeon.cardRandomRng.random(0, DragonknightMod.Stars.size()-1));
            addToBot(new MakeTempCardInHandAction(StarToGenerate, 1));
        }
        isDone = true;
    }
}

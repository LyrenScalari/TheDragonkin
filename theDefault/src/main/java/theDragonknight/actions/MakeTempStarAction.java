package theDragonknight.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theDragonknight.DragonkinMod;

public class MakeTempStarAction extends AbstractGameAction {
    int amt;
    public MakeTempStarAction(int Amt) {
        amt = Amt;
    }

    @Override
    public void update() {
        for (int i = 0; i < amt; i++) {
            AbstractCard StarToGenerate = DragonkinMod.Stars.get(AbstractDungeon.cardRandomRng.random(0, DragonkinMod.Stars.size()-1));
            addToBot(new MakeTempCardInHandAction(StarToGenerate, 1));
        }
        isDone = true;
    }
}

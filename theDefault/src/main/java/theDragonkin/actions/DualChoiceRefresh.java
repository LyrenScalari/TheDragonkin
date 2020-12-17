package theDragonkin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.cards.GroveKeeper.AbstractChooseOneCard;
import theDragonkin.cards.GroveKeeper.Choices.FindtheSecret;
import theDragonkin.cards.GroveKeeper.Choices.KickintheDoor;
import theDragonkin.cards.GroveKeeper.Choices.LoottheRoom;
import theDragonkin.cards.GroveKeeper.Skills.BranchingPaths;

import javax.smartcardio.Card;
import java.util.ArrayList;


public class DualChoiceRefresh extends AbstractGameAction {
    public ArrayList Group;
    public AbstractMonster Target;
    public AbstractCard Callingcard;

    public DualChoiceRefresh(ArrayList group, AbstractMonster target, AbstractCard callingcard) {
        Group = group;
        Target = target;
        Callingcard = callingcard;
    }

    @Override
    public void update() {
        if (Callingcard.cardID.equals(BranchingPaths.ID)){
            ArrayList<AbstractCard> NextChoices = new ArrayList<>();
            NextChoices.add(new KickintheDoor(Callingcard.damage, Target));
            NextChoices.add(new LoottheRoom(Callingcard.block));
            NextChoices.add(new FindtheSecret(Callingcard.magicNumber));
            for (AbstractCard c : NextChoices){
                c.applyPowers();
            }
            AbstractChooseOneCard.lastchoice.clear();
            AbstractChooseOneCard.Roadsuntraveled.clear();
            AbstractChooseOneCard.lastchoices.clear();
            addToBot(new ChooseOneAction(NextChoices));
            this.isDone = true;
        }
        this.isDone = true;
    }
}

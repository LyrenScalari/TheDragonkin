package theDragonkin.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import theDragonkin.cards.GroveKeeper.AbstractChooseOneCard;
import theDragonkin.cards.GroveKeeper.AbstractGroveKeeperCard;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Consumer;

public class CustomChooseOne  extends AbstractGameAction
{
    private CardGroup group;
    private int numberOfCards;
    private AbstractMonster target;
    private boolean allowSkip;
    private boolean retrieveCard;
    private Consumer<AbstractCard> callback;
    public CustomChooseOne(CardGroup group, int number, boolean allowSkip, AbstractMonster Target, Consumer<AbstractCard> callback)
    {
        AbstractChooseOneCard.lastchoice.clear();
        AbstractChooseOneCard.lastchoices.clear();
        AbstractChooseOneCard.Roadsuntraveled.clear();
        this.group = group;
        this.target = Target;
        this.numberOfCards = number;
        this.allowSkip = allowSkip;
        this.callback = callback;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public CustomChooseOne(CardGroup group, AbstractMonster Target)
    {
        this(group, 2, false, Target, null);
    }

    public CustomChooseOne(CardGroup group, int number)
    {
        this(group, number, false,null, null);
    }

    @Override
    public void update()
    {
        if (numberOfCards < 1 || group.size() == 0)
        {
            this.isDone = true;
            return;
        }
        if (this.duration == Settings.ACTION_DUR_FAST)
        {
            try {
                CardGroup groupToShow = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                if (group.size() > numberOfCards)
                {
                    while (groupToShow.size() < numberOfCards)
                    {
                        boolean dupe = false;
                        AbstractCard tmp = group.getRandomCard(true);
                        for (AbstractCard c : groupToShow.group)
                        {
                            if (c.cardID.equals(tmp.cardID)) {
                                dupe = true;
                                break;
                            }
                        }

                        if (!dupe)
                        {
                            UnlockTracker.markCardAsSeen(tmp.cardID);
                            groupToShow.addToBottom(tmp.makeStatEquivalentCopy());
                        }
                    }
                }
                else
                {
                    for (AbstractCard c : group.group)
                    {
                        groupToShow.addToBottom(c.makeStatEquivalentCopy());
                    }
                }
                Method discovery = CardRewardScreen.class.getDeclaredMethod("customRangerDiscovery", CardGroup.class, boolean.class);
                discovery.setAccessible(true);
                discovery.invoke(AbstractDungeon.cardRewardScreen, groupToShow, allowSkip);
                this.tickDuration();
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                this.isDone = true;
                e.printStackTrace();
            }
        } else {
            if (!this.retrieveCard) {
                if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                    AbstractGroveKeeperCard disCard = (AbstractGroveKeeperCard) AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                    AbstractChooseOneCard.lastchoices = group;
                    AbstractChooseOneCard.lastchoice.addToBottom(disCard);
                    group.removeCard(disCard);
                    AbstractChooseOneCard.Roadsuntraveled = group;
                    if (disCard.target == AbstractCard.CardTarget.ENEMY){
                        disCard.calculateCardDamage(this.target);
                        disCard.applyPowers();
                        disCard.onChoseThisOption(this.target);
                    } else {
                        disCard.calculateCardDamage(null);

                        disCard.applyPowers();
                        disCard.onChoseThisOption(null);
                    }

                    disCard.current_x = -1000.0F * Settings.scale;

                    if (callback != null) callback.accept(disCard);
                    if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE) {
                    } else {
                    }
                    AbstractDungeon.cardRewardScreen.discoveryCard = null;
                }
                this.retrieveCard = true;
            }
            this.tickDuration();
        }
    }
}

package theDragonkin.util;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;

public class RareCardsReward extends RewardItem {
    public RareCardsReward() {
        type = RewardType.CARD;
        AbstractCard card1 = AbstractDungeon.getCard(AbstractCard.CardRarity.RARE);
        AbstractCard card2 = AbstractDungeon.getCard(AbstractCard.CardRarity.RARE);
        AbstractCard card3 = AbstractDungeon.getCard(AbstractCard.CardRarity.RARE);
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
    }
}

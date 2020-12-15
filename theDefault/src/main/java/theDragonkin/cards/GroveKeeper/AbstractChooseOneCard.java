package theDragonkin.cards.GroveKeeper;

import com.megacrit.cardcrawl.cards.CardGroup;

public abstract class AbstractChooseOneCard extends AbstractGroveKeeperCard {

    public static CardGroup lastchoices = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    public static CardGroup lastchoice = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    public static CardGroup Roadsuntraveled = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

    public AbstractChooseOneCard(String id, String img, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, img, cost, type, color, rarity, target);
    }
}

package theDragonkin.cards.WindWalker;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import theDragonkin.cards.AbstractDefaultCard;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public abstract class AbstractWindWalkerCard extends AbstractDefaultCard {
    public AbstractWindWalkerCard(final String id,
                                  final String img,
                                  final int cost,
                                  final CardType type,
                                  final CardColor color,
                                  final CardRarity rarity,
                                  final CardTarget target) {

        super(id, languagePack.getCardStrings(id).NAME, img, cost, languagePack.getCardStrings(id).DESCRIPTION, type, color, rarity, target);
    }
}

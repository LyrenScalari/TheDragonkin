package theDragonkin.cards.Dragonkin;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.DragonkinMod;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static theDragonkin.DragonkinMod.makeCardPath;
import static theDragonkin.characters.TheDefault.Enums.Justicar_Red_COLOR;

public class AncestralSeance extends AbstractPrimalCard {

    public static final String ID = DragonkinMod.makeID(AncestralSeance.class.getSimpleName());
    public static final String IMG = makeCardPath("AncestralSeance.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Justicar_Red_COLOR;
    public CardGroup NonHoly = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    public CardGroup FilteredGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;

    private static final int POTENCY = 0;
    private static final int UPGRADE_PLUS_POTENCY = 0;
    private static final int MAGIC = 3;
    private static final int UPGRADE_MAGIC = 0;

    public AncestralSeance() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = POTENCY;
        block = baseBlock = POTENCY;
        heal = baseHeal = POTENCY;
        baseMagicNumber = magicNumber = MAGIC;
        this.exhaust = true;
        tags.add(CardTags.HEALING);
        NonHoly.group = (ArrayList<AbstractCard>) CardLibrary.getAllCards()
                .stream()
                .filter(c -> c.color == Justicar_Red_COLOR)
                .filter(c -> c instanceof AbstractPrimalCard)
                .filter(c -> !c.hasTag(CardTags.HEALING))
                .filter(c -> !c.rarity.equals(CardRarity.BASIC))
                .collect(Collectors.toList());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (NonHoly.size() >= 1) {
            ArrayList<AbstractCard> cards = new ArrayList<>(NonHoly.group);
            int amt = Math.min(cards.size(), 3);
            while (FilteredGroup.size() < amt) {
                FilteredGroup.addToTop(cards.remove(AbstractDungeon.miscRng.random(0, cards.size() - 1)));
            }
            addToBot(new SelectCardsCenteredAction(FilteredGroup.group, 1, cardStrings.EXTENDED_DESCRIPTION[0], List -> {
                AbstractCard cardtoget = List.get(0);
                AbstractDungeon.player.hand.addToHand(cardtoget);
                cardtoget.freeToPlayOnce = true;
                FilteredGroup.clear();
            }));
        }
        super.use(p,m);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}
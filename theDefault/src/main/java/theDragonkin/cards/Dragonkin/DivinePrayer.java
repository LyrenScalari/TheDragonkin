package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.util.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.actions.CustomDiscoveryAction;
import theDragonkin.characters.TheDefault;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static theDragonkin.DefaultMod.makeCardPath;
import static theDragonkin.characters.TheDefault.Enums.Dragonkin_Red_COLOR;

public class DivinePrayer extends AbstractHolyBonusCard {

    public static final String ID = DefaultMod.makeID(DivinePrayer.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;
    public CardGroup Holy = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;

    private static final int POTENCY = 0;
    private static final int UPGRADE_PLUS_DMG = 0;
    private static final int MAGIC = 0;
    private static final int UPGRADE_MAGIC = 0;

    public DivinePrayer() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = POTENCY;
        block = baseBlock = POTENCY;
        heal = baseHeal = POTENCY;
        tags.add(CustomTags.HOLY_CARD);
        baseMagicNumber = magicNumber = MAGIC;
        Holy.group = (ArrayList<AbstractCard>) CardLibrary.getAllCards()
                .stream()
                .filter(c -> c.color == Dragonkin_Red_COLOR)
                .filter(c -> c.hasTag(CustomTags.HOLY_CARD))
                .filter(c -> !c.hasTag(CardTags.HEALING))
                .filter(c -> !c.rarity.equals(CardRarity.BASIC))
                .collect(Collectors.toList());

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new CustomDiscoveryAction(Holy,3,false, card -> card.freeToPlayOnce = true));
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
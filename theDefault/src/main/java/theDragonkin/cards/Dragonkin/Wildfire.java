package theDragonkin.cards.Dragonkin;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.NextTurnFetchpower;
import theDragonkin.powers.Scorchpower;

import static theDragonkin.DefaultMod.makeCardPath;

public class Wildfire extends AbstractDragonkinCard {

    public static final String ID = DefaultMod.makeID(Wildfire.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;

    private static final int POTENCY = 10;
    private static final int UPGRADE_PLUS_POTENCY = 0;
    private static final int MAGIC = 0;
    private static final int UPGRADE_MAGIC = 0;

    public Wildfire() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = POTENCY;
        block = baseBlock = POTENCY;
        heal = baseHeal = POTENCY;
        baseMagicNumber = magicNumber = MAGIC;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsInHandAction("Choose a card to Exhaust.", list -> list.forEach(c ->
        {
            addToTop(new ExhaustSpecificCardAction(c, p.hand));
            if (c.type != CardType.STATUS && c.type != CardType.CURSE) {
                addToBot(new ApplyPowerAction(p, p, new NextTurnFetchpower(p, p, c.cost, c.type, false, true)));
                for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    addToBot(new ApplyPowerAction(mo, p, new Scorchpower(mo, p, c.cost * 3)));
                }
            }
        })));
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
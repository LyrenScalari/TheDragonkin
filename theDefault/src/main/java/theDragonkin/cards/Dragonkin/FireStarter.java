package theDragonkin.cards.Dragonkin;

import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheDefault;

import static theDragonkin.DefaultMod.makeCardPath;

public class FireStarter extends AbstractDragonkinCard {

    public static final String ID = DefaultMod.makeID(FireStarter.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;

    private static final int DAMAGE = 9;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final int MAGIC = 2;
    private static final int UPGRADE_MAGIC = 2;
    private int burncount = 0;

    public FireStarter() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c.cardID.equals(Burn.ID)) {
                addToBot(new MoveCardsAction(AbstractDungeon.player.hand, AbstractDungeon.player.drawPile, b -> {
                    return b.cardID.equals(Burn.ID);
                }, 1));
                AbstractDungeon.actionManager.addToBottom((new GainEnergyAction(1)));
            }

        }
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


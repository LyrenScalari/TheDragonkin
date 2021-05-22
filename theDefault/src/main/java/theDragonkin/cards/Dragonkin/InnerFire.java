package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.DragonkinMod;
import theDragonkin.actions.CycleAction;
import theDragonkin.actions.GainDivineArmorAction;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.Dragonkin.DivineConvictionpower;

import static theDragonkin.DragonkinMod.makeCardPath;

public class InnerFire extends AbstractHolyCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DragonkinMod.makeID(InnerFire.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;

    private static final int COST = 1;
    private static final int BLOCK = 10;
    private static final int UPGRADE_PLUS_BLOCK = 4;
    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC = 1;


    // /STAT DECLARATION/


    public InnerFire() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = BLOCK;
        magicNumber = baseMagicNumber = MAGIC;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 8;
    }

    // Actions the card should do.

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainDivineArmorAction(p,p,defaultSecondMagicNumber));
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.cardID == Burn.ID) {
                addToBot(new CycleAction(c,1));
                addToBot(new ApplyPowerAction(p,p,new DivineConvictionpower(p,p,magicNumber),1));
                break;
            }
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(2);
            upgradeDefaultSecondMagicNumber(2);
            initializeDescription();
        }
    }
}
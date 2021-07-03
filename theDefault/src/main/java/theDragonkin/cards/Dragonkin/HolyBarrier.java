package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theDragonkin.DragonkinMod;
import theDragonkin.actions.GainDivineArmorAction;
import theDragonkin.characters.TheDefault;

import static theDragonkin.DragonkinMod.makeCardPath;

public class HolyBarrier extends AbstractHolyCard {

    public static final String ID = DragonkinMod.makeID(HolyBarrier.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;

    private static final int COST = -1;
    private static final int UPGRADED_COST = 1;

    private static final int POTENCY = 5;
    private static final int UPGRADE_PLUS_POTENCY= 2;
    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC = 1;
    public static int repeats = 0;
    public HolyBarrier() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = POTENCY;
        block = baseBlock = POTENCY;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = POTENCY;
        exhaust = true;
        baseMagicNumber = magicNumber = MAGIC;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.player.hasRelic("Chemical X")) {
            repeats += 2;
            AbstractDungeon.player.getRelic("Chemical X").flash();
        }
        repeats += EnergyPanel.totalCount;
        for (int i = 0; i < this.magicNumber + repeats; ++i) {
            addToBot(new GainDivineArmorAction(p,p,defaultSecondMagicNumber));
        }
        EnergyPanel.useEnergy(EnergyPanel.totalCount);
        repeats = 0;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDefaultSecondMagicNumber(UPGRADE_PLUS_POTENCY);
            initializeDescription();
        }
    }
}
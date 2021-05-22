package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theDragonkin.CustomTags;
import theDragonkin.DragonkinMod;
import theDragonkin.actions.GainDivineArmorAction;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.Dragonkin.HolyBarrierpower;

import static theDragonkin.DragonkinMod.makeCardPath;

public class DivineRadiance extends AbstractHolyCard {

    public static final String ID = DragonkinMod.makeID(DivineRadiance.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int POTENCY = 4;
    private static final int UPGRADE_PLUS_POTENCY= 3;
    private static final int MAGIC = 3;
    private static final int UPGRADE_MAGIC = 1;
    public static int repeats = 0;
    public DivineRadiance() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = POTENCY;
        block = baseBlock = POTENCY;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = POTENCY;
        exhaust = true;
        tags.add(CustomTags.Radiant);
        RadiantExchange = 2;
        baseMagicNumber = magicNumber = MAGIC;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new HolyBarrierpower(p,p,magicNumber,defaultSecondMagicNumber)));
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
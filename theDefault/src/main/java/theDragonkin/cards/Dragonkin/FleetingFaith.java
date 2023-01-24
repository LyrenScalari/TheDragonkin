package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.DragonkinMod;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.Dragonkin.DivineConvictionpower;
import theDragonkin.powers.Dragonkin.PenancePower;
import theDragonkin.powers.Dragonkin.ReflectiveScales;
import theDragonkin.util.TypeEnergyHelper;

import static theDragonkin.DragonkinMod.makeCardPath;

public class FleetingFaith extends AbstractHolyCard {

    public static final String ID = DragonkinMod.makeID(FleetingFaith.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");


    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF_AND_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int POTENCY= 10;
    private static final int UPGRADE_PLUS_DMG = 4;
    private static final int MAGIC = 2;
    private static final int UPGRADE_MAGIC = 1;

    public FleetingFaith() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        block = baseBlock = POTENCY;
        magicNumber = baseMagicNumber = 3;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = 1;
        energyCosts.put(TypeEnergyHelper.Mana.Exalt,defaultSecondMagicNumber);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p,block));
        if (p.hasPower(DivineConvictionpower.POWER_ID)) {
            if (AbstractDungeon.player.getPower(DivineConvictionpower.POWER_ID).amount >= defaultSecondMagicNumber) {
                addToBot(new ReducePowerAction(p,p,p.getPower(DivineConvictionpower.POWER_ID),defaultSecondMagicNumber));
                addToBot(new ApplyPowerAction(p,p,new ReflectiveScales(p,p,magicNumber)));
            }
        }
        super.use(p,m);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}
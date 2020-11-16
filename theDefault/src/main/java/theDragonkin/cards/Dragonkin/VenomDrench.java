package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheDefault;

import static theDragonkin.DefaultMod.makeCardPath;

public class VenomDrench extends AbstractDragonkinCard {

    public static final String ID = DefaultMod.makeID(VenomDrench.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 2;

    private static final int POTENCY = 6;
    private static final int UPGRADE_PLUS_POTENCY = 2;
    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC = 1;
    private int Statuscount = 0;

    public VenomDrench() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = POTENCY;
        block = baseBlock = POTENCY;
        heal = baseHeal = POTENCY;
        baseMagicNumber = magicNumber = MAGIC;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
            for (AbstractPower power : m.powers) {
                if (power.type == AbstractPower.PowerType.DEBUFF) {
                    Statuscount += 1;
                }
            }
            addToBot(new GainBlockAction(p, block +(magicNumber * Statuscount) ));
        }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_POTENCY);
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }
}
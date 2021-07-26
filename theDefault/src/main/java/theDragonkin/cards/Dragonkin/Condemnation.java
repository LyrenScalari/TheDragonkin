package theDragonkin.cards.Dragonkin;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.CardMods.StormEffect;
import theDragonkin.DragonkinMod;
import theDragonkin.cards.Dragonkin.interfaces.StormCard;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.Dragonkin.HolyBombPower;

import static theDragonkin.DragonkinMod.makeCardPath;

public class Condemnation extends AbstractHolyCard implements StormCard {

    public static final String ID = DragonkinMod.makeID(Condemnation.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 2;

    private static final int POTENCY = 40;
    private static final int UPGRADE_PLUS_POTENCY = 10;
    private static final int MAGIC = 0;
    private static final int UPGRADE_MAGIC = 0;

    public Condemnation() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = POTENCY;
        block = baseBlock = POTENCY;
        heal = baseHeal = POTENCY;
        baseMagicNumber = magicNumber = 40;
        StormRate = 5;
        CardModifierManager.addModifier(this, new StormEffect(StormRate));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!Storm) {
            addToBot(new ApplyPowerAction(p, p, new HolyBombPower(p, p, 3, magicNumber)));
        } else {
            addToBot(new ApplyPowerAction(p, p, new HolyBombPower(p, p, 1, magicNumber)));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_POTENCY);
            initializeDescription();
        }
    }

    @Override
    public void onStorm() {

    }
}
package theDragonkin.cards.Dragonkin;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theDragonkin.CardMods.StormEffect;
import theDragonkin.DragonkinMod;
import theDragonkin.actions.CycleAction;
import theDragonkin.actions.FluxAction;
import theDragonkin.cards.Dragonkin.interfaces.StormCard;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.Dragonkin.DivineConvictionpower;
import theDragonkin.powers.Dragonkin.IncendiaryFlowPower;

import static theDragonkin.DragonkinMod.makeCardPath;

public class IncendiaryFlow extends AbstractPrimalCard implements StormCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DragonkinMod.makeID(IncendiaryFlow.class.getSimpleName());
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
    private static final int MAGIC = 4;
    private static final int UPGRADE_MAGIC = 1;

    // /STAT DECLARATION/


    public IncendiaryFlow() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = BLOCK;
        magicNumber = baseMagicNumber = MAGIC;
        StormRate = 3;
        CardModifierManager.addModifier(this, new StormEffect(StormRate));
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 3;
    }

    // Actions the card should do.

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int CycleCount = 0;
        addToBot(new FluxAction(defaultSecondMagicNumber,()->new AbstractGameAction() {
            @Override
            public void update() { addToBot(new ApplyPowerAction(p,p,new StrengthPower(p,defaultSecondMagicNumber)));
            addToBot(new ApplyPowerAction(p,p,new LoseStrengthPower(p,defaultSecondMagicNumber)));
            isDone = true; }
        }));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDefaultSecondMagicNumber(2);
            initializeDescription();
        }
    }

    @Override
    public void onStorm() {
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,
                new IncendiaryFlowPower(AbstractDungeon.player,AbstractDungeon.player,magicNumber),magicNumber));
    }
}
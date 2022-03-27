package theDragonknight.cards.Dragonkin;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonknight.CardMods.StormEffect;
import theDragonknight.DragonkinMod;
import theDragonknight.actions.CycleAction;
import theDragonknight.cards.Dragonkin.interfaces.StormCard;
import theDragonknight.characters.TheDefault;
import theDragonknight.orbs.FlameGlyph;
import theDragonknight.powers.Dragonkin.IncendiaryFlowPower;

import static theDragonknight.DragonkinMod.makeCardPath;

public class IncendiaryFlow extends AbstractPrimalCard implements StormCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * WindWalkerDefend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DragonkinMod.makeID(IncendiaryFlow.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;

    private static final int COST = 1;
    private static final int BLOCK = 10;
    private static final int UPGRADE_PLUS_BLOCK = 4;
    private static final int MAGIC = 3;
    private static final int UPGRADE_MAGIC = 1;

    // /STAT DECLARATION/


    public IncendiaryFlow() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = BLOCK;
        magicNumber = baseMagicNumber = MAGIC;
        StormRate = 3;
        CardModifierManager.addModifier(this, new StormEffect(StormRate));
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 2;
    }

    // Actions the card should do.

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,
                new IncendiaryFlowPower(AbstractDungeon.player,AbstractDungeon.player,magicNumber),magicNumber));
        addToBot(new SelectCardsInHandAction(defaultBaseSecondMagicNumber," Cycle",false,false,(card)->true,(List)-> {
            for (AbstractCard c : List) {
                addToBot(new CycleAction(c,1));
            }
        }));
        super.use(p,m);
    }
    public void triggerOnManualDiscard() {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                DragonkinMod.Seals.add(new FlameGlyph(magicNumber, defaultBaseSecondMagicNumber));
                isDone = true;
            }
        });
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
        triggerOnManualDiscard();
    }
}
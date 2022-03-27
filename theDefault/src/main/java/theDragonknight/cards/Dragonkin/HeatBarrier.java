package theDragonknight.cards.Dragonkin;


import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.blockmods.BlockModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonknight.CardMods.AddIconToDescriptionMod;
import theDragonknight.DamageModifiers.BlockModifiers.FireBlock;
import theDragonknight.DamageModifiers.Icons.FireIcon;
import theDragonknight.DragonkinMod;
import theDragonknight.characters.TheDefault;
import theDragonknight.util.TriggerOnCycleEffect;

import static theDragonknight.DragonkinMod.makeCardPath;

public class HeatBarrier extends AbstractPrimalCard implements TriggerOnCycleEffect {

    public static final String ID = DragonkinMod.makeID(HeatBarrier.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;
    private static int realBlock = 8;
    private static final int POTENCY = 8;
    private static final int UPGRADE_PLUS_DMG = 2;
    private static final int MAGIC = 4;
    private static final int UPGRADE_MAGIC = 2;
    public HeatBarrier() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = POTENCY;
        block = baseBlock = POTENCY;
        heal = baseHeal = POTENCY;
        baseMagicNumber = magicNumber = MAGIC;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 2;
        BlockModifierManager.addModifier(this,new FireBlock(true));
        CardModifierManager.addModifier(this,new AddIconToDescriptionMod(AddIconToDescriptionMod.BLOCK, FireIcon.get()));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new GainBlockAction(p,block));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                baseBlock = realBlock;
                isDone = true;
            }
        });
        super.use(p,m);
    }
    @Override
    public void triggerWhenDrawn(){
        if (DragonkinMod.StatusesCycledThisTurn < 1){
            baseBlock = realBlock;
        }

    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(2);
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }

    @Override
    public void TriggerOnCycle(AbstractCard ca) {
        baseBlock += magicNumber;
    }
}
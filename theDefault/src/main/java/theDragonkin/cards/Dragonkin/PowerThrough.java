package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.DragonkinMod;
import theDragonkin.characters.TheDefault;

import java.util.Iterator;

import static theDragonkin.DragonkinMod.makeCardPath;

public class PowerThrough extends AbstractDragonkinCard {

    public static final String ID = DragonkinMod.makeID(PowerThrough.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;

    private static final int POTENCY= 10;
    private static final int UPGRADE_PLUS_DMG = 4;
    private static final int MAGIC = 6;
    private static final int UPGRADE_MAGIC = 2;
    public static int reductionval = 0;
    public static int baseCost = COST;

    public PowerThrough() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        block = baseBlock = POTENCY;
        magicNumber = baseMagicNumber = 1;
        if (CardCrawlGame.dungeon != null && AbstractDungeon.currMapNode != null && !AbstractDungeon.getCurrRoom().isBattleOver) {
            this.configureCostsOnNewCard();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p,block));
        super.use(p,m);
    }
    @Override
    public void applyPowers(){
        reductionval = 0;
        for (AbstractPower p : AbstractDungeon.player.powers){
            if (p.type == AbstractPower.PowerType.DEBUFF){
                reductionval += 1;
            }
        }
            if ((baseCost-reductionval) != costForTurn){
                cost = baseCost;
                this.updateCost(-reductionval);
            }
            super.applyPowers();
        }
    @Override
    public void triggerWhenDrawn() {
        baseCost = costForTurn;
        for (AbstractPower p : AbstractDungeon.player.powers){
            if (p.type == AbstractPower.PowerType.DEBUFF){
                reductionval += 1;
            }
        }
        if ((baseCost-reductionval) != costForTurn){
            cost = baseCost;
            this.updateCost(-reductionval);
        }
    }
    public void configureCostsOnNewCard() {
        reductionval = 0;
        for (AbstractPower p : AbstractDungeon.player.powers){
            if (p.type == AbstractPower.PowerType.DEBUFF){
                reductionval += 1;
            }
        }
        if ((baseCost-reductionval) != costForTurn){
            cost = baseCost;
            this.updateCost(-reductionval);
        }
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
            initializeDescription();
        }
    }
}
package theDragonkin.cards.WindWalker;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.orbs.Lightning;
import theDragonkin.DragonkinMod;
import theDragonkin.characters.TheWindWalker;

import static theDragonkin.DragonkinMod.makeCardPath;

public class Northwind extends AbstractWindWalkerCard {

/*
 * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
 *
 * WindWalkerDefend Gain 5 (8) block.
 */


// TEXT DECLARATION

public static final String ID = DragonkinMod.makeID(Northwind.class.getSimpleName());
public static final String IMG = makeCardPath("WindWalkerDefend.png");

// /TEXT DECLARATION/


// STAT DECLARATION

private static final CardRarity RARITY = CardRarity.COMMON;
private static final CardTarget TARGET = CardTarget.SELF;
private static final CardType TYPE = CardType.SKILL;
public static final CardColor COLOR = TheWindWalker.Enums.WindWalker_Jade_COLOR;

private static final int COST = 1;
private static final int BLOCK = 8;
private static final int UPGRADE_PLUS_BLOCK = 3;


// /STAT DECLARATION/


public Northwind() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        magicNumber = baseMagicNumber = 1;
}

// Actions the card should do.
@Override
public void use(AbstractPlayer p, AbstractMonster m) {
    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
    for (int i = 0; i < magicNumber; i++) {
        addToBot(new ChannelAction(new Frost()));
    }
}

//Upgraded stats.
@Override
public void upgrade() {
        if (!upgraded) {
        upgradeName();
        upgradeBlock(UPGRADE_PLUS_BLOCK);
        initializeDescription();
        }
    }
}
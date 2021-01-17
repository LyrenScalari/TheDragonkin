package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.Dragonkin.DragonBreaths.SmaugsSmogEffect;

import static theDragonkin.DefaultMod.makeCardPath;

public class Smaugs_Smog extends AbstractPrimalCard {

public static final String ID=DefaultMod.makeID(Smaugs_Smog.class.getSimpleName());
public static final String IMG=makeCardPath("Attack.png");


private static final CardRarity RARITY=CardRarity.RARE;
private static final CardTarget TARGET=CardTarget.SELF;
private static final CardType TYPE=CardType.ATTACK;
public static final CardColor COLOR=TheDefault.Enums.Dragonkin_Red_COLOR;

private static final int COST=2;
private static final int UPGRADED_COST=2;


public Smaugs_Smog() {
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
        damage=baseDamage=10;
        block=baseBlock=10;
        baseMagicNumber=magicNumber=4;
}

@Override
public void use(AbstractPlayer p,AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new SmaugsSmogEffect(damage,block,magicNumber)));
}

@Override
public void upgrade(){
        if(!upgraded){
        upgradeName();
        upgradeDamage(2);
        upgradeBlock(2);
        upgradeMagicNumber(2);
        initializeDescription();
        }
        }
}
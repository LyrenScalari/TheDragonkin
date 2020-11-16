package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheDefault;

import static theDragonkin.DefaultMod.makeCardPath;

public class Smaugs_Smog extends AbstractDragonkinCard {

public static final String ID=DefaultMod.makeID(Smaugs_Smog.class.getSimpleName());
public static final String IMG=makeCardPath("Attack.png");


private static final CardRarity RARITY=CardRarity.RARE;
private static final CardTarget TARGET=CardTarget.SELF;
private static final CardType TYPE=CardType.ATTACK;
public static final CardColor COLOR=TheDefault.Enums.Dragonkin_Red_COLOR;

private static final int COST=2;
private static final int UPGRADED_COST=2;

private static final int POTENCY=3;
private static final int UPGRADE_PLUS_POTENCY=2;
private static final int MAGIC=1;
private static final int UPGRADE_MAGIC=0;

public Smaugs_Smog() {
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
        damage=baseDamage=POTENCY;
        block=baseBlock=POTENCY;
        heal=baseHeal=POTENCY;
        baseMagicNumber=magicNumber=MAGIC;
        this.isMultiDamage = true;

        }

@Override
public void use(AbstractPlayer p,AbstractMonster m) {
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                for (AbstractPower power : mo.powers){
                        if (power.type == AbstractPower.PowerType.DEBUFF) {
                                int max = Math.max(1, power.amount);
                                for (int i = 0; i < max; ++i) {
                                        AbstractDungeon.actionManager.addToBottom(
                                                new DamageAction(mo, new DamageInfo(p, baseDamage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));

                                }
                        }
                }
        }
}

@Override
public void upgrade(){
        if(!upgraded){
        upgradeName();
        upgradeDamage(UPGRADE_PLUS_POTENCY);
        upgradeMagicNumber(UPGRADE_MAGIC);
        initializeDescription();
        }
        }
}
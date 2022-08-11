package theDragonkin.cards.Dragonkin;

import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.mod.stslib.actions.common.DamageCallbackAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.DragonkinMod;
import theDragonkin.actions.SmiteAction;
import theDragonkin.characters.TheDefault;

import static theDragonkin.DragonkinMod.makeCardPath;

public class ScalesofJustice extends AbstractHolyCard {

    public static final String ID = DragonkinMod.makeID(ScalesofJustice.class.getSimpleName());
    public static final String IMG = makeCardPath("Pandemic.png");


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;

    private static final int POTENCY = 6;
    private static final int UPGRADE_PLUS_POTENCY = 0;
    private static final int UPGRADE_MAGIC = 0;

    public ScalesofJustice() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = POTENCY;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractPower power : p.powers){
            if (power.type == AbstractPower.PowerType.DEBUFF){
                for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters){
                    AbstractDungeon.actionManager.addToBottom(new SFXAction("ORB_LIGHTNING_EVOKE"));
                    addToBot(new SmiteAction(mo, new DamageInfo(p, damage, damageTypeForTurn)));
                }
            }
        }
        super.use(p,m);
        for (AbstractPower po : p.powers){
            if (po.type == AbstractPower.PowerType.DEBUFF) {
                if (po instanceof CloneablePowerInterface) {
                    for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                        AbstractPower temp = ((CloneablePowerInterface)po).makeCopy();
                        temp.owner = mo;
                        addToBot(new ApplyPowerAction(mo, p, temp));
                    }
                }
            }
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3 );
            initializeDescription();
        }
    }
}


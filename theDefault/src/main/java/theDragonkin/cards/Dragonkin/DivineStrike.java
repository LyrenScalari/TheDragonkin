package theDragonkin.cards.Dragonkin;

import com.evacipated.cardcrawl.mod.stslib.actions.common.DamageCallbackAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import theDragonkin.CustomTags;
import theDragonkin.DragonkinMod;
import theDragonkin.characters.TheDefault;

import static theDragonkin.DragonkinMod.makeCardPath;

public class DivineStrike extends AbstractHolyCard {


    // TEXT DECLARATION

    public static final String ID = DragonkinMod.makeID(DivineStrike.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("WindwalkerStrike.png");// "public static final String IMG = makeCardPath("FlameweaverStrike.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;

    private static final int COST = 1;  // COST = 1
    private static final int UPGRADED_COST = 1; // UPGRADED_COST = 1

    private static final int DAMAGE = 8;    // DAMAGE = 6
    private static final int UPGRADE_PLUS_DMG = 2;  // UPGRADE_PLUS_DMG = 4

    // /STAT DECLARATION/


    public DivineStrike(){
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
        baseDamage =DAMAGE;
        baseMagicNumber = magicNumber = 2;
        tags.add(CustomTags.Smite);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m,new DamageInfo(p,damage,damageTypeForTurn)));
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters){
            if (mo.hasPower(WeakPower.POWER_ID)){
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m,new DamageInfo(p,damage,damageTypeForTurn)));
            }
        }
        addToBot(new ApplyPowerAction(m,p,new WeakPower(m,magicNumber,false)));
        super.use(p,m);
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}
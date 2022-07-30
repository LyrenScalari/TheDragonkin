package theDragonkin.cards.Dragonkin;

import basemod.AutoAdd;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.DragonkinMod;
import theDragonkin.actions.CycleAction;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.Dragonkin.SacrificePower;

import static theDragonkin.DragonkinMod.makeCardPath;

public class Sunburst extends AbstractHolyCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * WindWalkerDefend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DragonkinMod.makeID(Sunburst.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;

    private static final int COST = 1;
    private static final int BLOCK = 4;
    private static final int UPGRADE_PLUS_BLOCK = 4;
    private static final int MAGIC = 3;
    private static final int UPGRADE_MAGIC = 1;


    // /STAT DECLARATION/


    public Sunburst() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = 6;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 2;
        block = baseBlock = BLOCK;
    }

    // Actions the card should do.

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m,new DamageInfo(m,damage, DamageInfo.DamageType.NORMAL)));
        addToBot(new SelectCardsInHandAction(defaultSecondMagicNumber," Cycle",false,false,(card)->true,(List)-> {
            boolean blessed = false;
            for (AbstractCard c : List){
                addToBot(new CycleAction(c,1));
                if (c instanceof AbstractHolyCard){
                    if (!blessed) {
                        addToBot(new GainBlockAction(p, block));
                        blessed = true;
                    }
                }
            }
        }));
        super.use(p,m);
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDefaultSecondMagicNumber(1);
            initializeDescription();
        }
    }
}

package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.CustomTags;
import theDragonkin.DragonkinMod;
import theDragonkin.actions.CycleAction;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.Dragonkin.AcidMarkPower;
import theDragonkin.powers.Dragonkin.Scorchpower;

import static theDragonkin.DragonkinMod.makeCardPath;

public class AlchemicalFlame extends AbstractPrimalCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DragonkinMod.makeID(AlchemicalFlame.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;

    private static final int COST = 1;
    private static final int BLOCK = 8;
    private static final int UPGRADE_PLUS_BLOCK = 3;


    // /STAT DECLARATION/


    public AlchemicalFlame() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        baseDamage = baseBlock;
        baseMagicNumber = magicNumber = 6;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 2;
        tags.add(CustomTags.Acid_Applicator);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m.hasPower(Scorchpower.POWER_ID)){
            addToBot(new ApplyPowerAction(m,p,new AcidMarkPower(m,p,m.getPower(Scorchpower.POWER_ID).amount)));
        }
        int CycleCount = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (CycleCount >= defaultSecondMagicNumber){
                break;
            }
            if (c.cardID == Burn.ID) {
                addToBot(new CycleAction(c,1));
                addToBot(new ApplyPowerAction(m,p,new Scorchpower(m,p,magicNumber)));
                CycleCount++;
            }
        }
        super.use(p,m);
    }


    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            upgradeDefaultSecondMagicNumber(1);
            initializeDescription();
        }
    }
}
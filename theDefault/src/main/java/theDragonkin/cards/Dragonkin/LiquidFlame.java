package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.CustomTags;
import theDragonkin.DragonkinMod;
import theDragonkin.actions.CycleAction;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.Dragonkin.AcidMarkPower;
import theDragonkin.powers.Dragonkin.Scorchpower;

import static theDragonkin.DragonkinMod.makeCardPath;

public class LiquidFlame extends AbstractPrimalCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DragonkinMod.makeID(LiquidFlame.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final int COST = 1;
    private static final int BLOCK = 8;
    private static final int UPGRADE_PLUS_BLOCK = 3;


    // /STAT DECLARATION/


    public LiquidFlame() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        baseDamage = baseBlock;
        baseMagicNumber = magicNumber = 8;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 1;
        tags.add(CustomTags.Acid_Applicator);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m,p, new AcidMarkPower(m,p,magicNumber)));
        if (upgraded){
            addToBot(new ApplyPowerAction(m,p, new Scorchpower(m,p,magicNumber)));
        }
        addToBot(new MakeTempCardInDrawPileAction(new Burn(),defaultSecondMagicNumber,true,false));
        int CycleCount = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (CycleCount >= 2){
                break;
            }
            if (c.cardID == Burn.ID) {
                addToBot(new CycleAction(c,1));
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        if (m.hasPower(AcidMarkPower.POWER_ID)) {
                            addToBot(new LoseHPAction(m, p, m.getPower(AcidMarkPower.POWER_ID).amount));
                        }
                        isDone = true;
                    }
                });
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
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
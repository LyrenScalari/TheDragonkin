package theDragonknight.cards.Dragonkin;

import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonknight.CardMods.StormEffect;
import theDragonknight.CustomTags;
import theDragonknight.DragonkinMod;
import theDragonknight.cards.Dragonkin.interfaces.StormCard;
import theDragonknight.characters.TheDefault;
import theDragonknight.orbs.VenganceRune;
import theDragonknight.powers.Dragonkin.ReflectiveScales;

import java.util.ArrayList;
import java.util.List;

import static theDragonknight.DragonkinMod.makeCardPath;

public class BlessedWeapon extends AbstractPrimalCard implements StormCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * WindWalkerDefend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DragonkinMod.makeID(BlessedWeapon.class.getSimpleName());
    public static final String IMG = makeCardPath("WindWalkerDefend.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 1;
    private static final int BLOCK = 9;
    private static final int UPGRADE_PLUS_BLOCK = 2;


    // /STAT DECLARATION/
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Primal"),BaseMod.getKeywordDescription("thedragonkin:Primal")));
        retVal.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Rune"),BaseMod.getKeywordDescription("thedragonkin:Rune")));
        return retVal;
    }
    public BlessedWeapon() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = 4;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 3;
        block = baseBlock = 3;
        tags.add(CustomTags.Rune);
        StormRate = magicNumber;
        CardModifierManager.addModifier(this, new StormEffect(StormRate));
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(AbstractDungeon.player,block));
        addToBot(new ApplyPowerAction(p,p,new ReflectiveScales(p,p,defaultSecondMagicNumber)));
        super.use(p,m);
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDefaultSecondMagicNumber(2);
            upgradeBlock(2);
            initializeDescription();
        }
    }

    public void triggerOnManualDiscard() {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                DragonkinMod.Seals.add(new VenganceRune(block, defaultSecondMagicNumber));
                isDone = true;
            }
        });
    }

    @Override
    public void onStorm() {
        triggerOnManualDiscard();
    }
}
package theDragonknight.cards.Dragonkin;

import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theDragonknight.CardMods.StormEffect;
import theDragonknight.CustomTags;
import theDragonknight.DragonkinMod;
import theDragonknight.cards.Dragonkin.interfaces.StormCard;
import theDragonknight.characters.TheDefault;
import theDragonknight.orbs.ShatterGlyph;

import java.util.ArrayList;
import java.util.List;

import static theDragonknight.DragonkinMod.makeCardPath;

public class ShatterRune extends AbstractPrimalCard implements StormCard{

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * WindWalkerDefend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DragonkinMod.makeID(ShatterRune.class.getSimpleName());
    public static final String IMG = makeCardPath("WindWalkerDefend.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 2;
    private static final int BLOCK = 9;
    private static final int UPGRADE_PLUS_BLOCK = 2;


    // /STAT DECLARATION/
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Rune"),BaseMod.getKeywordDescription("thedragonkin:Rune")));
        return retVal;
    }
    @Override
    public List<String> getCardDescriptors() {

        List<String> tags = new ArrayList<>();
        tags.add(BaseMod.getKeywordTitle("thedragonkin:Rune"));
        tags.addAll(super.getCardDescriptors());
        return tags;
    }
    public ShatterRune() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = 1;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 2;
        damage = baseDamage = 18;
        tags.add(CustomTags.Rune);
        StormRate = magicNumber;
        CardModifierManager.addModifier(this, new StormEffect(StormRate));
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m,new DamageInfo(p,damage)));
        addToBot(new ApplyPowerAction(m,p,new VulnerablePower(m,defaultSecondMagicNumber,false)));
        super.use(p,m);
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDefaultSecondMagicNumber(1);
            upgradeDamage(2);
            initializeDescription();
        }
    }

    public void triggerOnManualDiscard() {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                DragonkinMod.Seals.add(new ShatterGlyph(damage, magicNumber));
                isDone = true;
            }
        });
    }

    @Override
    public void onStorm() {
        triggerOnManualDiscard();
    }
}
package theDragonkin.cards.Dragonkin;

import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theDragonkin.CardMods.StormEffect;
import theDragonkin.CustomTags;
import theDragonkin.DragonkinMod;
import theDragonkin.cards.Dragonkin.interfaces.StormCard;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.Dragonkin.AuraFlame;
import theDragonkin.powers.Dragonkin.FuryPower;
import theDragonkin.util.RuneTextEffect;
import theDragonkin.util.TriggerOnCycleEffect;

import java.util.ArrayList;
import java.util.List;

import static theDragonkin.DragonkinMod.makeCardPath;

public class BladeMirrorRune extends AbstractPrimalCard implements StormCard, TriggerOnCycleEffect {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * WindWalkerDefend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DragonkinMod.makeID(BladeMirrorRune.class.getSimpleName());
    public static final String IMG = makeCardPath("WindWalkerDefend.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 0;
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
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        if (AbstractDungeon.player.hasPower(FuryPower.POWER_ID)) {
            if (AbstractDungeon.player.getPower(FuryPower.POWER_ID).amount >= StormRate) {
                return true;
            } else {
                return false;
            }
        } else return false;
    }
    public BladeMirrorRune() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = 9;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 3;
        damage = baseDamage = 8;
        tags.add(CustomTags.Rune);
        StormRate = magicNumber;
        CardModifierManager.addModifier(this, new StormEffect(StormRate));
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new RuneTextEffect(p.drawX,p.drawY,cardStrings.EXTENDED_DESCRIPTION[1])));
        addToBot(new ApplyPowerAction(m,p,new StrengthPower(m,-defaultSecondMagicNumber)));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDefaultSecondMagicNumber(2);
            upgradeDamage(2);
            initializeDescription();
        }
    }

    @Override
    public void onStorm() {

    }
    public void triggerOnManualDiscard() {
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new VFXAction(new RuneTextEffect(p.drawX,p.drawY,cardStrings.EXTENDED_DESCRIPTION[1])));
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
            if (!m.isDeadOrEscaped()){
                addToBot(new ApplyPowerAction(m,p,new WeakPower(m,magicNumber,false)));
            }
        }
    }
    @Override
    public void TriggerOnCycle(AbstractCard ca) {
    }
}
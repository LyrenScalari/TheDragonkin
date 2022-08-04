package theDragonkin.cards.Event;

import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.CardMods.StormEffect;
import theDragonkin.CustomTags;
import theDragonkin.DragonkinMod;
import theDragonkin.cards.AbstractDefaultCard;
import theDragonkin.cards.Dragonkin.interfaces.StormCard;
import theDragonkin.powers.Dragonkin.FuryPower;
import theDragonkin.powers.WindWalker.MasteryPower;
import theDragonkin.util.TriggerOnCycleEffect;

import java.util.ArrayList;
import java.util.List;

import static theDragonkin.DragonkinMod.makeCardPath;

public class WindwalkersGift extends AbstractDefaultCard implements StormCard, TriggerOnCycleEffect {
    public static final String ID = DragonkinMod.makeID(WindwalkersGift.class.getSimpleName());
    public static final String IMG = makeCardPath("WindWalkerDefend.png");
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = CardColor.COLORLESS;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 0;
    private static final int BLOCK = 9;
    private static final int UPGRADE_PLUS_BLOCK = 2;

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Rune"), BaseMod.getKeywordDescription("thedragonkin:Rune")));
        return retVal;
    }

    @Override
    public List<String> getCardDescriptors() {

        List<String> tags = new ArrayList<>();
        tags.add(BaseMod.getKeywordTitle("thedragonkin:Rune"));
        tags.addAll(super.getCardDescriptors());
        return tags;
    }

    public WindwalkersGift() {
        super(ID,cardStrings.NAME, IMG, COST,cardStrings.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        tags.add(CustomTags.Rune);
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 3;
        StormRate = magicNumber;
        magicNumber = baseMagicNumber = 4;
        CardModifierManager.addModifier(this, new StormEffect(6));
    }

    @Override
    public void upgrade() {

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

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(defaultSecondMagicNumber));
    }

    @Override
    public void onStorm() {

    }

    @Override
    public void TriggerOnCycle(AbstractCard ca) {
        AbstractPlayer p = AbstractDungeon.player;
        if (ca == this) {
            addToBot(new ApplyPowerAction(p,p,new MasteryPower(p,p,magicNumber)));
        }
    }
}

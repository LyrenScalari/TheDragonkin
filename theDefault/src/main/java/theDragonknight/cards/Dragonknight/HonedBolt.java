package theDragonknight.cards.Dragonknight;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonknight.CustomTags;
import theDragonknight.DragonknightMod;
import theDragonknight.characters.TheDragonknight;
import theDragonknight.orbs.DragonShouts.FlameMark;
import theDragonknight.orbs.DragonShouts.MagmaMark;
import theDragonknight.orbs.DragonShouts.StormMark;
import theDragonknight.powers.FrostbitePower;
import theDragonknight.util.AbstractNotOrb;
import theDragonknight.util.Wiz;

import java.util.ArrayList;
import java.util.List;

import static theDragonknight.DragonknightMod.makeCardPath;

public class HonedBolt extends AbstractDragonknightCard {

    public static final String ID = DragonknightMod.makeID(HonedBolt.class.getSimpleName());
    public static final String IMG = makeCardPath("WindWalkerDefend.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final CardColor COLOR = TheDragonknight.Enums.Dragonknight_Crimson_Color;

    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonknight:Static"), BaseMod.getKeywordDescription("thedragonknight:Static")));
        retVal.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonknight:Conduit"), BaseMod.getKeywordDescription("thedragonknight:Conduit")));
        return retVal;
    }
    public HonedBolt() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = 1;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = 2;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToEnemy(m,new FrostbitePower(m,p,magicNumber));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractNotOrb mark : DragonknightMod.Seals){
                    if (mark instanceof StormMark){
                        ((StormMark) mark).TriggerPassive();
                        if (upgraded){
                            ((StormMark) mark).TriggerPassive();
                        }
                    }
                }
                isDone = true;
            }
        });
    }
    public void triggerOnExhaust() {
        addToBot(new DrawCardAction(defaultSecondMagicNumber));
    }
    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
            initializeDescription();
        }
    }
}
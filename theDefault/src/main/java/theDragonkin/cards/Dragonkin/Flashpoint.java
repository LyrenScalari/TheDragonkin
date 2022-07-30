package theDragonkin.cards.Dragonkin;

import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NoDrawPower;
import theDragonkin.CardMods.StormEffect;
import theDragonkin.CustomTags;
import theDragonkin.DragonkinMod;
import theDragonkin.actions.InfernoWardAction;
import theDragonkin.cards.Dragonkin.interfaces.StormCard;
import theDragonkin.characters.TheDefault;
import theDragonkin.orbs.BlazeRune;
import theDragonkin.orbs.SparkGlyph;
import theDragonkin.powers.Dragonkin.HeatPower;

import java.util.ArrayList;
import java.util.List;

import static theDragonkin.DragonkinMod.makeCardPath;

public class Flashpoint extends AbstractPrimalCard {

    public static final String ID = DragonkinMod.makeID(Flashpoint.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Rune"),BaseMod.getKeywordDescription("thedragonkin:Rune")));
        return retVal;
    }
    public Flashpoint() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = 2;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 3;
        tags.add(CustomTags.Rune);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                DragonkinMod.Seals.add(new SparkGlyph(magicNumber, defaultSecondMagicNumber));
                isDone = true;
            }
        });
        super.use(p,m);
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}
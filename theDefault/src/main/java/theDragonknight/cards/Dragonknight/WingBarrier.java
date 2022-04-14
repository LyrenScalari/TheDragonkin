package theDragonknight.cards.Dragonknight;

import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonknight.CustomTags;
import theDragonknight.DragonknightMod;
import theDragonknight.actions.TransfigureAction;
import theDragonknight.cards.Dragonknight.TransfiguredCards.TransfiguredFlameBlitz;
import theDragonknight.cards.Dragonknight.TransfiguredCards.TransfiguredWingGaurd;
import theDragonknight.characters.TheDragonknight;
import theDragonknight.util.Wiz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static theDragonknight.DragonknightMod.makeCardPath;

public class WingBarrier extends AbstractDragonknightCard {

    public static final String ID = DragonknightMod.makeID(WingBarrier.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("WindwalkerStrike.png");// "public static final String IMG = makeCardPath("FlameweaverStrike.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheDragonknight.Enums.Dragonknight_Crimson_Color;

    private static final int COST = 1;  // COST = 1
    private static final int UPGRADED_COST = 1; // UPGRADED_COST = 1

    private static final int DAMAGE = 7;    // DAMAGE = 6
    private static final int UPGRADE_PLUS_DMG = 4;  // UPGRADE_PLUS_DMG = 4
    // /STAT DECLARATION/

    @Override
    public List<String> getCardDescriptors() {
        List<String> tags = new ArrayList<>();
        tags.add(BaseMod.getKeywordTitle("thedragonknight:Draconic"));
        tags.addAll(super.getCardDescriptors());
        return tags;
    }
    public WingBarrier(){
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
        baseDamage =DAMAGE;
        block = baseBlock = 7;
        magicNumber = baseMagicNumber = 3;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 2;
        cardsToPreview = new TransfiguredWingGaurd();
        tags.add(CustomTags.Draconic);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (magicNumber > 0){
            HashMap<AbstractCard, AbstractCard> tranfigurecards = new HashMap<>();
            tranfigurecards.put(new WingBarrier(),this);
            addToBot(new TransfigureAction(magicNumber,this,tranfigurecards));
        } else {

        }
    }
    public void applyPowers() {
        if (magicNumber <= 0) {
            rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
            modifyCostForCombat(1);
            block = baseBlock = 13;
            cardsToPreview = null;
            this.initializeDescription();
        }
        super.applyPowers();
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
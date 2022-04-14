package theDragonknight.cards.Dragonknight;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonknight.DragonknightMod;
import theDragonknight.actions.TransfigureAction;
import theDragonknight.cards.Dragonknight.TransfiguredCards.TransfiguredVolcanicFissure;
import theDragonknight.characters.TheDragonknight;
import theDragonknight.orbs.DragonShouts.MagmaMark;
import theDragonknight.util.Wiz;

import java.util.HashMap;

import static theDragonknight.DragonknightMod.makeCardPath;

public class VolcanicFissure extends AbstractDragonknightCard {

    public static final String ID = DragonknightMod.makeID(VolcanicFissure.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("WindwalkerStrike.png");// "public static final String IMG = makeCardPath("FlameweaverStrike.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheDragonknight.Enums.Dragonknight_Crimson_Color;

    private static final int COST = 2;  // COST = 1
    private static final int UPGRADED_COST = 1; // UPGRADED_COST = 1

    private static final int DAMAGE = 12;    // DAMAGE = 6
    private static final int UPGRADE_PLUS_DMG = 4;  // UPGRADE_PLUS_DMG = 4
    // /STAT DECLARATION/

    public VolcanicFissure(){
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
        baseDamage =DAMAGE;
        magicNumber = baseMagicNumber = 3;
        cardsToPreview = new TransfiguredVolcanicFissure();
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (magicNumber > 0){
            addToBot(new DiscardAction(p,p,1,false));
            HashMap<AbstractCard, AbstractCard> tranfigurecards = new HashMap<>();
            tranfigurecards.put(new VolcanicFissure(),this);
            addToBot(new TransfigureAction(magicNumber,this,tranfigurecards));
        }
        Wiz.dmg(m,new DamageInfo(p,damage, DamageInfo.DamageType.NORMAL));
        if (magicNumber <= 0) {
            addToBot(new DrawCardAction(1));
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    DragonknightMod.Seals.add(new MagmaMark(AbstractDungeon.player));
                    isDone = true;
                }
            });
        }
    }
    public void applyPowers() {
        if (magicNumber <= 0) {
            rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
            modifyCostForCombat(-1);
            cardsToPreview = null;
            this.initializeDescription();
        }
        super.applyPowers();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            cardsToPreview = new TransfiguredVolcanicFissure();
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            cardsToPreview.upgrade();
            initializeDescription();
        }
    }
}
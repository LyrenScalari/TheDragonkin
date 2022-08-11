package theDragonkin.cards.Dragonkin;

import basemod.devcommands.power.Power;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.CommonKeywordIconsField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import theDragonkin.DragonkinMod;
import theDragonkin.actions.CycleAction;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.Dragonkin.PenancePower;
import theDragonkin.powers.Dragonkin.Scorchpower;
import theDragonkin.powers.Dragonkin.SteelWillPower;

import java.util.ArrayList;
import java.util.List;

import static theDragonkin.DragonkinMod.makeCardPath;

public class SteelWill extends AbstractPrimalCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * WindWalkerDefend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DragonkinMod.makeID(SteelWill.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final int COST = 0;
    private static final int BLOCK = 8;
    private static final int UPGRADE_PLUS_BLOCK = 3;


    // /STAT DECLARATION/


    public SteelWill() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = 3;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        cards.add(AbstractDungeon.player.drawPile.getTopCard());
        cards.add(AbstractDungeon.player.drawPile.getNCardFromTop(1));
        cards.add(AbstractDungeon.player.drawPile.getNCardFromTop(2));
        ArrayList<AbstractPower> Burdens = new ArrayList<>();
        Burdens.add(new WeakPower(p,magicNumber,false));
        Burdens.add(new VulnerablePower(p,magicNumber,false));
        Burdens.add(new FrailPower(p,magicNumber,false));
        Burdens.add(new Scorchpower(p,p,magicNumber));
        Burdens.add(new PenancePower(p,p,magicNumber));
        Burdens.add(new ConstrictedPower(p,p,magicNumber));
        addToBot(new SelectCardsCenteredAction(cards,1,"Choose one to add to hand, others will be discarded.",false,card -> true, (List) -> {
            for (AbstractCard c : List){
                cards.remove(c);
                AbstractDungeon.player.drawPile.group.remove(c);
                AbstractDungeon.player.drawPile.addToTop(c);
                addToTop(new DrawCardAction(1));
            }
            for (AbstractCard c : cards){
                addToBot(new AbstractGameAction() {
                             @Override
                             public void update() {
                                 AbstractDungeon.player.drawPile.group.remove(c);
                                 AbstractDungeon.player.discardPile.addToTop(c);
                                 triggerOnManualDiscard();
                                 isDone = true;
                             }
                         }
                );
            }
        }));
        addToBot(new ApplyPowerAction(p,p,Burdens.get(AbstractDungeon.miscRng.random(Burdens.size()-1))));
        super.use(p,m);
    }


    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            isInnate = true;
            initializeDescription();
        }
    }
}
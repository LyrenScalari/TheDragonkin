package theDragonkin.cards.Drifter;

import basemod.AutoAdd;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.helpers.ModalChoice;
import basemod.helpers.ModalChoiceBuilder;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.CardMods.ForetellCardMod;
import theDragonkin.DragonkinMod;
import theDragonkin.cards.Dragonkin.AbstractDragonkinCard;
import theDragonkin.characters.TheDefault;

import java.util.List;

import static theDragonkin.DragonkinMod.makeCardPath;
@AutoAdd.Ignore
public class ForetellTest extends AbstractDragonkinCard implements ForetellCard, ModalChoice.Callback {


    // TEXT DECLARATION

    public static final String ID = DragonkinMod.makeID(ForetellTest.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("WindwalkerStrike.png");// "public static final String IMG = makeCardPath("FlameweaverStrike.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.BASIC; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;

    private static final int COST = 1;  // COST = 1
    private static final int UPGRADED_COST = 1; // UPGRADED_COST = 1

    private static final int DAMAGE = 6;    // DAMAGE = 6
    private static final int UPGRADE_PLUS_DMG = 4;  // UPGRADE_PLUS_DMG = 4
    private boolean Foretold = false;
    private ModalChoice modal;
    // /STAT DECLARATION/
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public ForetellTest(){
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
        damage = baseDamage =DAMAGE;
        magicNumber = baseMagicNumber = 4;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 3;
        CardModifierManager.addModifier(this,new ForetellCardMod(magicNumber));
        modal = new ModalChoiceBuilder()
                .setCallback(this) // Sets callback of all the below options to this
                .setColor(TheDefault.Enums.Justicar_Red_COLOR) // Sets color of any following cards to red
                .addOption("Deal "  + damage  + " Damage", CardTarget.ENEMY)
                .setColor(TheDefault.Enums.Justicar_Red_COLOR) // Sets color of any following cards to green
                .addOption("thedragonkin:Foretell this card NL Exhausting it and gaining [E] play it later for free and gain " + magicNumber + " thedragonkin:Temporal_Flux", CardTarget.NONE)
                .create();
    }

    public void triggerAtStartOfTurn() {
        if (Foretold && AbstractDungeon.actionManager.turnHasEnded){
            AbstractCard that = this;
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    AbstractDungeon.player.exhaustPile.removeCard(that);
                    AbstractDungeon.player.limbo.addToBottom(that);
                    that.stopGlowing();
                    that.unhover();
                    that.unfadeOut();
                    that.exhaust = false;
                    AbstractDungeon.player.hand.addToBottom(that);
                    isDone = true;
                }
            });
        }
    }
    public void triggerOnEndOfTurnForPlayingCard() {
        if (Foretold) {
            this.dontTriggerOnUseCard = true;
            this.exhaust = true;
            AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
        }
    }
        // Uses the titles and descriptions of the option cards as tooltips for this card
    @Override
    public List<TooltipInfo> getCustomTooltips()
    {
        return modal.generateTooltips();
    }
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (this.Foretold) {
                return true;
        } else return super.canUse(p,m);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if (Foretold && !AbstractDungeon.actionManager.turnHasEnded){
            addToBot(new DamageAction(m,new DamageInfo(p,damage)));
            addToBot(new ScryAction(defaultSecondMagicNumber));
            AbstractCard that = this;
            addToBot(new AbstractGameAction() {
                         @Override
                         public void update() { for (AbstractCardModifier ca : CardModifierManager.modifiers(that)) {
                                 if (ca instanceof ForetellCardMod) {
                                     ((ForetellCardMod) ca).Foretold = false;
                                 }
                             }
                             rawDescription = cardStrings.DESCRIPTION;
                         isDone = true;
                         }
                     });
            Foretold = false;
        } else {
            modal.open();
        }
    }

    // This is called when one of the option cards us chosen
    public void optionSelected(AbstractPlayer p, AbstractMonster m, int i)
    {
        switch (i) {
            case 0:
                addToBot(new DamageAction(m,new DamageInfo(p,damage)));
                break;
            case 1:
                Foretold = true;
                for (AbstractCardModifier ca : CardModifierManager.modifiers(this)){
                    if (ca instanceof ForetellCardMod){
                        ((ForetellCardMod)ca).Foretell = true;
                        ((ForetellCardMod)ca).Foretold = true;
                    }
                }
                break;
            default:
                return;
        }
    }

    @Override
    public void upgrade()
    {
        if (!upgraded) {
            upgradeName();
        }
    }

    @Override
    public void Foretold() {
        exhaust = true;
        for (AbstractCardModifier ca : CardModifierManager.modifiers(this)){
            if (ca instanceof ForetellCardMod){
                ((ForetellCardMod)ca).Foretell = false;
            }
        }
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
    }
}
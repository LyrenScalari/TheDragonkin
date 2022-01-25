package theDragonkin.cards.WindWalker;

import IconsAddon.cardmods.AddIconToDescriptionMod;
import IconsAddon.icons.WindIcon;
import IconsAddon.util.DamageModifierHelper;
import IconsAddon.util.DamageModifierManager;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.helpers.ModalChoice;
import basemod.helpers.ModalChoiceBuilder;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.DragonkinMod;
import theDragonkin.characters.TheDefault;
import theDragonkin.characters.TheWindWalker;
import theDragonkin.orbs.DragonShouts.Frostbreath.Diin;
import theDragonkin.orbs.DragonShouts.Frostbreath.Fo;
import theDragonkin.orbs.DragonShouts.Frostbreath.Krah;

import static theDragonkin.DragonkinMod.makeCardPath;

public class FrostStep extends AbstractWindWalkerCard implements ModalChoice.Callback {


    // TEXT DECLARATION

    public static final String ID = DragonkinMod.makeID(FrostStep.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("SunriseStrike.png");// "public static final String IMG = makeCardPath("FlameweaverStrike.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheWindWalker.Enums.WindWalker_Jade_COLOR;

    private static final int COST = 1;  // COST = 1
    private static final int UPGRADED_COST = 1; // UPGRADED_COST = 1

    private static final int DAMAGE = 6;    // DAMAGE = 6
    private static final int UPGRADE_PLUS_DMG = 2;  // UPGRADE_PLUS_DMG = 4
    private ModalChoice modal;
    // /STAT DECLARATION/

    public FrostStep(){
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
        damage = baseDamage =DAMAGE;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 1;
        modal = new ModalChoiceBuilder()
                .setCallback(this) // Sets callback of all the below options to this
                .setColor(TheWindWalker.Enums.WindWalker_Jade_COLOR) // Sets color of any following cards to red
                .addOption("Channel thedragonkin:Fo ", CardTarget.NONE)
                .setColor(TheWindWalker.Enums.WindWalker_Jade_COLOR) // Sets color of any following cards to green
                .addOption("Channel thedragonkin:Krah", CardTarget.NONE)
                .setColor(TheWindWalker.Enums.WindWalker_Jade_COLOR) // Sets color of any following cards to green
                .addOption("Channel thedragonkin:Diin", CardTarget.NONE)
                .create();
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        modal.open();
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }

    @Override
    public void optionSelected(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster, int i) {
        switch (i) {
            case 0:
                addToBot(new ChannelAction(new Fo()));
                break;
            case 1:
                addToBot(new ChannelAction(new Krah()));
                break;
            case 2:
                addToBot(new ChannelAction(new Diin()));
                break;
            default:
                return;
        }
    }
}
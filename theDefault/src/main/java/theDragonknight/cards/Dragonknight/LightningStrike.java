package theDragonknight.cards.Dragonknight;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.CommonKeywordIconsField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.actions.utility.ExhaustToHandAction;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonknight.CustomTags;
import theDragonknight.DragonknightMod;
import theDragonknight.characters.TheDragonknight;
import theDragonknight.orbs.DragonShouts.StormMark;
import theDragonknight.powers.FrostbitePower;
import theDragonknight.util.AbstractNotOrb;
import theDragonknight.util.Wiz;

import java.util.ArrayList;
import java.util.List;

import static theDragonknight.DragonknightMod.makeCardPath;

public class LightningStrike extends AbstractDragonknightCard {

    public static final String ID = DragonknightMod.makeID(LightningStrike.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("WindwalkerStrike.png");// "public static final String IMG = makeCardPath("FlameweaverStrike.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheDragonknight.Enums.Dragonknight_Crimson_Color;

    private static final int COST = 3;  // COST = 1
    private static final int UPGRADED_COST = 1; // UPGRADED_COST = 1

    private static final int DAMAGE = 4;    // DAMAGE = 6
    private static final int UPGRADE_PLUS_DMG = 4;  // UPGRADE_PLUS_DMG = 4

    // /STAT DECLARATION/
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonknight:Static"), BaseMod.getKeywordDescription("thedragonknight:Static")));
        return retVal;
    }
    public LightningStrike(){
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
        damage = baseDamage = 14;
        magicNumber = baseMagicNumber = 3;
        this.tags.add(CardTags.STRIKE);
        tags.add(CustomTags.Draconic);
        CommonKeywordIconsField.useIcons.set(this,true);
        exhaust = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!dontTriggerOnUseCard){
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    for (AbstractNotOrb mark : DragonknightMod.Seals){
                        if (mark instanceof StormMark){
                            for (int i = 0; i < magicNumber; i++){
                                ((StormMark) mark).TriggerPassive();
                                ((StormMark) mark).WhenRemoved();
                            }
                        }
                    }
                    isDone = true;
                }
            });
        }
    }
    public void triggerOnEndOfTurnForPlayingCard() {
        this.dontTriggerOnUseCard = true;
        if (cost >-1){
            modifyCostForCombat(cost -1);
        }
        shuffleBackIntoDrawPile = true;
        exhaust = false;
        target = CardTarget.SELF;
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }
    public void triggerWhenDrawn() {
        this.dontTriggerOnUseCard = false;
        shuffleBackIntoDrawPile = false;
        target = CardTarget.ENEMY;
        exhaust = true;
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
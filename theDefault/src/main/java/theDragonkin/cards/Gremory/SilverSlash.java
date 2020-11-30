package theDragonkin.cards.Gremory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.util.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheGremory;

import static theDragonkin.DefaultMod.makeCardPath;
import static theDragonkin.cards.Gremory.AbstractMagicGremoryCard.AllCards;

public class SilverSlash extends AbstractGremoryCard {


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(SilverSlash.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("Strike.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION
    private static AbstractCard FollowUp;
    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheGremory.Enums.Gremory_Purple_Color;

    private static final int COST = 1;  // COST = 1
    private static final int UPGRADED_COST = 1; // UPGRADED_COST = 1

    private static final int DAMAGE = 8;    // DAMAGE = 6
    private static final int UPGRADE_PLUS_DMG = 2;  // UPGRADE_PLUS_DMG = 4
    private static int returncount = 0;

    // /STAT DECLARATION/


    public SilverSlash(){
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
        baseDamage =DAMAGE;
        returncount = 0;
        this.tags.add(CustomTags.Physical);
        isEthereal = true;
        isInnate = true;
        returnToHand = false;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m,new DamageInfo(p,damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        resetReturn();
        FollowUp = new BraveFollowUp();
        if (this.upgraded){
            FollowUp.upgrade();
        }
        addToBot(new MakeTempCardInHandAction(FollowUp));
        AllCards.addToBottom(FollowUp);
        if (AbstractDungeon.actionManager.cardsPlayedThisTurn.size() >= 2){
            for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn){
                if (c.type == CardType.SKILL && returncount < 1){
                    returnToHand = true;
                    returncount += 1;
                    break;
                }
            }
        }
    }
    private void resetReturn(){
        returnToHand = false;
    }

    @Override
    public void atTurnStart() {
        resetReturn();
        returncount = 0;
        super.atTurnStart();
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

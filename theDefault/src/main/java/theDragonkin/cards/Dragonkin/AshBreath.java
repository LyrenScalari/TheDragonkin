package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.Dragonkin.DragonBreaths.AshBreathEffect;
import theDragonkin.powers.Dragonkin.Scorchpower;

import static theDragonkin.DefaultMod.makeCardPath;

public class AshBreath extends AbstractPrimalCard {

    public static final String ID = DefaultMod.makeID(AshBreath.class.getSimpleName());
    public static final String IMG = makeCardPath("AshBreath.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;

    private static final int COST = 0;

    private static final int DAMAGE = 4;
    private static final int UPGRADE_PLUS_DMG = 2;
    private static final int MAGIC = 4;
    private static final int UPGRADE_MAGIC = 2;

    public AshBreath() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        block = baseBlock = DAMAGE;
        damage = baseDamage = DAMAGE;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        returnToHand = false;
        addToBot(new TalkAction(true,cardStrings.EXTENDED_DESCRIPTION[1],(float) 0.5,(float) 2.0));
        addToBot(new ApplyPowerAction(p,p,new AshBreathEffect(block,damage,this)));
        for (AbstractCard c : AbstractDungeon.player.hand.group){
            if (c.type == CardType.STATUS){
                addToBot(new ExhaustSpecificCardAction(c,AbstractDungeon.player.hand));
                this.returnToHand = true;
                break;
            }
        }
    }



    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeBlock(2);
            initializeDescription();
        }
    }
}
package theDragonkin.cards.GroveKeeper.Choices;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.CollectorCurseEffect;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.cards.GroveKeeper.AbstractChooseOneCard;
import theDragonkin.cards.GroveKeeper.AbstractGroveKeeperCard;
import theDragonkin.cards.GroveKeeper.Attacks.SwarmStrike;
import theDragonkin.characters.TheGroveKeeper;

import java.util.Iterator;

import static theDragonkin.DefaultMod.makeCardPath;
@AutoAdd.Ignore
public class SwarmAssault extends AbstractGroveKeeperCard {
    public static final String ID = DefaultMod.makeID(SwarmAssault.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.SPECIAL;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;
    public static final AbstractCard.CardColor COLOR = TheGroveKeeper.Enums.GroveKeeper_Forest_Color;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public AbstractMonster Target;
    public DamageInfo.DamageType dmgType = DamageInfo.DamageType.NORMAL;
    private static final int COST = 1;
    private static final int DAMAGE = 4;    // DAMAGE = 6
    private static final int UPGRADE_PLUS_DMG = 1;  // UPGRADE_PLUS_DMG = 4
    public static int swarmcount = 0;
    public SwarmAssault() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
        this.tags.add(CustomTags.Neutral);
    }
    public SwarmAssault(int dmg, AbstractMonster enemy) {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(CustomTags.Neutral);
        damage = baseDamage = dmg;
        Target = enemy;
    }
    public void onChoseThisOption() {
        AbstractChooseOneCard.lastchoice.addToBottom(new SwarmAssault());
        AbstractChooseOneCard.Roadsuntraveled.addToBottom(new SwarmAmbush());
        AbstractChooseOneCard.lastchoices.addToBottom(new SwarmAssault());
        AbstractChooseOneCard.lastchoices.addToBottom(new SwarmAmbush());
        addToBot(new DamageAction(Target,new DamageInfo(AbstractDungeon.player,damage,dmgType), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m,new DamageInfo(AbstractDungeon.player,damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    @Override
    public void upgrade() {
        upgradeDamage(UPGRADE_PLUS_DMG);
    }

    public static int countCards() {
        int count = 0;
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (isStrike(c)) {
                ++count;
            }
        }

        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (isStrike(c)) {
                ++count;
            }
        }

        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (isStrike(c)) {
                ++count;
            }
        }

        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (isStrike(c)) {
                ++count;
            }
        }
        swarmcount = count;
        return swarmcount;
    }

    public static boolean isStrike(AbstractCard c) {
        return c instanceof SwarmStrike;
    }
    public void calculateCardDamage(AbstractMonster mo) {
        countCards();
        int realBaseDamage = this.baseDamage;
        this.baseDamage = realBaseDamage * swarmcount;
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    public void applyPowers() {
        countCards();
        int realBaseDamage = this.baseDamage;
        this.baseDamage = realBaseDamage * swarmcount;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }
}

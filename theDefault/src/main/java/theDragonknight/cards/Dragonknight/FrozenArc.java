package theDragonknight.cards.Dragonknight;

import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonknight.CustomTags;
import theDragonknight.DragonknightMod;
import theDragonknight.characters.TheDragonknight;
import theDragonknight.util.Wiz;

import static theDragonknight.DragonknightMod.makeCardPath;

public class FrozenArc extends AbstractDragonknightCard {

    public static final String ID = DragonknightMod.makeID(FrozenArc.class.getSimpleName());
    public static final String IMG = makeCardPath("WindWalkerDefend.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final CardColor COLOR = TheDragonknight.Enums.Dragonknight_Crimson_Color;

    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int UPGRADE_PLUS_BLOCK = 3;

    public FrozenArc() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        tags.add(CustomTags.Draconic);
        block = baseBlock = 6;
        damage = baseDamage = 8;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!dontTriggerOnUseCard) {
            Wiz.dmg(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL));
        }
    }
    public void triggerOnEndOfTurnForPlayingCard() {
        this.dontTriggerOnUseCard = true;
        shuffleBackIntoDrawPile = true;
        damage += 1;
        baseDamage += 1;
        Wiz.block(AbstractDungeon.player,block);
        target = CardTarget.SELF;
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }
    public void triggerWhenDrawn() {
        this.dontTriggerOnUseCard = false;
        shuffleBackIntoDrawPile = false;
        target = CardTarget.ENEMY;
    }
    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(2);
            upgradeBlock(3);
            initializeDescription();
        }
    }
}
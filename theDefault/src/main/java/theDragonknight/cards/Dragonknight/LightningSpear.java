package theDragonknight.cards.Dragonknight;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.ExhaustToHandAction;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
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

public class LightningSpear extends AbstractDragonknightCard {

    public static final String ID = DragonknightMod.makeID(LightningSpear.class.getSimpleName());
    public static final String IMG = makeCardPath("WindWalkerDefend.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final CardColor COLOR = TheDragonknight.Enums.Dragonknight_Crimson_Color;
    private static int truebaseDmg;

    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonknight:Static"), BaseMod.getKeywordDescription("thedragonknight:Static")));
        return retVal;
    }
    public LightningSpear() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        tags.add(CustomTags.Draconic);
        magicNumber = baseMagicNumber = 1;
        truebaseDmg = damage = baseDamage = 8;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = 4;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!dontTriggerOnUseCard) {
            AbstractMonster target = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
            CardCrawlGame.sound.play("ORB_LIGHTNING_EVOKE", 0.0f);
            addToBot(new VFXAction(new LightningEffect(target.hb.cX,target.hb.cY)));
            Wiz.dmg(target,new DamageInfo(p,damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE);
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    for (AbstractNotOrb mark : DragonknightMod.Seals) {
                        if (mark instanceof StormMark) {
                            mark.PainAmount += magicNumber;
                            isDone = true;
                        }
                    }
                    if (!isDone) {
                        DragonknightMod.Seals.add(new StormMark(AbstractDungeon.player));
                        isDone = true;
                    }
                }
            });
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    damage = baseDamage = truebaseDmg;
                    isDone = true;
                }
            });
        }
    }
    public void triggerOnEndOfTurnForPlayingCard() {
        this.dontTriggerOnUseCard = true;
        damage += magicNumber;
        baseDamage += magicNumber;
        shuffleBackIntoDrawPile = true;
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }
    public void triggerWhenDrawn() {
        this.dontTriggerOnUseCard = false;
        shuffleBackIntoDrawPile = false;
    }
    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(2);
            truebaseDmg += 2;
            upgradeDefaultSecondMagicNumber(2);
            initializeDescription();
        }
    }
}
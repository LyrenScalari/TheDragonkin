package theDragonkin.cards.Gremory.Attacks;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.actions.CustomDiscoveryAction;
import theDragonkin.actions.DiscoverandShuffleAction;
import theDragonkin.cards.Gremory.AbstractInvokeCard;

import theDragonkin.characters.TheGremory;

import java.util.ArrayList;

import static theDragonkin.DefaultMod.makeCardPath;

public class TheEdgeofDawn extends AbstractInvokeCard {


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(TheEdgeofDawn.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;
    public static final AbstractCard.CardColor COLOR = TheGremory.Enums.Gremory_Purple_Color;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    public CardGroup WindSnowMoon = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

    // /STAT DECLARATION/


    public TheEdgeofDawn() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        damage = baseDamage = 12;
        magicNumber = baseMagicNumber = 2;
        CardPreview1 = new Hoarfrost();
        CardPreview2 = new FallingStar();
        CardPreview3 = new Atrocity();
        CardPreview4 = null;
        GraveField.grave.set(this,true);
        this.isEthereal = true;
        this.tags.add(CustomTags.Physical);
        WindSnowMoon.group.add(new FallingStar());
        WindSnowMoon.group.add(new Hoarfrost());
        WindSnowMoon.group.add(new Atrocity());
    }
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (AbstractDungeon.player.drawPile.isEmpty() || AbstractDungeon.player.discardPile.isEmpty()) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }

    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else if (p.drawPile.size() > 0 && p.discardPile.size() > 0) {
            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            return false;
        } else {
            return canUse;
        }
    }
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new CleaveEffect()));
        addToBot(new DamageAllEnemiesAction(p,damage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE));
        AbstractDungeon.actionManager.addToBottom(new DiscoverandShuffleAction(WindSnowMoon,3,false, card ->{}));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            isEthereal = false;
            AlwaysRetainField.alwaysRetain.set(this,true);
            initializeDescription();
        }
    }
}

package theDragonkin.cards.Dragonkin;

import basemod.devcommands.power.Power;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.stance.DivinityParticleEffect;
import org.lwjgl.Sys;
import theDragonkin.DragonkinMod;
import theDragonkin.cards.Dragonkin.interfaces.ReciveDamageEffect;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.Dragonkin.DreamingPower;
import theDragonkin.powers.Dragonkin.LunacyPower;

import java.util.ArrayList;

import static theDragonkin.DragonkinMod.makeCardPath;

public class ShadowVision extends AbstractHolyCard implements ReciveDamageEffect {

    public static final String ID = DragonkinMod.makeID(ShadowVision.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private CardGroup ShadowVisons = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int UPGRADE_PLUS_POTENCY = 0;
    private static final int MAGIC = 2;
    private static final int UPGRADE_MAGIC = 0;

    public ShadowVision() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.player.drawPile.size() >= 1) {
            ArrayList<AbstractCard> cards = new ArrayList<>(AbstractDungeon.player.drawPile.group);
            int amt = Math.min(cards.size(), 3);
            while (ShadowVisons.size() < amt) {
                ShadowVisons.addToTop(cards.remove(AbstractDungeon.miscRng.random(0,cards.size()-1)));
            }
            addToBot(new SelectCardsCenteredAction(ShadowVisons.group, 1, cardStrings.EXTENDED_DESCRIPTION[0], List -> {
                AbstractCard cardtoget = List.get(0);
                AbstractDungeon.player.hand.addToHand(cardtoget);
                AbstractDungeon.player.drawPile.removeCard(cardtoget);
                ShadowVisons.clear();
            }));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            initializeDescription();
        }
    }
    @Override
    public void onReciveDamage(int damage) {
        if (AbstractDungeon.player.hand.contains(this)) {
            CardCrawlGame.sound.play("POWER_MANTRA", 0.05F);
            AbstractDungeon.effectList.add(new DivinityParticleEffect());
            AbstractDungeon.effectList.add(new DivinityParticleEffect());
            addToBot(new DrawCardAction(AbstractDungeon.player,1));
        }
    }
}
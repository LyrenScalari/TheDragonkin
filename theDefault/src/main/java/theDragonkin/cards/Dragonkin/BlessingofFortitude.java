package theDragonkin.cards.Dragonkin;

import basemod.helpers.CardBorderGlowManager;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.RetainCardPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.SmokePuffEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import com.megacrit.cardcrawl.vfx.stance.DivinityParticleEffect;
import theDragonkin.CardMods.RetainCardMod;
import theDragonkin.DragonkinMod;
import theDragonkin.cards.Dragonkin.interfaces.ReciveDamageEffect;
import theDragonkin.characters.TheDefault;

import java.util.List;

import static theDragonkin.DragonkinMod.makeCardPath;

public class BlessingofFortitude extends AbstractHolyCard implements ReciveDamageEffect {

    public static final String ID = DragonkinMod.makeID(BlessingofFortitude.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int UPGRADE_PLUS_POTENCY = 0;
    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC = 0;
    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        float runningbaseDamage = realBaseDamage;
        for (AbstractPower p : AbstractDungeon.player.powers){
            runningbaseDamage =  p.atDamageGive(runningbaseDamage, DamageInfo.DamageType.NORMAL);
            runningbaseDamage =  p.atDamageReceive(runningbaseDamage, DamageInfo.DamageType.NORMAL);
            runningbaseDamage =  p.atDamageFinalGive(runningbaseDamage, DamageInfo.DamageType.NORMAL);
            runningbaseDamage =  p.atDamageFinalReceive(runningbaseDamage, DamageInfo.DamageType.NORMAL);
        }
        this.baseDamage = (int)runningbaseDamage;
        damage = baseDamage;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;

    }
    public BlessingofFortitude() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        baseDamage = damage = 3;
        CardBorderGlowManager.addGlowInfo(new CardBorderGlowManager.GlowInfo() {
            @Override
            public boolean test(AbstractCard abstractCard) {
                return false;
            }

            @Override
            public Color getColor(AbstractCard abstractCard) {
                return Color.GOLDENROD;
            }

            @Override
            public String glowID() {
                return "Reckoning";
            }
        });
        CardBorderGlowManager.addGlowInfo(new CardBorderGlowManager.GlowInfo() {
            @Override
            public boolean test(AbstractCard abstractCard) {
                return false;
            }

            @Override
            public Color getColor(AbstractCard abstractCard) {
                return Color.RED;
            }

            @Override
            public String glowID() {
                return "Exhaust";
            }
        });
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsInHandAction(2,cardStrings.EXTENDED_DESCRIPTION[0],AbstractCard ->{
            CardModifierManager.addModifier(AbstractCard.get(0),new RetainCardMod(1));
            CardModifierManager.addModifier(AbstractCard.get(1),new RetainCardMod(1));
        }));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
            initializeDescription();
        }
    }
    @Override
    public void onReciveDamage(int damage) {
        if (AbstractDungeon.player.hand.contains(this)) {
            addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,
                    new RetainCardPower(AbstractDungeon.player,1)));
            addToBot(new ExhaustSpecificCardAction(this,AbstractDungeon.player.hand));
        }
    }
}
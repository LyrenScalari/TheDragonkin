package theDragonkin.cards.Gremory.Attacks.Magic;

import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.CollectorStakeEffect;
import com.megacrit.cardcrawl.vfx.combat.LightFlareParticleEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import theDragonkin.CardMods.MiasmaCardMod;
import theDragonkin.CardMods.NosferatuCardMod;
import theDragonkin.CardMods.NosferatuYCardMod;
import theDragonkin.cards.Gremory.AbstractMagicGremoryCard;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheGremory;

import java.util.ArrayList;
import java.util.List;

import static theDragonkin.DefaultMod.makeCardPath;

public class Nosferatu extends AbstractMagicGremoryCard implements BranchingUpgradesCard {
    public static final String ID = DefaultMod.makeID(Nosferatu.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheGremory.Enums.Gremory_Purple_Color;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 2;
    private static float animx;
    private static float animy;
    private static ArrayList<TooltipInfo> TrapTooltip;
    @Override
    public List<TooltipInfo> getCustomTooltipsTop() {
        return TrapTooltip;
    }
    public Nosferatu() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(CustomTags.Light);
        TrapTooltip = new ArrayList<>();
        setOrbTexture(DefaultMod.Light_SMALL_ORB,DefaultMod.Light_LARGE_ORB);
        TrapTooltip.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Light"), BaseMod.getKeywordDescription("thedragonkin:Light")));
        TrapTooltip.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Afterglow"), BaseMod.getKeywordDescription("thedragonkin:Afterglow")));
        getCustomTooltips();
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
        MagDamage = baseMagDamage = 12;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.name.equals(cardStrings.NAME) || this.name.equals(cardStrings.EXTENDED_DESCRIPTION[0])){
            animx = m.drawX;
            animy = m.drawY;
            addToBot(new DamageAction(m, new DamageInfo(p,MagDamage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            if (!this.hasTag(CustomTags.Dark)) {
                addToBot(new VFXAction(new ShockWaveEffect(animx, animy, Color.YELLOW, ShockWaveEffect.ShockWaveType.ADDITIVE)));
                addToBot(new VFXAction(new LightFlareParticleEffect(animx, animy, Color.SKY)));
                addToBot(new VFXAction(new ShockWaveEffect(animx, animy, Color.GOLD, ShockWaveEffect.ShockWaveType.ADDITIVE)));
            }
            else {
                addToBot(new VFXAction(new ShockWaveEffect(animx, animy, Color.PURPLE, ShockWaveEffect.ShockWaveType.ADDITIVE)));
                addToBot(new VFXAction(new LightFlareParticleEffect(animx, animy, Color.PURPLE)));
            }
            if (upgraded) {
                for (AbstractCard c : AllCards.group) {
                    if (c instanceof AbstractMagicGremoryCard) {
                        if (!c.hasTag(CustomTags.Dark)) {
                            addToBot(new AbstractGameAction() {
                                public void update() {
                                    CardModifierManager.addModifier(c, new NosferatuCardMod(1, m.lastDamageTaken / 2));
                                    isDone = true;
                                }
                            });
                        }
                    }
                }
            } else {
                for (AbstractCard c : AllCards.group) {
                    if (c instanceof AbstractMagicGremoryCard) {
                        if (!c.hasTag(CustomTags.Dark)) {
                            addToBot(new AbstractGameAction() {
                                public void update() {
                                    CardModifierManager.addModifier(c, new NosferatuYCardMod(1, m.lastDamageTaken / 2));
                                    isDone = true;
                                }
                            });
                        }
                    }
                }
            }
        }
        else {
            addToBot(new DamageAction(m, new DamageInfo(p,MagDamage, DamageInfo.DamageType.NORMAL)));
            addToBot(new VFXAction(new CollectorStakeEffect(animx,animy)));
            for (AbstractCard c : AllCards.group) {
                if (c instanceof AbstractMagicGremoryCard) {
                    if (!c.hasTag(CustomTags.Light)) {
                        addToBot(new AbstractGameAction() {
                            public void update() {
                                CardModifierManager.addModifier(c, new MiasmaCardMod(1, 2, 2));
                                isDone = true;
                            }
                        });
                    }
                }
            }
        }
        super.use(p, m);
    }
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            if (isBranchUpgrade()) {
                branchUpgrade();
            } else {
                baseUpgrade();
            }
        }
    }

    public void baseUpgrade() {
        name = cardStrings.EXTENDED_DESCRIPTION[0];
        this.rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    public void branchUpgrade() {
        name = cardStrings.EXTENDED_DESCRIPTION[2];
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[3];
        tags.remove(CustomTags.Light);
        tags.add(CustomTags.Dark);
        TrapTooltip.clear();
        TrapTooltip.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Dark"), BaseMod.getKeywordDescription("thedragonkin:Dark")));
        TrapTooltip.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Darken"), BaseMod.getKeywordDescription("thedragonkin:Darken")));
        setOrbTexture(DefaultMod.Dark_SMALL_ORB,DefaultMod.Dark_LARGE_ORB);
        baseMagDamage -= 5;
        this.upgradeBaseCost(1);
        MagDamageUpgraded = true;
        initializeDescription();

    }
}

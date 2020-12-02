package theDragonkin.cards.Gremory.Attacks.Magic;

import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.CollectorCurseEffect;
import com.megacrit.cardcrawl.vfx.CollectorStakeEffect;
import com.megacrit.cardcrawl.vfx.combat.LightFlareParticleEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import com.megacrit.cardcrawl.vfx.scene.LightFlareLEffect;
import theDragonkin.CardMods.AbraxasCardMod;
import theDragonkin.CardMods.DarkTransmute;
import theDragonkin.cards.Gremory.AbstractMagicGremoryCard;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheGremory;

import static theDragonkin.DefaultMod.makeCardPath;

public class Hades extends AbstractMagicGremoryCard implements BranchingUpgradesCard {
    public static final String ID = DefaultMod.makeID(Hades.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static float animx;
    private static float animy;
    public static final CardColor COLOR = TheGremory.Enums.Gremory_Purple_Color;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 2;

    public Hades() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(CustomTags.Dark);
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[6] +  cardStrings.DESCRIPTION;
        magicNumber = baseMagicNumber = 1;
        initializeDescription();
        MagDamage = baseMagDamage = 15;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        animx = m.drawX;
        animy = m.drawY;
        if (this.timesUpgraded % 2 == 0) {
            addToBot(new VFXAction(new CollectorStakeEffect(animx,animy)));
            addToBot(new VFXAction(new CollectorCurseEffect(animx,animy)));
            addToBot(new DamageAction(m, new DamageInfo(p, MagDamage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
            addToBot(new SelectCardsInHandAction(magicNumber,cardStrings.EXTENDED_DESCRIPTION[7],false,false,
                    c -> !(c.hasTag(CustomTags.Dark)) && (c instanceof AbstractMagicGremoryCard || c.hasTag(CustomTags.Enchanted)),
                    list -> list.forEach(this::transmute)));
        }
        else{
            addToBot(new VFXAction(new LightFlareLEffect(animx,animy)));
            addToBot(new VFXAction(new LightningEffect(animx,animy)));
            addToBot(new VFXAction(new ShockWaveEffect(animx, animy, Color.YELLOW, ShockWaveEffect.ShockWaveType.ADDITIVE)));
            addToBot(new VFXAction(new LightFlareParticleEffect(animx, animy, Color.SKY)));
            addToBot(new VFXAction(new ShockWaveEffect(animx, animy, Color.GOLD, ShockWaveEffect.ShockWaveType.ADDITIVE)));
            addToBot(new DamageAllEnemiesAction(p,MagDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE));
            for (AbstractCard c : AllCards.group) {
                if (c instanceof AbstractMagicGremoryCard) {
                    if (!c.hasTag(CustomTags.Dark)) {
                        addToBot(new AbstractGameAction() {
                            public void update() {
                                CardModifierManager.addModifier(c, new AbraxasCardMod(1,magicNumber,m));
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

    public AbstractCard transmute(AbstractCard c){
        for (AbstractCard.CardTags tag : c.tags){
            if (tag == CustomTags.Wind){
                addToTop(new AbstractGameAction() {
                    @Override
                    public void update() {
                        CardModifierManager.addModifier(c, new DarkTransmute(CustomTags.Wind));
                        isDone = true;
                    }

                });
            }
            else if (tag == CustomTags.Thunder){
                addToTop(new AbstractGameAction() {
                    @Override
                    public void update() {
                        CardModifierManager.addModifier(c, new DarkTransmute(CustomTags.Thunder));
                        isDone = true;
                    }

                });
            }
            else if (tag == CustomTags.Fire){
                addToTop(new AbstractGameAction() {
                    @Override
                    public void update() {
                        CardModifierManager.addModifier(c, new DarkTransmute(CustomTags.Fire));
                        isDone = true;
                    }

                });
            }
            else if (tag == CustomTags.Light){
                addToTop(new AbstractGameAction() {
                    @Override
                    public void update() {
                        CardModifierManager.addModifier(c, new DarkTransmute(CustomTags.Light));
                        isDone = true;
                    }

                });
            }
            else if (tag == CustomTags.Ice){
                addToTop(new AbstractGameAction() {
                    @Override
                    public void update() {
                        CardModifierManager.addModifier(c, new DarkTransmute(CustomTags.Ice));
                        isDone = true;
                    }

                });
            }
        }
        return c;
    }

    public void baseUpgrade() {
        name = cardStrings.EXTENDED_DESCRIPTION[0];
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[6] + UPGRADE_DESCRIPTION;
        upgradeMagicNumber(1);
        initializeDescription();
    }

    public void branchUpgrade() {
        name = cardStrings.EXTENDED_DESCRIPTION[2];
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[5] + cardStrings.EXTENDED_DESCRIPTION[3];
        tags.remove(CustomTags.Dark);
        tags.add(CustomTags.Light);
        this.isMultiDamage = true;
        this.upgradeMagicNumber(4);
        this.upgradeBaseCost(1);
        MagDamageUpgraded = true;
        initializeDescription();

    }
}

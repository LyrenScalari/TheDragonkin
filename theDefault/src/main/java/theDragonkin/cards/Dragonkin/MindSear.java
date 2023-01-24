package theDragonkin.cards.Dragonkin;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.BuffParticleEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import com.megacrit.cardcrawl.vfx.combat.ViolentAttackEffect;
import theDragonkin.DragonkinMod;
import theDragonkin.characters.TheDefault;

import static theDragonkin.DragonkinMod.makeCardPath;

public class MindSear extends AbstractPrimalCard {

    public static final String ID = DragonkinMod.makeID(MindSear.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private CardGroup ShadowVisons = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int UPGRADE_PLUS_POTENCY = 0;
    private static final int MAGIC = 2;
    private static final int UPGRADE_MAGIC = 0;

    public MindSear() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        damage = baseDamage = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToTop(new ApplyPowerAction(m,p,new WeakPower(m,magicNumber,false)));
        for (AbstractPower po : m.powers){
            if (po.type == AbstractPower.PowerType.DEBUFF){
                addToBot(new VFXAction(new BuffParticleEffect(m.drawX,m.drawY)));
                addToBot(new VFXAction(new BuffParticleEffect(m.drawX,m.drawY)));
                addToBot(new VFXAction(new ShockWaveEffect(m.drawX,m.drawY, Color.NAVY, ShockWaveEffect.ShockWaveType.CHAOTIC)));
                if (!m.isDeadOrEscaped()) {
                    addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                }
                for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters){
                    if (mo != m && !mo.isDeadOrEscaped()){
                        addToBot(new VFXAction(new BuffParticleEffect(mo.drawX,mo.drawY)));
                        addToBot(new VFXAction(new BuffParticleEffect(mo.drawX,mo.drawY)));
                        addToBot(new VFXAction(new ViolentAttackEffect(mo.drawX,mo.drawY,Color.FOREST)));
                        addToBot(new DamageAction(mo,new DamageInfo(p,damage, DamageInfo.DamageType.NORMAL)));
                    }
                }
            }
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(2);
            initializeDescription();
        }
    }
}
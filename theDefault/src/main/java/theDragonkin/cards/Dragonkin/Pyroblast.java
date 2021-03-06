package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.FireBurstParticleEffect;
import com.megacrit.cardcrawl.vfx.GhostlyFireEffect;
import com.megacrit.cardcrawl.vfx.combat.FireballEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import theDragonkin.CustomTags;
import theDragonkin.DragonkinMod;
import theDragonkin.characters.TheDefault;
import theDragonkin.util.TriggerOnCycleEffect;

import static theDragonkin.DragonkinMod.makeCardPath;

public class Pyroblast extends AbstractDragonkinCard implements TriggerOnCycleEffect {

    public static final String ID = DragonkinMod.makeID(Pyroblast.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 10;
    private static final int UPGRADED_COST = 1;

    private static final int POTENCY = 100;
    private static final int UPGRADE_PLUS_DMG = 0;
    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC = 1;
    public static int currentReduction = 0;
    public int realCost = 10;

    public Pyroblast() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = POTENCY;
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(CustomTags.SnekProof);
        tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new InflameEffect(p)));
        addToBot(new VFXAction(new FireballEffect(AbstractDungeon.player.hb.cX,AbstractDungeon.player.hb.cY,m.hb.cX,m.hb.cY)));
        addToBot(new VFXAction(new InflameEffect(m)));
        addToBot(new VFXAction(new GhostlyFireEffect(m.hb.cX,m.hb.cY)));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
        addToBot(new VFXAction(new FireBurstParticleEffect(m.hb.cX,m.hb.cY)));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                cost = 10;
                realCost = cost;
                this.isDone = true;
            }
        });
    }
    public void applyPowers() {
        if (costForTurn != realCost){
            setCostForTurn(realCost);
        }
        super.applyPowers();
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            selfRetain = true;
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void TriggerOnCycle(AbstractCard ca) {
        if (ca.type == CardType.STATUS){
            this.updateCost(-1);
            realCost = costForTurn;
        }
    }
}
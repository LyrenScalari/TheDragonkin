package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.CollectorCurseEffect;
import com.megacrit.cardcrawl.vfx.DarkSmokePuffEffect;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.Dragonkin.TempEnergyDown;

import static theDragonkin.DefaultMod.makeCardPath;

public class SlimeBreath extends AbstractDragonkinCard {

    public static final String ID = DefaultMod.makeID(SlimeBreath.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 1;
    private static final int UPGRADED_COST = 2;

    private static final int POTENCY = 35;
    private static final int UPGRADE_PLUS_POTENCY = 16;
    private static final int MAGIC = 2;
    private static final int UPGRADE_MAGIC = -1;

    public SlimeBreath() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = POTENCY;
        block = baseBlock = POTENCY;
        heal = baseHeal = POTENCY;
        baseMagicNumber = magicNumber = MAGIC;
        initializeDescription();
    }
    public void applyPowers() {
        initializeDescription();
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new CollectorCurseEffect(m.drawX,m.drawY)));
        addToBot(new DamageAction(m,new DamageInfo(p,damage)));
        addToBot(new VFXAction(new DarkSmokePuffEffect(p.drawX,p.drawY)));
        addToBot(new ApplyPowerAction(p,p,new TempEnergyDown(p,magicNumber,magicNumber,false)));
    }
    @Override
    public void initializeDescription(){
        StringBuilder sb = new StringBuilder();
        sb.append(cardStrings.DESCRIPTION);
        for (int i = 0; i < this.magicNumber; ++i) {
            sb.append("[E] ");
        }
        rawDescription = sb.toString();
        super.initializeDescription();
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(2);
            upgradeDamage(UPGRADE_PLUS_POTENCY);
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }
}
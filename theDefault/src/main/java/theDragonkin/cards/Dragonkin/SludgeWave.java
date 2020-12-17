package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ConstrictedPower;
import com.megacrit.cardcrawl.powers.DrawReductionPower;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.vfx.CollectorCurseEffect;
import com.megacrit.cardcrawl.vfx.DarkSmokePuffEffect;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheDefault;

import static theDragonkin.DefaultMod.makeCardPath;

public class SludgeWave extends AbstractDragonkinCard {

    public static final String ID = DefaultMod.makeID(SludgeWave.class.getSimpleName());
    public static final String IMG = makeCardPath("SludgeWave.png");


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 1;
    private static final int UPGRADED_COST = 2;

    private static final int POTENCY = 3;
    private static final int UPGRADE_PLUS_POTENCY = 1;
    private static final int MAGIC = 2;
    private static final int UPGRADE_MAGIC = 0;

    public SludgeWave() {
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
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DiscardAction(p,p,magicNumber,true));
        addToBot(new VFXAction(new CollectorCurseEffect(p.drawX,p.drawY)));
        addToBot(new ApplyPowerAction(p,p, new DrawReductionPower(p,magicNumber),magicNumber));
        addToBot(new VFXAction(new DarkSmokePuffEffect(p.drawX,p.drawY)));
        addToBot(new ApplyPowerAction(p,p, new EnergizedPower(p,magicNumber)));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_POTENCY);
            initializeDescription();
        }
    }
}
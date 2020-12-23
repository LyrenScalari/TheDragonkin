package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;
import com.megacrit.cardcrawl.powers.ConstrictedPower;
import com.megacrit.cardcrawl.powers.watcher.EnergyDownPower;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.Dragonkin.TempEnergyDown;

import static theDragonkin.DefaultMod.makeCardPath;

public class BindingSlime extends AbstractDragonkinCard {

    public static final String ID = DefaultMod.makeID(BindingSlime.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");


    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int POTENCY = 25;
    private static final int UPGRADE_PLUS_POTENCY = 5;
    private static final int MAGIC = 15;
    private static final int UPGRADE_MAGIC = -5;

    public BindingSlime() {
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
        addToBot(new GainBlockAction(p,block));
        if (p.exhaustPile.size() < magicNumber) {
            addToBot(new ApplyPowerAction(p, p, new TempEnergyDown(p, magicNumber, 1, false)));
        }
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
            upgradeBlock(UPGRADE_PLUS_POTENCY);
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }
}
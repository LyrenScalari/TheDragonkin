package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheDefault;

import static theDragonkin.DefaultMod.makeCardPath;

public class Detox extends AbstractDragonkinCard {

    public static final String ID = DefaultMod.makeID(Detox.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;

    private static final int POTENCY = 0;
    private static final int UPGRADE_PLUS_POTENCY = 0;
    private static final int MAGIC = 0;
    private static final int UPGRADE_MAGIC = 0;

    public Detox() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = POTENCY;
        block = baseBlock = POTENCY;
        heal = baseHeal = POTENCY;
        baseMagicNumber = magicNumber = MAGIC;
        this.exhaust = true;
        this.isEthereal = true;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            for (AbstractPower power : mo.powers){
                if (power.type == AbstractPower.PowerType.DEBUFF) {
                    int max = Math.max(1,power.amount);
                    for (int i = 0; i < max; ++i) {
                        AbstractDungeon.actionManager.addToBottom(
                                new ApplyPowerAction(p,p, new StrengthPower(p,1),1));
                        AbstractDungeon.actionManager.addToBottom(
                                new ApplyPowerAction(p,p, new DexterityPower(p,1),1));
                        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(m,p,power,1));
                    }
                }
            }
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            this.isEthereal = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
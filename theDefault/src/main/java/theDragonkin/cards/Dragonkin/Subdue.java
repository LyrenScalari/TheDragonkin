package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheDefault;

import static theDragonkin.DefaultMod.makeCardPath;

public class Subdue extends AbstractHolyBonusCard {

    public static final String ID = DefaultMod.makeID(Subdue.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;

    private static final int POTENCY = 0;
    private static final int UPGRADE_PLUS_DMG = 0;
    private static final int MAGIC = 0;
    private static final int UPGRADE_MAGIC = 0;

    public Subdue() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = POTENCY;
        block = baseBlock = POTENCY;
        heal = baseHeal = POTENCY;
        tags.add(CustomTags.HOLY_CARD);
        baseMagicNumber = magicNumber = MAGIC;
        this.exhaust = true;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int enemyStrength = 0;
        if (m.hasPower(StrengthPower.POWER_ID)){
            enemyStrength = m.getPower(StrengthPower.POWER_ID).amount;
        }
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(m, p, new StrengthPower(m, -((m.getIntentBaseDmg() + enemyStrength)-1))));
        if (!m.hasPower("Artifact")) {
            this.addToBot(new ApplyPowerAction(m, p, new GainStrengthPower(m, (m.getIntentBaseDmg() + + enemyStrength)-1)));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            updateCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}
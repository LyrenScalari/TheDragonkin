package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheDefault;

import static theDragonkin.DefaultMod.makeCardPath;

public class SlimeBreath extends AbstractDragonkinCard {

    public static final String ID = DefaultMod.makeID(SlimeBreath.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF_AND_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int POTENCY = 1;
    private static final int UPGRADE_PLUS_POTENCY = 1;
    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC = 0;

    public SlimeBreath() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = POTENCY;
        block = baseBlock = POTENCY;
        heal = baseHeal = POTENCY;
        baseMagicNumber = magicNumber = MAGIC;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractPower power : m.powers) {
            if (!upgraded) {
                if (power.ID.equals(ConstrictedPower.POWER_ID)) {
                    AbstractDungeon.actionManager.addToBottom(
                            new GainBlockAction(p, m.getPower(ConstrictedPower.POWER_ID).amount));
                }
            } else if (power.ID.equals(ConstrictedPower.POWER_ID)) {
                AbstractDungeon.actionManager.addToBottom(
                        new GainBlockAction(p, m.getPower(ConstrictedPower.POWER_ID).amount * 2));
            }
        }
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_POTENCY);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
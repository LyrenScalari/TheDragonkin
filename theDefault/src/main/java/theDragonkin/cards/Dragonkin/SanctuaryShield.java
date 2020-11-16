package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheDefault;

import static theDragonkin.DefaultMod.makeCardPath;

public class SanctuaryShield extends AbstractHolyBonusCard {

    public static final String ID = DefaultMod.makeID(SanctuaryShield.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 2;

    private static final int POTENCY = 10;
    private static final int UPGRADE_PLUS_POTENCY = 3;
    private static final int MAGIC = 2;
    private static final int UPGRADE_MAGIC = 1;

    public SanctuaryShield() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = POTENCY;
        block = baseBlock = POTENCY;
        heal = baseHeal = POTENCY;
        tags.add(CustomTags.HOLY_CARD);
        baseMagicNumber = magicNumber = MAGIC;

    }
    public void tookDamage(){
        this.baseBlock += magicNumber;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new GainBlockAction(p,block));
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
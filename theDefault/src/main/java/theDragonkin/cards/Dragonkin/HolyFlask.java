package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.AcidMarkPower;

import static theDragonkin.DefaultMod.makeCardPath;

public class HolyFlask extends AbstractHolyBonusCard {

    public static final String ID = DefaultMod.makeID(HolyFlask.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 2;

    private static final int POTENCY = 0;
    private static final int UPGRADE_PLUS_POTENCY = 0;
    private static final int MAGIC = 3;
    private static final int UPGRADE_MAGIC = 1;

    public HolyFlask() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = POTENCY;
        block = baseBlock = POTENCY;
        heal = baseHeal = POTENCY;
        tags.add(CustomTags.HOLY_CARD);
        baseMagicNumber = magicNumber = MAGIC;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < this.magicNumber; ++i) {
            m = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng);
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(m, p, new AcidMarkPower(m,p, this.magicNumber), this.magicNumber, false,AbstractGameAction.AttackEffect.LIGHTNING) );

        }    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_POTENCY);
            upgradeBaseCost(UPGRADED_COST);
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }
}
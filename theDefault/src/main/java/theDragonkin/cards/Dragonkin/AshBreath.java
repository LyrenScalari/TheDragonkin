package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.util.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.Scorchpower;

import static theDragonkin.DefaultMod.makeCardPath;

public class AshBreath extends AbstractDragonkinCard {

    public static final String ID = DefaultMod.makeID(AshBreath.class.getSimpleName());
    public static final String IMG = makeCardPath("AshBreath.png");


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int DAMAGE = 8;
    private static final int UPGRADE_PLUS_DMG = 2;
    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC = 1;

    public AshBreath() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        tags.add(CustomTags.HOLY_CARD);
        baseMagicNumber = magicNumber = MAGIC;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToTop(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));

        for (AbstractCard c : AbstractDungeon.player.drawPile.group){
            if (c.cardID.equals(Burn.ID)){
                AbstractDungeon.actionManager.addToBottom(
                        new ApplyPowerAction(m,p, new Scorchpower(m,p,magicNumber)));

                        AbstractDungeon.actionManager.addToBottom(
                                new MakeTempCardInDiscardAction(new Burn(),1));
            }
        }
    }



    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }
}
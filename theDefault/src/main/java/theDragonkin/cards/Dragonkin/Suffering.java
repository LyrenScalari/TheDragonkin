package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.DragonkinMod;

import static theDragonkin.DragonkinMod.makeCardPath;

public class Suffering extends AbstractDragonkinCard {

    public static final String ID = DragonkinMod.makeID(Suffering.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");


    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.CURSE;
    public static final CardColor COLOR = CardColor.CURSE;

    private static final int COST = -2;
    private static final int UPGRADED_COST = -2;

    private static final int POTENCY = 0;
    private static final int UPGRADE_PLUS_POTENCY = 0;
    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC = 0;

    public Suffering() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = POTENCY;
        block = baseBlock = POTENCY;
        heal = baseHeal = POTENCY;
        baseMagicNumber = magicNumber = MAGIC;
        isEthereal = true;
    }
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m){
        return false;
    }

    @Override
    public void upgrade() {
    }
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        this.addToTop(new DamageAction(AbstractDungeon.player,new DamageInfo(AbstractDungeon.player,1)));
    }
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }
}

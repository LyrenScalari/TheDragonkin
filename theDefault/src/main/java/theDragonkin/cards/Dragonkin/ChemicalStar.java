package theDragonkin.cards.Dragonkin;

import com.evacipated.cardcrawl.mod.stslib.actions.common.DamageCallbackAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.CustomTags;
import theDragonkin.DragonkinMod;
import theDragonkin.actions.SmiteAction;
import theDragonkin.characters.TheDefault;

import static theDragonkin.DragonkinMod.makeCardPath;

public class ChemicalStar extends AbstractDragonkinCard {

    public static final String ID = DragonkinMod.makeID(ChemicalStar.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int DAMAGE = 16;
    private static final int UPGRADE_PLUS_DMG = 1;
    private static final int MAGIC = 6;
    private static final int UPGRADE_MAGIC = 0;

    public ChemicalStar() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = 3;
        cardsToPreview = new Burn();
        tags.add(CustomTags.Smite);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(p,new DamageInfo(p,magicNumber, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
        addToBot(new SmiteAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL)));
        addToBot(new MakeTempCardInDiscardAction(new Burn(),1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}
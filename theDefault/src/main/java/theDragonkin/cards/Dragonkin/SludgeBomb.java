package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.DarkSmokePuffEffect;
import com.megacrit.cardcrawl.vfx.combat.*;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheDefault;

import static theDragonkin.DefaultMod.makeCardPath;

public class SludgeBomb extends AbstractDragonkinCard {

    public static final String ID = DefaultMod.makeID(SludgeBomb.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 2;

    private static final int POTENCY = 10;
    private static final int UPGRADE_PLUS_POTENCY = 4;
    private static final int MAGIC = 5;
    private static final int UPGRADE_MAGIC = 3;

    public SludgeBomb() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = POTENCY;
        block = baseBlock = POTENCY;
        heal = baseHeal = POTENCY;
        baseMagicNumber = magicNumber = MAGIC;
        cardsToPreview = new ObsidianShard();

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new DarkSmokePuffEffect(p.drawX,p.drawY)));
        addToBot(new VFXAction(new InflameEffect(p)));
        addToBot(new LoseHPAction(p,p,magicNumber));
        addToBot(new VFXAction(new WhirlwindEffect()));
        addToBot(new VFXAction(new CleaveEffect()));
        addToBot(new DamageAllEnemiesAction(p,multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE));
        addToBot(new VFXAction(new IceShatterEffect(p.drawX,p.drawY)));
        addToBot(new MakeTempCardInDrawPileAction(new ObsidianShard(),magicNumber,true,false));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_POTENCY);
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }
}
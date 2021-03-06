package theDragonkin.cards.Dragonkin;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.DragonkinMod;
import theDragonkin.actions.CycleAction;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.Dragonkin.PenancePower;

import static theDragonkin.DragonkinMod.makeCardPath;

public class Liebreaker extends AbstractHolyCard {

    public static final String ID = DragonkinMod.makeID(Liebreaker.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int POTENCY= 9;
    private static final int UPGRADE_PLUS_DMG = 1;
    private static final int MAGIC = 2;
    private static final int UPGRADE_MAGIC = 1;

    public Liebreaker() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = POTENCY;
        block = baseBlock = POTENCY;
        heal = baseHeal = POTENCY;
        baseMagicNumber = magicNumber = MAGIC;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsInHandAction(magicNumber," Cycle",false,false,(card)->true,(List)-> {
            for (AbstractCard c : List){
                addToBot(new CycleAction(c,1));
                if (c.type == CardType.STATUS){
                    AbstractMonster mo = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
                    addToBot(new ApplyPowerAction(mo,p,new PenancePower(mo,p,defaultSecondMagicNumber)));
                }
            }
        }));

        super.use(p,m);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDefaultSecondMagicNumber(1);
        }
    }
}
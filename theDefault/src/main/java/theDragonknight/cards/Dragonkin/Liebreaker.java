package theDragonknight.cards.Dragonkin;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonknight.DragonkinMod;
import theDragonknight.actions.CycleAction;
import theDragonknight.characters.TheDefault;
import theDragonknight.orbs.WrathSeal;

import static theDragonknight.DragonkinMod.makeCardPath;

public class Liebreaker extends AbstractHolyCard {

    public static final String ID = DragonkinMod.makeID(Liebreaker.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int POTENCY= 8;
    private static final int UPGRADE_PLUS_DMG = 1;
    private static final int MAGIC = 3;
    private static final int UPGRADE_MAGIC = 1;

    public Liebreaker() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = POTENCY;
        block = baseBlock = POTENCY;
        heal = baseHeal = POTENCY;
        baseMagicNumber = magicNumber = MAGIC;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 5;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsInHandAction(magicNumber," Cycle",false,false,(card)->true,(List)-> {
            addToBot(new DamageAction(p,new DamageInfo(p,defaultSecondMagicNumber, DamageInfo.DamageType.THORNS)));
            for (AbstractCard c : List){
                addToBot(new CycleAction(c,1));
                if (c instanceof AbstractHolyCard){
                    addToBot(new AbstractGameAction() {
                        @Override
                        public void update() {
                            DragonkinMod.Seals.add(new WrathSeal(damage,magicNumber));
                            isDone = true;
                        }
                    });
                }
            }
        }));

        super.use(p,m);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}
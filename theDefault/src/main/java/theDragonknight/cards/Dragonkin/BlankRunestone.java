package theDragonknight.cards.Dragonkin;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonknight.DragonkinMod;
import theDragonknight.actions.CycleAction;
import theDragonknight.characters.TheDefault;

import static theDragonknight.DragonkinMod.getRandomRune;
import static theDragonknight.DragonkinMod.makeCardPath;

public class BlankRunestone extends AbstractPrimalCard {

    public static final String ID = DragonkinMod.makeID(BlankRunestone.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;


    public BlankRunestone() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        block = baseBlock = 6;
        magicNumber = baseMagicNumber = 2;
        cardToPreview.add(new BlessedWeapon());
        cardToPreview.add(new SpiritFireRune());
        cardToPreview.add(new ShatterRune());
        cardToPreview.add(new BladeMirrorRune());
        cardToPreview.add(new WarHungerRune());
        cardToPreview.add(new BlazingBreath());
        cardToPreview.add(new FlameWard());
        cardToPreview.add(new Condemnation());
        cardToPreview.add(new Flashpoint());
        cardToPreview.add(new IncendiaryFlow());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsInHandAction(magicNumber," Cycle",false,false,(card)->true,(List)-> {
            for (AbstractCard c : List){
                addToBot(new CycleAction(c,1,getRandomRune()));
            }
        }));
        super.use(p,m);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}
package theDragonkin.cards.Dragonkin;

import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NoDrawPower;
import theDragonkin.CardMods.StormEffect;
import theDragonkin.DragonkinMod;
import theDragonkin.cards.Dragonkin.interfaces.StormCard;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.Dragonkin.HeatPower;

import static theDragonkin.DragonkinMod.makeCardPath;

public class Flashpoint extends AbstractPrimalCard implements StormCard {

    public static final String ID = DragonkinMod.makeID(Flashpoint.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;


    public Flashpoint() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = 3;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 2;
        StormRate = 6;
        CardModifierManager.addModifier(this, new StormEffect(StormRate));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new HeatPower(p,p,magicNumber)));
        if (!(AbstractDungeon.player.hand.size() >= BaseMod.MAX_HAND_SIZE) || !AbstractDungeon.player.hasPower(NoDrawPower.POWER_ID)){
            int burncount = 0;
            for (AbstractCard c : AbstractDungeon.player.drawPile.group){
                if (c.type == CardType.STATUS){
                    burncount++;
                    if (burncount >= magicNumber){
                        break;
                    }
                    addToBot(new AbstractGameAction() {
                        @Override
                        public void update() {
                            AbstractDungeon.player.drawPile.group.remove(c);
                            AbstractDungeon.player.drawPile.addToTop(c);
                            isDone = true; }
                    });
                    addToBot(new DrawCardAction(1));
                }
            }
        }
        if (!Storm){
            super.use(p,m);
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
            initializeDescription();
        }
    }

    @Override
    public void onStorm() {

    }
}
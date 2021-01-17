package theDragonkin.cards.GroveKeeper.Choices;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import theDragonkin.DefaultMod;
import theDragonkin.cards.GroveKeeper.AbstractGroveKeeperCard;
import theDragonkin.characters.TheGroveKeeper;
import theDragonkin.orbs.AbstractGrovekeeperOrb;

import java.util.ArrayList;

import static theDragonkin.DefaultMod.makeCardPath;
@AutoAdd.Ignore
public class TimetoSow  extends AbstractGroveKeeperCard {
    public static final String ID = DefaultMod.makeID(TimetoSow.class.getSimpleName());
    public static final String IMG = makeCardPath("Power.png");


    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.SPECIAL;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.POWER;
    public static final AbstractCard.CardColor COLOR = TheGroveKeeper.Enums.GroveKeeper_Forest_Color;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 2;

    public TimetoSow() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }
    public void onChoseThisOption() {
        ArrayList<AbstractCard> stanceChoices = new ArrayList();
        stanceChoices.add(new EverbloomInvig());
        stanceChoices.add(new EverbloomThorn());
        stanceChoices.add(new EverbloomPrimal());
        stanceChoices.add(new EverbloomToxic());
        stanceChoices.add(new EverbloomLife());
        addToBot(new ChooseOneAction(stanceChoices));
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.onChoseThisOption();
    }
    @Override
    public void upgrade() {
    }
}
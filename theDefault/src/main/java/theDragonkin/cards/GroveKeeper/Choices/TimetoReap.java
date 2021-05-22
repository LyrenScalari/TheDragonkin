package theDragonkin.cards.GroveKeeper.Choices;

import basemod.AutoAdd;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import theDragonkin.DragonkinMod;
import theDragonkin.cards.GroveKeeper.AbstractGroveKeeperCard;
import theDragonkin.characters.TheGroveKeeper;
import theDragonkin.orbs.AbstractGrovekeeperOrb;
import theDragonkin.relics.Grovekeeper.GrovekeeperStarting;

import static theDragonkin.DragonkinMod.makeCardPath;
@AutoAdd.Ignore
public class TimetoReap extends AbstractGroveKeeperCard {
    public static final String ID = DragonkinMod.makeID(TimetoReap.class.getSimpleName());
    public static final String IMG = makeCardPath("Power.png");


    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.SPECIAL;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.POWER;
    public static final AbstractCard.CardColor COLOR = TheGroveKeeper.Enums.GroveKeeper_Forest_Color;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 2;

    public TimetoReap() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }
    public void onChoseThisOption() {
        for (AbstractOrb orb : AbstractDungeon.player.orbs){
            if (orb instanceof AbstractGrovekeeperOrb){
                ((AbstractGrovekeeperOrb) orb).onHarvest(orb.passiveAmount);
                addToBot(new GainEnergyAction(1));
                addToBot(new DrawCardAction(AbstractDungeon.player,1));
                if (AbstractDungeon.player.hasRelic(GrovekeeperStarting.ID)){
                    addToBot(new AddTemporaryHPAction(AbstractDungeon.player,AbstractDungeon.player,5));
                }
            }
        }
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.onChoseThisOption();
    }
    @Override
    public void upgrade() {
    }
}

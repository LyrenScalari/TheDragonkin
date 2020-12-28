package theDragonkin.cards.GroveKeeper.Choices;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.DefaultMod;
import theDragonkin.cards.GroveKeeper.AbstractGroveKeeperCard;
import theDragonkin.characters.TheGroveKeeper;
import theDragonkin.orbs.InvigoratingBloom;
import theDragonkin.orbs.ToxicBloom;

import static theDragonkin.DefaultMod.makeCardPath;
@AutoAdd.Ignore
public class EverbloomToxic extends AbstractGroveKeeperCard {
    public static final String ID = DefaultMod.makeID(EverbloomToxic.class.getSimpleName());
    public static final String IMG = makeCardPath("Power.png");


    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.SPECIAL;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.POWER;
    public static final AbstractCard.CardColor COLOR = TheGroveKeeper.Enums.GroveKeeper_Forest_Color;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 2;

    public EverbloomToxic() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }
    public void onChoseThisOption() {
        addToBot(new ChannelAction(new ToxicBloom()));
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.onChoseThisOption();
    }
    @Override
    public void upgrade() {
    }
}

package theDragonkin.cards.GroveKeeper.Skills;

import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.cards.GroveKeeper.AbstractChooseOneCard;
import theDragonkin.cards.GroveKeeper.Choices.EverbloomInvig;
import theDragonkin.cards.GroveKeeper.Choices.EverbloomThorn;
import theDragonkin.cards.GroveKeeper.Choices.TimetoReap;
import theDragonkin.cards.GroveKeeper.Choices.TimetoSow;
import theDragonkin.characters.TheGroveKeeper;

import java.util.ArrayList;

import static theDragonkin.DefaultMod.makeCardPath;

public class NaturesWisdom extends AbstractChooseOneCard {
    public static final String ID = DefaultMod.makeID(NaturesWisdom.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    private static final AbstractCard.CardRarity RARITY = CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = CardType.SKILL;
    public static final AbstractCard.CardColor COLOR = TheGroveKeeper.Enums.GroveKeeper_Forest_Color;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 2;  // COST = 1
    private static final int UPGRADED_COST = 0; // UPGRADED_COST = 1
    private static final int DAMAGE = 15;    // DAMAGE = 6
    private static final int UPGRADE_PLUS_DMG = 4;  // UPGRADE_PLUS_DMG = 4
    private static final int DAMAGE2 = 1;
    private static final int BLOCK = 15;

    public NaturesWisdom() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(CustomTags.Neutral);
        damage = baseDamage = DAMAGE;
        block = baseBlock = BLOCK;
        magicNumber = baseMagicNumber = DAMAGE2;
        isMultiDamage = true;

    }

    @Override
    public void upgrade() {
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        ArrayList<AbstractCard> NextChoices = new ArrayList<>();
        NextChoices.add(new TimetoSow());
        NextChoices.add(new TimetoReap());
        if (upgraded) {
            for (AbstractCard c : NextChoices) {
                c.upgrade();
            }
        }
        addToBot(new ChooseOneAction(NextChoices));
    }
}

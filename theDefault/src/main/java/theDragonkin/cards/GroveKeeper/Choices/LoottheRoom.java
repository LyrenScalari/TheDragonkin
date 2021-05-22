package theDragonkin.cards.GroveKeeper.Choices;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.CustomTags;
import theDragonkin.DragonkinMod;
import theDragonkin.cards.GroveKeeper.AbstractChooseOneCard;
import theDragonkin.cards.GroveKeeper.AbstractGroveKeeperCard;
import theDragonkin.characters.TheGroveKeeper;

import static theDragonkin.DragonkinMod.makeCardPath;
@AutoAdd.Ignore
public class LoottheRoom extends AbstractGroveKeeperCard {
    public static final String ID = DragonkinMod.makeID(LoottheRoom.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");


    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.SPECIAL;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = CardType.SKILL;
    public static final AbstractCard.CardColor COLOR = TheGroveKeeper.Enums.GroveKeeper_Forest_Color;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int DAMAGE = 15;    // DAMAGE = 6
    private static final int UPGRADE_PLUS_DMG = 4;  // UPGRADE_PLUS_DMG = 4
    public LoottheRoom() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        block = baseBlock = DAMAGE;
        this.tags.add(CustomTags.Neutral);
    }
    public LoottheRoom(int block) {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(CustomTags.Neutral);
        this.block = baseBlock = block;
    }
    public void onChoseThisOption() {
        AbstractChooseOneCard.lastchoice.addToBottom(new LoottheRoom());
        AbstractChooseOneCard.Roadsuntraveled.addToBottom(new KickintheDoor());
        AbstractChooseOneCard.Roadsuntraveled.addToBottom(new FindtheSecret());
        AbstractChooseOneCard.lastchoices.addToBottom(new KickintheDoor());
        AbstractChooseOneCard.lastchoices.addToBottom(new LoottheRoom());
        AbstractChooseOneCard.lastchoice.addToBottom(new FindtheSecret());
        addToTop(new GainBlockAction(AbstractDungeon.player,block));
        addToTop(new WaitAction((float) .02));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(AbstractDungeon.player,block));
    }

    @Override
    public void upgrade() {
        upgradeDamage(UPGRADE_PLUS_DMG);
    }
}
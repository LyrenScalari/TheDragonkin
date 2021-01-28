package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheDefault;

import static theDragonkin.DefaultMod.makeCardPath;

public class MirrorBreath extends AbstractPrimalCard {


    public static final String ID = DefaultMod.makeID(MirrorBreath.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath("Skill.png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;

    private static final int COST = 1;

    // /STAT DECLARATION/

    public MirrorBreath() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        // Temp HP + Weak
        this.magicNumber = this.baseMagicNumber = 6;
        this.defaultSecondMagicNumber = this.defaultBaseSecondMagicNumber = 4;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new TalkAction(true,cardStrings.EXTENDED_DESCRIPTION[0],(float) 0.5,(float) 2.0));
        for (int i = AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 2; i > 0; i--){
            if (AbstractDungeon.actionManager.cardsPlayedThisCombat.get(i).hasTag(CustomTags.Dragon_Breath) && !AbstractDungeon.actionManager.cardsPlayedThisCombat.get(i).purgeOnUse){

                AbstractCard tmp = AbstractDungeon.actionManager.cardsPlayedThisCombat.get(i).makeSameInstanceOf();
                AbstractDungeon.player.limbo.addToBottom(tmp);
                tmp.current_x = AbstractDungeon.actionManager.cardsPlayedThisCombat.get(i).current_x;
                tmp.current_y = AbstractDungeon.actionManager.cardsPlayedThisCombat.get(i).current_y;
                tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                tmp.target_y = (float)Settings.HEIGHT / 2.0F;
                if (m != null) {
                    tmp.calculateCardDamage(m);
                }

                tmp.purgeOnUse = true;
                AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, AbstractDungeon.actionManager.cardsPlayedThisCombat.get(i).energyOnUse,
                        true, true), true);
                break;
            }
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
            initializeDescription();
        }
    }
}
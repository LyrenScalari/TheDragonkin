package theDragonknight.cards.Dragonknight;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonknight.DragonknightMod;
import theDragonknight.cards.Dragonknight.Dragonclaws.ToxicDragonclaw;
import theDragonknight.characters.TheDragonknight;
import theDragonknight.orbs.DragonShouts.FlameMark;
import theDragonknight.orbs.DragonShouts.PlagueMark;
import theDragonknight.util.AbstractNotOrb;

import static theDragonknight.DragonknightMod.makeCardPath;

public class ToxicBreath extends AbstractDragonknightCard {


    public static final String ID = DragonknightMod.makeID(ToxicBreath.class.getSimpleName());
    public static final String IMG = makeCardPath("WindWalkerDefend.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDragonknight.Enums.Dragonknight_Crimson_Color;

    private static final int COST = 1;
    private static final int BLOCK = 8;
    private static final int UPGRADE_PLUS_BLOCK = 3;


    // /STAT DECLARATION/


    public ToxicBreath() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        magicNumber = baseMagicNumber = 3;
        cardsToPreview = new ToxicDragonclaw();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractNotOrb mark : DragonknightMod.Seals){
                    if (mark instanceof PlagueMark){
                        mark.PainAmount += 1;
                        isDone = true;
                    }
                }
                if (!isDone) {
                DragonknightMod.Seals.add(new PlagueMark(AbstractDungeon.player));
                isDone = true;
                }
            }
        });
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (!AbstractDungeon.player.hand.group.isEmpty()) {
                    addToBot(new ExhaustAction(1, false, false, false));
                    addToBot(new MakeTempCardInHandAction(new ToxicDragonclaw()));
                }
                isDone = true;
            }
        });
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
            initializeDescription();
        }
    }
}
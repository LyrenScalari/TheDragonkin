package theDragonknight.cards.Dragonknight.Token;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonknight.DragonknightMod;
import theDragonknight.cards.Dragonknight.AbstractDragonknightCard;
import theDragonknight.orbs.DragonShouts.FlameMark;
import theDragonknight.powers.ProfaneDivitation;
import theDragonknight.relics.TaintedSoul;

import static theDragonknight.DragonknightMod.makeCardPath;
@AutoAdd.Ignore
public class FlameSwap extends AbstractDragonknightCard {

    public static final String ID = DragonknightMod.makeID(FlameSwap.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("WindwalkerStrike.png");// "public static final String IMG = makeCardPath("FlameweaverStrike.png");

    private static final CardRarity RARITY = CardRarity.SPECIAL; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -2;  // COST = 1
    private static final int UPGRADED_COST = 1; // UPGRADED_COST = 1

    private static final int DAMAGE = 4;    // DAMAGE = 6
    private static final int UPGRADE_PLUS_DMG = 4;  // UPGRADE_PLUS_DMG = 4

    // /STAT DECLARATION/
    public FlameSwap(){
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
    }


    // Actions the card should do.
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.onChoseThisOption();
    }

    public void onChoseThisOption() {
        if (AbstractDungeon.player.hasRelic(TaintedSoul.ID)){
            TaintedSoul.type = new FlameMark(AbstractDungeon.player);
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
    }
}

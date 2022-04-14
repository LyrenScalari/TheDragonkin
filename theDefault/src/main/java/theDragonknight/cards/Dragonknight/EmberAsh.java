package theDragonknight.cards.Dragonknight;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonknight.DragonknightMod;
import theDragonknight.characters.TheDragonknight;
import theDragonknight.powers.RotPower;
import theDragonknight.util.Wiz;

import static theDragonknight.DragonknightMod.makeCardPath;

public class EmberAsh extends AbstractDragonknightCard {

    public static final String ID = DragonknightMod.makeID(EmberAsh.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("WindwalkerStrike.png");// "public static final String IMG = makeCardPath("FlameweaverStrike.png");

    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheDragonknight.Enums.Dragonknight_Crimson_Color;

    private static final int COST = 1;  // COST = 1
    private static final int UPGRADED_COST = 1; // UPGRADED_COST = 1

    private static final int DAMAGE = 14;    // DAMAGE = 6
    private static final int UPGRADE_PLUS_DMG = 2;  // UPGRADE_PLUS_DMG = 4

    // /STAT DECLARATION/


    public EmberAsh(){
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
        block = baseBlock = 8;
        damage = baseDamage = 4;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.block(p,block);
    }

    public void triggerOnExhaust() {
        Wiz.dmg(AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true),new DamageInfo(AbstractDungeon.player,damage, DamageInfo.DamageType.THORNS));
        Wiz.dmg(AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true),new DamageInfo(AbstractDungeon.player,damage, DamageInfo.DamageType.THORNS));
    }
    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeBlock(2);
            initializeDescription();
        }
    }
}
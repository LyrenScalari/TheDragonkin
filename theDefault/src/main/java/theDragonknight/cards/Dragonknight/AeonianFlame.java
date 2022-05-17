package theDragonknight.cards.Dragonknight;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import theDragonknight.CustomTags;
import theDragonknight.DragonknightMod;
import theDragonknight.characters.TheDragonknight;
import theDragonknight.powers.RotPower;
import theDragonknight.util.Wiz;

import java.util.ArrayList;
import java.util.List;

import static theDragonknight.DragonknightMod.makeCardPath;

public class AeonianFlame extends AbstractDragonknightCard {

    public static final String ID = DragonknightMod.makeID(AeonianFlame.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("WindwalkerStrike.png");// "public static final String IMG = makeCardPath("FlameweaverStrike.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheDragonknight.Enums.Dragonknight_Crimson_Color;

    private static final int COST = 0;  // COST = 1
    private static final int UPGRADED_COST = 1; // UPGRADED_COST = 1

    private static final int DAMAGE = 14;    // DAMAGE = 6
    private static final int UPGRADE_PLUS_DMG = 4;  // UPGRADE_PLUS_DMG = 4

    // /STAT DECLARATION/

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonknight:Rot"), BaseMod.getKeywordDescription("thedragonknight:Rot")));
        retVal.add(new TooltipInfo("[theDragonknight:PoisonIcon] " + TipHelper.capitalize(GameDictionary.POISON.NAMES[0]), GameDictionary.keywords.get(GameDictionary.POISON.NAMES[0])));
        return retVal;
    }
    public AeonianFlame(){
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
        baseDamage =DAMAGE;
        magicNumber = baseMagicNumber = 1;
        defaultSecondMagicNumber =defaultBaseSecondMagicNumber = 10;
        tags.add(CustomTags.Draconic);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToEnemy(m, new RotPower(m, p, magicNumber));
    }
    public void triggerOnExhaust() {
        AbstractMonster target = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
        Wiz.applyToEnemy(target, new RotPower(target, AbstractDungeon.player, 3));
        Wiz.applyToEnemy(target,new PoisonPower(target,AbstractDungeon.player,defaultSecondMagicNumber));

    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}
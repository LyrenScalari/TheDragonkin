package theDragonknight.cards.Dragonknight;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import theDragonknight.CustomTags;
import theDragonknight.DragonknightMod;
import theDragonknight.characters.TheDragonknight;
import theDragonknight.powers.BurnPower;
import theDragonknight.powers.LambdaPower;
import theDragonknight.powers.RotPower;
import theDragonknight.util.Wiz;

import java.util.ArrayList;
import java.util.List;

import static theDragonknight.DragonknightMod.makeCardPath;

public class IzalithsBlaze extends AbstractDragonknightCard {

    public static final String ID = DragonknightMod.makeID(IzalithsBlaze.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("WindwalkerStrike.png");// "public static final String IMG = makeCardPath("FlameweaverStrike.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.POWER;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);//
    public static final CardColor COLOR = TheDragonknight.Enums.Dragonknight_Crimson_Color;

    private static final int COST = 1;  // COST = 1
    private static final int UPGRADED_COST = 1; // UPGRADED_COST = 1

    private static final int DAMAGE = 14;    // DAMAGE = 6
    private static final int UPGRADE_PLUS_DMG = 4;  // UPGRADE_PLUS_DMG = 4

    // /STAT DECLARATION/
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonknight:Burn"), BaseMod.getKeywordDescription("thedragonknight:Burn")));
        retVal.add(new TooltipInfo("[theDragonknight:ThornsIcon] " + TipHelper.capitalize(GameDictionary.THORNS.NAMES[0]), GameDictionary.keywords.get(GameDictionary.THORNS.NAMES[0])));
        return retVal;
    }
    public IzalithsBlaze(){
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
        baseDamage =DAMAGE;
        magicNumber = baseMagicNumber = 2;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = 2;
        tags.add(CustomTags.Draconic);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new ThornsPower(p,defaultSecondMagicNumber));
        Wiz.applyToSelf(new LambdaPower(this.name, AbstractPower.PowerType.BUFF,false,p,magicNumber) {
            @Override
            public void atStartOfTurnPostDraw() {
                Wiz.applyToSelf(new BurnPower(p,p,amount));
                for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters){
                    if (!mo.isDeadOrEscaped()){
                        Wiz.applyToEnemy(mo,new BurnPower(mo,p,amount));
                    }
                }
            }
            @Override
            public void updateDescription() {
                description = cardStrings.EXTENDED_DESCRIPTION[0] + amount + cardStrings.EXTENDED_DESCRIPTION[1];
            }
        });
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            upgradeDefaultSecondMagicNumber(2);
            initializeDescription();
        }
    }
}
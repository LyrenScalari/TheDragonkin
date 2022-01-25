package theDragonkin.cards.WindWalker;

import IconsAddon.cardmods.AddIconToDescriptionMod;
import IconsAddon.icons.ElectricIcon;
import IconsAddon.icons.WaterIcon;
import IconsAddon.util.DamageModifierHelper;
import IconsAddon.util.DamageModifierManager;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.DamageModifiers.LightningDamage;
import theDragonkin.DragonkinMod;
import theDragonkin.characters.TheWindWalker;

import static theDragonkin.DragonkinMod.makeCardPath;

public class WardingZap extends AbstractWindWalkerCard {


    // TEXT DECLARATION

    public static final String ID = DragonkinMod.makeID(WardingZap.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("SunriseStrike.png");// "public static final String IMG = makeCardPath("FlameweaverStrike.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheWindWalker.Enums.WindWalker_Jade_COLOR;

    private static final int COST = 1;  // COST = 1
    private static final int UPGRADED_COST = 1; // UPGRADED_COST = 1

    private static final int DAMAGE = 6;    // DAMAGE = 6
    private static final int UPGRADE_PLUS_DMG = 3;  // UPGRADE_PLUS_DMG = 4

    // /STAT DECLARATION/


    public WardingZap(){
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
        baseDamage =DAMAGE;
        block = baseBlock = 4;
        DamageModifierManager.addModifier(this, new LightningDamage(true));
        CardModifierManager.addModifier(this,new AddIconToDescriptionMod(AddIconToDescriptionMod.DAMAGE, ElectricIcon.get()));
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, DamageModifierHelper.makeBoundDamageInfo(this, AbstractDungeon.player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        addToBot(new GainBlockAction(p,block));
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
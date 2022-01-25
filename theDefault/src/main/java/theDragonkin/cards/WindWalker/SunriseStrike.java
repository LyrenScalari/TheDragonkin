package theDragonkin.cards.WindWalker;

import IconsAddon.cardmods.AddIconToDescriptionMod;
import IconsAddon.icons.ElectricIcon;
import IconsAddon.util.DamageModifierHelper;
import IconsAddon.util.DamageModifierManager;
import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.DamageModifiers.LightningDamage;
import theDragonkin.DragonkinMod;
import theDragonkin.actions.GainChiAction;
import theDragonkin.characters.TheWindWalker;

import java.util.ArrayList;
import java.util.List;

import static theDragonkin.DragonkinMod.makeCardPath;

public class SunriseStrike extends AbstractWindWalkerCard {


    // TEXT DECLARATION

    public static final String ID = DragonkinMod.makeID(SunriseStrike.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("SunriseStrike.png");// "public static final String IMG = makeCardPath("FlameweaverStrike.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.BASIC; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheWindWalker.Enums.WindWalker_Jade_COLOR;

    private static final int COST = 1;  // COST = 1
    private static final int UPGRADED_COST = 1; // UPGRADED_COST = 1

    private static final int DAMAGE = 8;    // DAMAGE = 6
    private static final int UPGRADE_PLUS_DMG = 4;  // UPGRADE_PLUS_DMG = 4

    // /STAT DECLARATION/
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Chi"),BaseMod.getKeywordDescription("thedragonkin:Chi")));
        return retVal;
    }

    public SunriseStrike(){
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
        baseDamage =DAMAGE;
        magicNumber = baseMagicNumber = 1;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 1;
        this.tags.add(CardTags.STRIKE);
        DamageModifierManager.addModifier(this, new LightningDamage(true));
        CardModifierManager.addModifier(this,new AddIconToDescriptionMod(AddIconToDescriptionMod.DAMAGE, ElectricIcon.get()));
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m,
                DamageModifierHelper.makeBoundDamageInfo(this, p, damage, damageTypeForTurn),
                AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        for (int i = 0; i < magicNumber ; i++){
        }
        addToBot(new GainChiAction(p,defaultSecondMagicNumber));
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
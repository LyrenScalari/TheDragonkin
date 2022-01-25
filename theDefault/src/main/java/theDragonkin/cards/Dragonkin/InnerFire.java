package theDragonkin.cards.Dragonkin;

import IconsAddon.actions.GainCustomBlockAction;
import IconsAddon.cardmods.AddIconToDescriptionMod;
import IconsAddon.icons.FireIcon;
import IconsAddon.icons.HolyIcon;
import IconsAddon.icons.LightIcon;
import IconsAddon.util.BlockModifierManager;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.CustomTags;
import theDragonkin.DamageModifiers.BlockModifiers.DivineBlock;
import theDragonkin.DamageModifiers.BlockModifiers.FireBlock;
import theDragonkin.DragonkinMod;
import theDragonkin.actions.FluxAction;
import theDragonkin.actions.GainDivineArmorAction;
import theDragonkin.actions.HolyFluxAction;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.Dragonkin.AuraFlame;
import theDragonkin.powers.Dragonkin.DivineConvictionpower;
import theDragonkin.powers.Dragonkin.SacrificePower;

import static theDragonkin.DragonkinMod.makeCardPath;

public class InnerFire extends AbstractHolyCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * WindWalkerDefend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DragonkinMod.makeID(InnerFire.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;

    private static final int COST = 1;
    private static final int BLOCK = 12;
    private static final int UPGRADE_PLUS_BLOCK = 4;
    private static final int MAGIC = 2;
    private static final int UPGRADE_MAGIC = 1;


    // /STAT DECLARATION/


    public InnerFire() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC;
        block = baseBlock = BLOCK;
        BlockModifierManager.addModifier(this,new FireBlock(true));
        CardModifierManager.addModifier(this,new AddIconToDescriptionMod(AddIconToDescriptionMod.BLOCK, FireIcon.get()));
        tags.add(CustomTags.Radiant);
    }

    // Actions the card should do.

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(p,new DamageInfo(p,magicNumber, DamageInfo.DamageType.THORNS)));
        addToBot(new GainCustomBlockAction(this,p,block));
        addToBot(new HolyFluxAction(2,()-> new ApplyPowerAction(p,p,new SacrificePower(p,p,magicNumber))));
        super.use(p,m);
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDefaultSecondMagicNumber(3);
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}
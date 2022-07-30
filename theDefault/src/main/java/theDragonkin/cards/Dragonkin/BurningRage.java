package theDragonkin.cards.Dragonkin;


import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.blockmods.BlockModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import theDragonkin.CardMods.AddIconToDescriptionMod;
import theDragonkin.CustomTags;
import theDragonkin.DamageModifiers.BlockModifiers.DivineBlock;
import theDragonkin.DamageModifiers.Icons.LightIcon;
import theDragonkin.DragonkinMod;
import theDragonkin.cards.Dragonkin.interfaces.ReciveDamageEffect;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.Dragonkin.WingsofLight;

import javax.swing.*;

import java.util.List;

import static theDragonkin.DragonkinMod.makeCardPath;

public class BurningRage extends AbstractHolyCard{

    public static final String ID = DragonkinMod.makeID(BurningRage.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;

    private static final int DAMAGE = 14;
    private static final int UPGRADE_PLUS_DMG = 2;
    private static final int MAGIC = 6;
    private static final int UPGRADE_MAGIC = 0;
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = super.getCustomTooltips();
        retVal.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Blessing"),BaseMod.getKeywordDescription("thedragonkin:Blessing")));
        return retVal;
    }
    public BurningRage() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = 1;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 6;
        BlockModifierManager.addModifier(this,new DivineBlock(true));
        CardModifierManager.addModifier(this,new AddIconToDescriptionMod(AddIconToDescriptionMod.MAGIC2, LightIcon.get()));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new WingsofLight(p,p,magicNumber,defaultSecondMagicNumber,this)));
        super.use(p,m);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            isInnate = true;
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
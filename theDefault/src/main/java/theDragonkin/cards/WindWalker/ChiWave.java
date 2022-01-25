package theDragonkin.cards.WindWalker;

import IconsAddon.actions.GainCustomBlockAction;
import IconsAddon.cardmods.AddIconToDescriptionMod;
import IconsAddon.icons.ElectricIcon;
import IconsAddon.icons.SpellIcon;
import IconsAddon.util.BlockModifierManager;
import IconsAddon.util.DamageModifierManager;
import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.actions.defect.TriggerPassiveAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.DamageModifiers.ArcaneDamage;
import theDragonkin.DamageModifiers.BlockModifiers.ArcaneBlock;
import theDragonkin.DamageModifiers.LightningDamage;
import theDragonkin.DragonkinMod;
import theDragonkin.actions.ChiBurstAction;
import theDragonkin.actions.GainChiAction;
import theDragonkin.characters.TheWindWalker;
import theDragonkin.patches.ChiField;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static theDragonkin.DragonkinMod.makeCardPath;

public class ChiWave extends AbstractWindWalkerCard {


    // TEXT DECLARATION

    public static final String ID = DragonkinMod.makeID(ChiWave.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("SunriseStrike.png");// "public static final String IMG = makeCardPath("FlameweaverStrike.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.BASIC; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ALL;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheWindWalker.Enums.WindWalker_Jade_COLOR;

    private static final int COST = 0;  // COST = 1
    private static final int UPGRADED_COST = 1; // UPGRADED_COST = 1

    private static final int DAMAGE = 12;    // DAMAGE = 6
    private static final int UPGRADE_PLUS_DMG = 4;  // UPGRADE_PLUS_DMG = 4

    // /STAT DECLARATION/
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Chi"),BaseMod.getKeywordDescription("thedragonkin:Chi")));
        return retVal;
    }

    public ChiWave(){
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
        damage = baseDamage = 3;
        block = baseBlock = 3;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 1;
        DamageModifierManager.addModifier(this, new ArcaneDamage(true,true));
        CardModifierManager.addModifier(this,new AddIconToDescriptionMod(AddIconToDescriptionMod.DAMAGE, SpellIcon.get()));
        BlockModifierManager.addModifier(this, new ArcaneBlock(true));
        CardModifierManager.addModifier(this,new AddIconToDescriptionMod(AddIconToDescriptionMod.BLOCK, SpellIcon.get()));
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p,block));
        addToBot(new DamageAction(AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true),new DamageInfo(p,damage, DamageInfo.DamageType.NORMAL)));
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
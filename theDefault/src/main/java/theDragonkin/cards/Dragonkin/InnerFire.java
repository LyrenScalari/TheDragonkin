package theDragonkin.cards.Dragonkin;


import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.actions.common.GainCustomBlockAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.evacipated.cardcrawl.mod.stslib.blockmods.BlockModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.CardMods.AddIconToDescriptionMod;
import theDragonkin.CustomTags;
import theDragonkin.DamageModifiers.BlockModifiers.DivineBlock;
import theDragonkin.DamageModifiers.BlockModifiers.FireBlock;
import theDragonkin.DamageModifiers.Icons.FireIcon;
import theDragonkin.DragonkinMod;
import theDragonkin.actions.CycleAction;
import theDragonkin.actions.FluxAction;
import theDragonkin.actions.GainDivineArmorAction;
import theDragonkin.actions.HolyFluxAction;
import theDragonkin.characters.TheDefault;
import theDragonkin.orbs.WrathSeal;
import theDragonkin.powers.Dragonkin.AuraFlame;
import theDragonkin.powers.Dragonkin.DivineConvictionpower;
import theDragonkin.powers.Dragonkin.SacrificePower;

import java.util.ArrayList;
import java.util.List;

import static theDragonkin.DragonkinMod.makeCardPath;

public class InnerFire extends AbstractHolyCard {

    public static final String ID = DragonkinMod.makeID(InnerFire.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;

    private static final int COST = 1;
    private static final int BLOCK = 12;
    private static final int UPGRADE_PLUS_BLOCK = 4;
    private static final int MAGIC = 3;
    private static final int UPGRADE_MAGIC = 1;

    public InnerFire() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 2;
        block = baseBlock = BLOCK;
    }

    // Actions the card should do.

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsInHandAction(magicNumber," Discard",false,false,(card)->true,(List)-> {
            addToBot(new DamageAction(p,new DamageInfo(p,2, DamageInfo.DamageType.THORNS)));
            for (AbstractCard c : List){
                addToBot(new CycleAction(c,0));
                if (c instanceof AbstractHolyCard){
                    addToBot(new ApplyPowerAction(p,p,new SacrificePower(p,p,defaultSecondMagicNumber)));
                }
            }
        }));
        super.use(p,m);
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDefaultSecondMagicNumber(1);
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}
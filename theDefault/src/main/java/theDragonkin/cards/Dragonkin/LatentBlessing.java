package theDragonkin.cards.Dragonkin;


import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.actions.common.GainCustomBlockAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.evacipated.cardcrawl.mod.stslib.blockmods.BlockModifierManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.CardMods.AddIconToDescriptionMod;
import theDragonkin.CustomTags;
import theDragonkin.DamageModifiers.BlockModifiers.DivineBlock;
import theDragonkin.DamageModifiers.Icons.LightIcon;
import theDragonkin.DragonkinMod;
import theDragonkin.actions.CycleAction;
import theDragonkin.actions.GainDivineArmorAction;
import theDragonkin.characters.TheDefault;

import java.util.ArrayList;
import java.util.List;

import static theDragonkin.DragonkinMod.makeCardPath;

public class LatentBlessing extends AbstractHolyCard {

    public static final String ID = DragonkinMod.makeID(LatentBlessing.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 0;
    private static final int UPGRADED_COST = 1;

    private static final int POTENCY= 7;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final int MAGIC = 8;
    private static final int UPGRADE_MAGIC = 2;

    public LatentBlessing() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        block = baseBlock = 8;
        defaultSecondMagicNumber =defaultBaseSecondMagicNumber = 2;
        BlockModifierManager.addModifier(this,new DivineBlock(true));
        CardModifierManager.addModifier(this,new AddIconToDescriptionMod(AddIconToDescriptionMod.BLOCK, LightIcon.get()));
        cardsToPreview = new DivineEmber();
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        returnToHand = false;
        if (AbstractDungeon.player.hand.size() <= 2){
            if (AbstractDungeon.player.hand.getTopCard() instanceof AbstractHolyCard){
                addToBot(new CycleAction(AbstractDungeon.player.hand.getTopCard(),0));
                addToBot(new GainCustomBlockAction(this, p, block));
            } else if (AbstractDungeon.player.hand.getTopCard() instanceof AbstractPrimalCard || AbstractDungeon.player.hand.getTopCard().type == CardType.STATUS || AbstractDungeon.player.hand.getTopCard().type == CardType.CURSE){
                addToBot(new CycleAction(AbstractDungeon.player.hand.getTopCard(),0,cardsToPreview.makeStatEquivalentCopy()));
            }
        } else {
            addToBot(new SelectCardsInHandAction(defaultSecondMagicNumber, " Sanctify", true, false, (cards) -> true, (List) -> {
                boolean blessed = false;
                if (List.get(0) instanceof AbstractHolyCard) {
                    addToBot(new CycleAction(List.get(0), 0));
                    addToBot(new GainCustomBlockAction(this, p, block));
                    blessed = true;
                } else if (List.get(0) instanceof AbstractPrimalCard || List.get(0).type == CardType.STATUS || List.get(0).type == CardType.CURSE){
                    addToBot(new CycleAction(List.get(0),0,cardsToPreview.makeStatEquivalentCopy()));
                }
                if (List.get(1) instanceof AbstractHolyCard && !blessed) {
                    addToBot(new CycleAction(List.get(1), 0));
                    addToBot(new GainCustomBlockAction(this, p, block));
                } else if (List.get(1) instanceof AbstractPrimalCard || List.get(1).type == CardType.STATUS || List.get(1).type == CardType.CURSE) {
                    addToBot(new CycleAction(List.get(1), 0, cardsToPreview.makeStatEquivalentCopy()));
                }
            }));
        }
        super.use(p,m);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(3);
            initializeDescription();
        }
    }
}
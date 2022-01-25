package theDragonkin.cards.WindWalker;

import IconsAddon.actions.GainCustomBlockAction;
import IconsAddon.cardmods.AddIconToDescriptionMod;
import IconsAddon.icons.ElectricIcon;
import IconsAddon.util.BlockModifierManager;
import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.CustomTags;
import theDragonkin.DamageModifiers.BlockModifiers.LightningBlock;
import theDragonkin.DragonkinMod;
import theDragonkin.characters.TheWindWalker;

import java.util.ArrayList;
import java.util.List;

import static theDragonkin.DragonkinMod.makeCardPath;

public class StaticBarrier extends AbstractWindWalkerCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * WindWalkerDefend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DragonkinMod.makeID(StaticBarrier.class.getSimpleName());
    public static final String IMG = makeCardPath("WindWalkerDefend.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheWindWalker.Enums.WindWalker_Jade_COLOR;

    private static final int COST = 1;
    private static final int BLOCK = 8;
    private static final int UPGRADE_PLUS_BLOCK = 3;


    // /STAT DECLARATION/
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Martial"),BaseMod.getKeywordDescription("thedragonkin:Martial")));
        return retVal;
    }
    @Override
    public List<String> getCardDescriptors() {

        List<String> tags = new ArrayList<>();
        tags.add(BaseMod.getKeywordTitle("thedragonkin:Martial"));
        tags.addAll(super.getCardDescriptors());
        return tags;
    }

    public StaticBarrier() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        block = baseBlock = 10;
        magicNumber = baseMagicNumber = 1;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 1;
        BlockModifierManager.addModifier(this,new LightningBlock(true));
        CardModifierManager.addModifier(this,new AddIconToDescriptionMod(AddIconToDescriptionMod.BLOCK, ElectricIcon.get()));
        AbstractCard previewcard = new WindWalkerDefend();
        BlockModifierManager.addModifier(previewcard,new LightningBlock(false));
        CardModifierManager.addModifier(previewcard,new AddIconToDescriptionMod(AddIconToDescriptionMod.BLOCK, ElectricIcon.get()));
        cardsToPreview = previewcard;
        tags.add(CardTags.STRIKE);
        tags.add(CustomTags.Defend);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainCustomBlockAction(this,p,block));
        for (int i = 0; i < magicNumber; i++) {
            AbstractCard newcard = new WindWalkerDefend();
            BlockModifierManager.addModifier(newcard,new LightningBlock(false));
            CardModifierManager.addModifier(newcard,new AddIconToDescriptionMod(AddIconToDescriptionMod.BLOCK, ElectricIcon.get()));
            addToBot(new MakeTempCardInHandAction(newcard));
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            initializeDescription();
        }
    }
}
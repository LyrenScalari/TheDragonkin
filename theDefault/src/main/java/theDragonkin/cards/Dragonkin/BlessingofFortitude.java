package theDragonkin.cards.Dragonkin;

import IconsAddon.actions.GainCustomBlockAction;
import IconsAddon.cardmods.AddIconToDescriptionMod;
import IconsAddon.icons.HolyIcon;
import IconsAddon.icons.LightIcon;
import IconsAddon.util.BlockModifierManager;
import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.RetainCardPower;
import theDragonkin.CardMods.RetainCardMod;
import theDragonkin.CustomTags;
import theDragonkin.DamageModifiers.BlockModifiers.DivineBlock;
import theDragonkin.DragonkinMod;
import theDragonkin.actions.GainDivineArmorAction;
import theDragonkin.cards.Dragonkin.interfaces.ReciveDamageEffect;
import theDragonkin.characters.TheDefault;

import java.util.ArrayList;
import java.util.List;

import static theDragonkin.DragonkinMod.makeCardPath;

public class BlessingofFortitude extends AbstractHolyCard implements ReciveDamageEffect {

    public static final String ID = DragonkinMod.makeID(BlessingofFortitude.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;

    private static final int COST = -2;
    private static final int UPGRADED_COST = 1;

    private static final int UPGRADE_PLUS_POTENCY = 0;
    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC = 0;
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Blessing"),BaseMod.getKeywordDescription("thedragonkin:Blessing")));
        return retVal;
    }
    @Override
    public List<String> getCardDescriptors() {

        List<String> tags = new ArrayList<>();
        tags.add(BaseMod.getKeywordTitle("thedragonkin:Blessing"));
        tags.addAll(super.getCardDescriptors());
        return tags;
    }
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[1];
        return false;
    }
    public BlessingofFortitude() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        block = baseBlock = MAGIC;
        BlockModifierManager.addModifier(this,new DivineBlock(true));
        CardModifierManager.addModifier(this,new AddIconToDescriptionMod(AddIconToDescriptionMod.BLOCK, LightIcon.get()));
        tags.add(CustomTags.Blessing);
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            upgraded = true;
            initializeDescription();
        }
    }
    @Override
    public void onReciveDamage(int damage) {
        if (AbstractDungeon.player.hand.contains(this)) {
            addToBot(new SelectCardsInHandAction(1, cardStrings.EXTENDED_DESCRIPTION[0],AbstractCard -> !(AbstractCard instanceof BlessingofFortitude) && !CardModifierManager.hasModifier(AbstractCard,"RetainMod"), AbstractCard -> {
                CardModifierManager.addModifier(AbstractCard.get(0), new RetainCardMod(1));
            }));
            if (this.upgraded){
                addToBot(new GainCustomBlockAction(this,AbstractDungeon.player,block));
            }
        }
    }
}
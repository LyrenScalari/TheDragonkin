package theDragonkin.cards.Dragonkin;


import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.actions.common.GainCustomBlockAction;
import com.evacipated.cardcrawl.mod.stslib.blockmods.BlockModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.stance.DivinityParticleEffect;
import theDragonkin.CardMods.AddIconToDescriptionMod;
import theDragonkin.CustomTags;
import theDragonkin.DamageModifiers.BlockModifiers.DivineBlock;
import theDragonkin.DamageModifiers.Icons.LightIcon;
import theDragonkin.DragonkinMod;
import theDragonkin.cards.Dragonkin.interfaces.ReciveDamageEffect;
import theDragonkin.characters.TheDefault;
import theDragonkin.orbs.ConsecrationSeal;
import theDragonkin.orbs.SanctuarySeal;
import theDragonkin.powers.Dragonkin.PenancePower;
import theDragonkin.powers.Dragonkin.SinnersBurdenPower;

import java.util.ArrayList;
import java.util.List;

import static theDragonkin.DragonkinMod.makeCardPath;

public class Absolution extends AbstractHolyCard {

    public static final String ID = DragonkinMod.makeID(Absolution.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;

    private static final int UPGRADE_PLUS_POTENCY = 0;
    private static final int MAGIC = 6;
    private static final int UPGRADE_MAGIC = 0;
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Blessing"),BaseMod.getKeywordDescription("thedragonkin:Blessing")));
        return retVal;
    }
    public Absolution() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        block = baseBlock = 8;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = 6;
        BlockModifierManager.addModifier(this,new DivineBlock(true));
        CardModifierManager.addModifier(this,new AddIconToDescriptionMod(AddIconToDescriptionMod.BLOCK, LightIcon.get()));
        tags.add(CustomTags.Blessing);
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m,p,new PenancePower(m,p,magicNumber)));
        addToBot(new GainCustomBlockAction(this,p,block));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                DragonkinMod.Seals.add(new ConsecrationSeal(defaultSecondMagicNumber,magicNumber));
                isDone = true;
            }
        });
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(2);
            upgradeDefaultSecondMagicNumber(4);
            initializeDescription();
        }
    }
}
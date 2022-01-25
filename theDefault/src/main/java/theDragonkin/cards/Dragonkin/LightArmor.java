package theDragonkin.cards.Dragonkin;

import IconsAddon.actions.GainCustomBlockAction;
import IconsAddon.cardmods.AddIconToDescriptionMod;
import IconsAddon.icons.HolyIcon;
import IconsAddon.icons.LightIcon;
import IconsAddon.util.BlockModifierManager;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.CustomTags;
import theDragonkin.DamageModifiers.BlockModifiers.DivineBlock;
import theDragonkin.DragonkinMod;
import theDragonkin.actions.GainDivineArmorAction;
import theDragonkin.characters.TheDefault;

import static theDragonkin.DragonkinMod.makeCardPath;

public class LightArmor extends AbstractHolyCard {

    public static final String ID = DragonkinMod.makeID(LightArmor.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int POTENCY = 4;
    private static final int UPGRADE_PLUS_POTENCY = 1;
    private static final int MAGIC = 5;

    public LightArmor() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        block = baseBlock = 5;
        BlockModifierManager.addModifier(this,new DivineBlock(true));
        CardModifierManager.addModifier(this,new AddIconToDescriptionMod(AddIconToDescriptionMod.BLOCK, LightIcon.get()));
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 2;
        RadiantExchange = defaultSecondMagicNumber;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < 2; ++i) {
            addToBot(new GainCustomBlockAction(this,p,block));
        }
        super.use(p,m);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDefaultSecondMagicNumber(-1);
            RadiantExchange = defaultSecondMagicNumber;
            initializeDescription();
        }
    }
}
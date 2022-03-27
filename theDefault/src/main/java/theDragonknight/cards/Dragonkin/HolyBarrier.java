package theDragonknight.cards.Dragonkin;


import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.actions.common.GainCustomBlockAction;
import com.evacipated.cardcrawl.mod.stslib.blockmods.BlockModifierManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theDragonknight.CardMods.AddIconToDescriptionMod;
import theDragonknight.DamageModifiers.BlockModifiers.DivineBlock;
import theDragonknight.DamageModifiers.Icons.LightIcon;
import theDragonknight.DragonkinMod;
import theDragonknight.characters.TheDefault;

import static theDragonknight.DragonkinMod.makeCardPath;

public class HolyBarrier extends AbstractHolyCard {

    public static final String ID = DragonkinMod.makeID(HolyBarrier.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;

    private static final int COST = -1;
    private static final int UPGRADED_COST = 1;

    private static final int POTENCY = 5;
    private static final int UPGRADE_PLUS_POTENCY= 2;
    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC = 1;
    public static int repeats = 0;
    public HolyBarrier() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        block = baseBlock = POTENCY;
        BlockModifierManager.addModifier(this,new DivineBlock(true));
        CardModifierManager.addModifier(this,new AddIconToDescriptionMod(AddIconToDescriptionMod.BLOCK, LightIcon.get()));
        exhaust = true;
        baseMagicNumber = magicNumber = MAGIC;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.player.hasRelic("Chemical X")) {
            repeats += 2;
            AbstractDungeon.player.getRelic("Chemical X").flash();
        }
        repeats += EnergyPanel.totalCount;
        for (int i = 0; i < this.magicNumber + repeats; ++i) {
            addToBot(new GainCustomBlockAction(this,p,block));
        }
        EnergyPanel.useEnergy(EnergyPanel.totalCount);
        repeats = 0;
        super.use(p,m);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDefaultSecondMagicNumber(UPGRADE_PLUS_POTENCY);
            initializeDescription();
        }
    }
}
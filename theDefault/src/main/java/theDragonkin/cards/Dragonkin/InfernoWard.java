package theDragonkin.cards.Dragonkin;

import IconsAddon.cardmods.AddIconToDescriptionMod;
import IconsAddon.icons.FireIcon;
import IconsAddon.util.BlockModifierManager;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.DamageModifiers.BlockModifiers.FireBlock;
import theDragonkin.DragonkinMod;
import theDragonkin.actions.InfernoWardAction;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.Dragonkin.AuraFlame;

import static theDragonkin.DragonkinMod.makeCardPath;

public class InfernoWard extends AbstractPrimalCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * WindWalkerDefend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DragonkinMod.makeID(InfernoWard.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION 	

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;

    private static final int COST = 1;
    private static final int BLOCK = 8;
    private static final int UPGRADE_PLUS_BLOCK = 3;


    // /STAT DECLARATION/


    public InfernoWard() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        baseDamage = baseBlock;
        baseMagicNumber = magicNumber = 5;
        BlockModifierManager.addModifier(this,new FireBlock(true));
        CardModifierManager.addModifier(this,new AddIconToDescriptionMod(AddIconToDescriptionMod.BLOCK, FireIcon.get()));
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p,block));
        addToBot(new ApplyPowerAction(p,p,new AuraFlame(p,p,magicNumber)));
        addToBot(new InfernoWardAction(10));
        super.use(p,m);
    }


    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }
}

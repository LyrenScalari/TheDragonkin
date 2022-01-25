package theDragonkin.cards.WindWalker;

import IconsAddon.cardmods.AddIconToDescriptionMod;
import IconsAddon.icons.ElectricIcon;
import IconsAddon.icons.WindIcon;
import IconsAddon.util.DamageModifierHelper;
import IconsAddon.util.DamageModifierManager;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Frost;
import theDragonkin.DamageModifiers.LightningDamage;
import theDragonkin.DragonkinMod;
import theDragonkin.actions.ChiBurstAction;
import theDragonkin.characters.TheWindWalker;

import javax.swing.*;

import static theDragonkin.DragonkinMod.makeCardPath;

public class ConcussiveShout extends AbstractWindWalkerCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * WindWalkerDefend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DragonkinMod.makeID(ConcussiveShout.class.getSimpleName());
    public static final String IMG = makeCardPath("WindWalkerDefend.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheWindWalker.Enums.WindWalker_Jade_COLOR;

    private static final int COST = 2;
    private static final int BLOCK = 8;
    private static final int UPGRADE_PLUS_BLOCK = 3;


    // /STAT DECLARATION/


    public ConcussiveShout() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = 15;
        magicNumber = baseMagicNumber = 2;
        DamageModifierManager.addModifier(this, new LightningDamage(true));
        CardModifierManager.addModifier(this,new AddIconToDescriptionMod(AddIconToDescriptionMod.DAMAGE, ElectricIcon.get()));
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters){
            addToBot(new DamageAction(mo,
                    DamageModifierHelper.makeBoundDamageInfo(this, p, damage, damageTypeForTurn),
                    AbstractGameAction.AttackEffect.SLASH_HEAVY));
        }
        addToBot(new ChiBurstAction(magicNumber,()->new ChannelAction(new Frost())));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            upgradeDamage(2);
            initializeDescription();
        }
    }
}
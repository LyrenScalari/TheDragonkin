package theDragonkin.cards.WindWalker;

import IconsAddon.cardmods.AddIconToDescriptionMod;
import IconsAddon.icons.ElectricIcon;
import IconsAddon.icons.WaterIcon;
import IconsAddon.util.DamageModifierHelper;
import IconsAddon.util.DamageModifierManager;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.DamageModifiers.LightningDamage;
import theDragonkin.DragonkinMod;
import theDragonkin.characters.TheWindWalker;

import static theDragonkin.DragonkinMod.makeCardPath;

public class Stormstrike extends AbstractWindWalkerCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * WindWalkerDefend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DragonkinMod.makeID(Stormstrike.class.getSimpleName());
    public static final String IMG = makeCardPath("WindWalkerDefend.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheWindWalker.Enums.WindWalker_Jade_COLOR;

    private static final int COST = 1;
    private static final int BLOCK = 8;
    private static final int UPGRADE_PLUS_BLOCK = 3;


    // /STAT DECLARATION/


    public Stormstrike() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = 8;
        DamageModifierManager.addModifier(this, new LightningDamage(true));
        CardModifierManager.addModifier(this,new AddIconToDescriptionMod(AddIconToDescriptionMod.DAMAGE, ElectricIcon.get()));
        tags.add(CardTags.STRIKE);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
            addToBot(new DamageAction(m,
                    DamageModifierHelper.makeBoundDamageInfo(this, p, damage, damageTypeForTurn),
                    AbstractGameAction.AttackEffect.SLASH_HEAVY));

    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
            initializeDescription();
        }
    }
}
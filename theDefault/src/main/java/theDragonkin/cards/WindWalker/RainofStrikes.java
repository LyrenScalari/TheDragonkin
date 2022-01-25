package theDragonkin.cards.WindWalker;

import IconsAddon.cardmods.AddIconToDescriptionMod;
import IconsAddon.icons.SpellIcon;
import IconsAddon.icons.WaterIcon;
import IconsAddon.util.DamageModifierHelper;
import IconsAddon.util.DamageModifierManager;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.DamageModifiers.ArcaneDamage;
import theDragonkin.DragonkinMod;
import theDragonkin.characters.TheWindWalker;
import theDragonkin.orbs.Rain;

import javax.swing.*;

import static theDragonkin.DragonkinMod.makeCardPath;

public class RainofStrikes extends AbstractWindWalkerCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * WindWalkerDefend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DragonkinMod.makeID(RainofStrikes.class.getSimpleName());
    public static final String IMG = makeCardPath("WindWalkerDefend.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheWindWalker.Enums.WindWalker_Jade_COLOR;

    private static final int COST = 1;
    private static final int BLOCK = 8;
    private static final int UPGRADE_PLUS_BLOCK = 3;


    // /STAT DECLARATION/


    public RainofStrikes() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = 8;
        magicNumber = baseMagicNumber = 2;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 1;
        DamageModifierManager.addModifier(this, new ArcaneDamage(true,true));
        CardModifierManager.addModifier(this,new AddIconToDescriptionMod(AddIconToDescriptionMod.DAMAGE, SpellIcon.get()));
        CardModifierManager.addModifier(this,new AddIconToDescriptionMod(AddIconToDescriptionMod.MAGIC, SpellIcon.get()));
        tags.add(CardTags.STRIKE);
        AbstractCard previewcard = new WindwalkerStrike();
        DamageModifierManager.addModifier(previewcard,new ArcaneDamage(false,true));
        CardModifierManager.addModifier(previewcard,new AddIconToDescriptionMod(AddIconToDescriptionMod.DAMAGE, SpellIcon.get()));
        cardsToPreview = previewcard;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m,
                    DamageModifierHelper.makeBoundDamageInfo(this, p, damage, damageTypeForTurn),
                    AbstractGameAction.AttackEffect.SLASH_HEAVY));

        for (int i = 0; i < magicNumber; i++) {
            AbstractCard newcard = new WindwalkerStrike();
            DamageModifierManager.addModifier(newcard,new ArcaneDamage(false,true));
            CardModifierManager.addModifier(newcard,new AddIconToDescriptionMod(AddIconToDescriptionMod.DAMAGE, SpellIcon.get()));
            addToBot(new MakeTempCardInHandAction(newcard));
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(2);
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}
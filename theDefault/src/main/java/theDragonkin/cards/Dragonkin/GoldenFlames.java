package theDragonkin.cards.Dragonkin;

import IconsAddon.actions.GainCustomBlockAction;
import IconsAddon.cardmods.AddIconToDescriptionMod;
import IconsAddon.icons.FireIcon;
import IconsAddon.icons.HolyIcon;
import IconsAddon.icons.LightIcon;
import IconsAddon.util.BlockModifierManager;
import IconsAddon.util.DamageModifierManager;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.CustomTags;
import theDragonkin.DamageModifiers.BlockModifiers.DivineBlock;
import theDragonkin.DamageModifiers.DivineDamage;
import theDragonkin.DamageModifiers.FireDamage;
import theDragonkin.DragonkinMod;
import theDragonkin.actions.GainDivineArmorAction;
import theDragonkin.characters.TheDefault;

import javax.swing.*;

import static theDragonkin.DragonkinMod.makeCardPath;

public class GoldenFlames extends AbstractHolyCard {

    public static final String ID = DragonkinMod.makeID(GoldenFlames.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int POTENCY = 4;
    private static final int UPGRADE_PLUS_POTENCY = 3;
    private static final int MAGIC = 6;
    private static final int UPGRADE_MAGIC = 3;

    public GoldenFlames() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = POTENCY;
        block = baseBlock = 6;
        DamageModifierManager.addModifier(this, new FireDamage(true,true));
        CardModifierManager.addModifier(this,new AddIconToDescriptionMod(AddIconToDescriptionMod.DAMAGE, FireIcon.get()));
        BlockModifierManager.addModifier(this,new DivineBlock(true));
        CardModifierManager.addModifier(this,new AddIconToDescriptionMod(AddIconToDescriptionMod.BLOCK, LightIcon.get()));
        RadiantExchange = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new GainCustomBlockAction(this,p,block));
        super.use(p,m);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_POTENCY);
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }
}
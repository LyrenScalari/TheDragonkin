package theDragonkin.cards.Dragonkin;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theDragonkin.CardMods.AddIconToDescriptionMod;
import theDragonkin.CustomTags;
import theDragonkin.DamageModifiers.FireDamage;
import theDragonkin.DamageModifiers.Icons.FireIcon;
import theDragonkin.DragonkinMod;
import theDragonkin.actions.SmiteAction;
import theDragonkin.characters.TheDefault;
import theDragonkin.orbs.WrathSeal;

import static theDragonkin.DragonkinMod.makeCardPath;

public class PartingShot extends AbstractHolyCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * WindWalkerDefend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DragonkinMod.makeID(PartingShot.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;

    private static final int COST = 1;
    private static final int BLOCK = 18;
    private static final int UPGRADE_PLUS_BLOCK = 4;
    private static final int MAGIC = 1;


    // /STAT DECLARATION/


    public PartingShot() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = 8;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 4;
        magicNumber = baseMagicNumber = MAGIC;
        DamageModifierManager.addModifier(this, new FireDamage(true,true));
        CardModifierManager.addModifier(this,new AddIconToDescriptionMod(AddIconToDescriptionMod.DAMAGE, FireIcon.get()));
        tags.add(CustomTags.Smite);
    }

    // Actions the card should do.

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SmiteAction(m, new DamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.NORMAL)));
        addToBot(new ApplyPowerAction(p,p,new WeakPower(p,magicNumber,false)));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                DragonkinMod.Seals.add(new WrathSeal(damage,defaultSecondMagicNumber));
                DragonkinMod.Seals.add(new WrathSeal(damage,defaultSecondMagicNumber));
                DragonkinMod.Seals.add(new WrathSeal(damage,defaultSecondMagicNumber));
                isDone = true;
            }
        });
        super.use(p,m);
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(2);
            upgradeBlock(2);
            initializeDescription();
        }
    }
}
package theDragonkin.cards.Dragonkin;


import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.blockmods.BlockModifierManager;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.DamageModifiers.BlockModifiers.DivineBlock;
import theDragonkin.DamageModifiers.DivineDamage;
import theDragonkin.DragonkinMod;
import theDragonkin.actions.SmiteAction;
import theDragonkin.characters.TheDefault;
import theDragonkin.patches.DivineArmorPatches.DivineArmorField;

import static theDragonkin.DragonkinMod.makeCardPath;

public class HolyWrath extends AbstractHolyCard {

    public static final String ID = DragonkinMod.makeID(HolyWrath.class.getSimpleName());
    public static final String IMG = makeCardPath("HolyWrath.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private CardGroup ShadowVisons = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;

    private static final int UPGRADE_PLUS_POTENCY = 0;
    private static final int MAGIC = 2;
    private static final int UPGRADE_MAGIC = 0;

    public HolyWrath() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        damage = baseDamage = 4;
        exhaust = true;
        DamageModifierManager.addModifier(this, new DivineDamage(true,true));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (BlockModifierManager.hasCustomBlockType(p) && BlockModifierManager.getTopBlockInstance(p).getBlockTypes().get(0) instanceof DivineBlock){
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters){
                addToBot(new SmiteAction(mo, new DamageInfo(p, BlockModifierManager.getTopBlockInstance(p).getBlockAmount()*2, damageTypeForTurn)));
            }
            this.addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    if (BlockModifierManager.hasCustomBlockType(p) && BlockModifierManager.getTopBlockInstance(p).getBlockTypes().stream().anyMatch(mod -> mod instanceof DivineBlock)) {
                        if (!upgraded){
                            BlockModifierManager.reduceSpecificBlockType(BlockModifierManager.getTopBlockInstance(p), BlockModifierManager.getTopBlockInstance(p).getBlockAmount());
                        } else BlockModifierManager.reduceSpecificBlockType(BlockModifierManager.getTopBlockInstance(p), BlockModifierManager.getTopBlockInstance(p).getBlockAmount()/2);
                    }
                    this.isDone = true;
                }
            });
        }
        super.use(p,m);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}

package theDragonkin.cards.Dragonkin;

import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.actions.common.GainCustomBlockAction;
import com.evacipated.cardcrawl.mod.stslib.blockmods.BlockModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.bytecode.stackmap.BasicBlock;
import theDragonkin.CardMods.AddIconToDescriptionMod;
import theDragonkin.CardMods.StormEffect;
import theDragonkin.CustomTags;
import theDragonkin.DamageModifiers.BlockModifiers.DivineBlock;
import theDragonkin.DamageModifiers.BlockModifiers.FireBlock;
import theDragonkin.DamageModifiers.Icons.FireIcon;
import theDragonkin.DragonkinMod;
import theDragonkin.cards.Dragonkin.interfaces.StormCard;
import theDragonkin.characters.TheDefault;
import theDragonkin.orbs.BlazeRune;
import theDragonkin.orbs.WardGlyph;

import java.util.ArrayList;
import java.util.List;

import static theDragonkin.DragonkinMod.makeCardPath;

public class FlameWard extends AbstractPrimalCard {

public static final String ID = DragonkinMod.makeID(FlameWard.class.getSimpleName());
public static final String IMG = makeCardPath("Skill.png");


private static final CardRarity RARITY = CardRarity.COMMON;
private static final CardTarget TARGET = CardTarget.SELF;
private static final CardType TYPE = CardType.SKILL;
public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;

private static final int COST = 1;
private static final int UPGRADED_COST = 1;

private static final int POTENCY= 10;
private static final int UPGRADE_PLUS_DMG = 4;
private static final int MAGIC = 2;
private static final int UPGRADE_MAGIC = 1;
        public FlameWard() {
                super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
                block = baseBlock = POTENCY;
                baseMagicNumber = magicNumber = MAGIC;
                defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 2;
                cardsToPreview = new Burn();
        }

        @Override
        public void use(AbstractPlayer p, AbstractMonster m) {
                AbstractDungeon.actionManager.addToBottom(new GainCustomBlockAction(this,AbstractDungeon.player, block));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Burn(), 1, true, false));
                super.use(p, m);
        }
        @Override
        public void upgrade() {
                if (!upgraded) {
                upgradeName();
                upgradeBlock(UPGRADE_PLUS_DMG);
                initializeDescription();
                }
        }
}
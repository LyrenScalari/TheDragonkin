package theDragonknight.cards.Dragonkin;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.blockmods.BlockModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonknight.CardMods.AddIconToDescriptionMod;
import theDragonknight.CardMods.StormEffect;
import theDragonknight.CustomTags;
import theDragonknight.DamageModifiers.BlockModifiers.FireBlock;
import theDragonknight.DamageModifiers.Icons.FireIcon;
import theDragonknight.DragonkinMod;
import theDragonknight.cards.Dragonkin.interfaces.StormCard;
import theDragonknight.characters.TheDefault;
import theDragonknight.orbs.WardGlyph;

import static theDragonknight.DragonkinMod.makeCardPath;

public class FlameWard extends AbstractPrimalCard implements StormCard {

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
        BlockModifierManager.addModifier(this,new FireBlock(true));
        CardModifierManager.addModifier(this,new AddIconToDescriptionMod(AddIconToDescriptionMod.BLOCK, FireIcon.get()));
        cardsToPreview = new Burn();
        tags.add(CustomTags.Rune);
        CardModifierManager.addModifier(this, new StormEffect(StormRate));
        }

@Override
public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p,block));
        addToBot(new MakeTempCardInDrawPileAction(new Burn(), 1, true, false));
        super.use(p, m);
}
        public void triggerOnManualDiscard() {
                addToBot(new AbstractGameAction() {
                        @Override
                        public void update() {
                                DragonkinMod.Seals.add(new WardGlyph(block, defaultSecondMagicNumber,FlameWard.this));
                                isDone = true;
                        }
                });
        }
@Override
public void upgrade() {
        if (!upgraded) {
        upgradeName();
        upgradeBlock(UPGRADE_PLUS_DMG);
        initializeDescription();
        }
}

        @Override
        public void onStorm() {
        triggerOnManualDiscard();
        }
}
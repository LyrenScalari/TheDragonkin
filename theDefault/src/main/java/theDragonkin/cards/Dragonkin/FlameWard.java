package theDragonkin.cards.Dragonkin;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.CardMods.StormEffect;
import theDragonkin.DragonkinMod;
import theDragonkin.cards.Dragonkin.interfaces.StormCard;
import theDragonkin.characters.TheDefault;

import static theDragonkin.DragonkinMod.makeCardPath;

public class FlameWard extends AbstractPrimalCard implements StormCard {

public static final String ID = DragonkinMod.makeID(FlameWard.class.getSimpleName());
public static final String IMG = makeCardPath("Skill.png");


private static final CardRarity RARITY = CardRarity.COMMON;
private static final CardTarget TARGET = CardTarget.SELF;
private static final CardType TYPE = CardType.SKILL;
public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;

private static final int COST = 1;
private static final int UPGRADED_COST = 1;

private static final int POTENCY= 14;
private static final int UPGRADE_PLUS_DMG = 4;
private static final int MAGIC = 2;
private static final int UPGRADE_MAGIC = 1;

public FlameWard() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = POTENCY;
        block = baseBlock = POTENCY;
        heal = baseHeal = POTENCY;
        baseMagicNumber = magicNumber = MAGIC;
        StormRate = 3;
        CardModifierManager.addModifier(this, new StormEffect(StormRate));
        cardsToPreview = new Burn();
        }

@Override
public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p,block));
        addToBot(new MakeTempCardInDrawPileAction(new Burn(), 1, true, false));
        addToBot(new MakeTempCardInDrawPileAction(new Burn(), 1, true, false));
        if (!Storm) {
                super.use(p, m);
        }
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

        }
}
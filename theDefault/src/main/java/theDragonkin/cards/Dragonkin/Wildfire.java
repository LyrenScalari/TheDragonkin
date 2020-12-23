package theDragonkin.cards.Dragonkin;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.Dragonkin.NextTurnFetchpower;
import theDragonkin.powers.Dragonkin.Scorchpower;

import static theDragonkin.DefaultMod.makeCardPath;

public class Wildfire extends AbstractDragonkinCard {

    public static final String ID = DefaultMod.makeID(Wildfire.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    private static final int COST = -1;
    private static final int UPGRADED_COST = 1;
    public static int repeats = 0;
    private static final int POTENCY = 7;
    private static final int UPGRADE_PLUS_POTENCY = 0;
    private static final int MAGIC = 0;
    private static final int UPGRADE_MAGIC = 0;

    public Wildfire() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = POTENCY;
        block = baseBlock = POTENCY;
        heal = baseHeal = POTENCY;
        baseMagicNumber = magicNumber = MAGIC;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 3;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.player.hasRelic("Chemical X")) {
            repeats += 2;
            AbstractDungeon.player.getRelic("Chemical X").flash();
        }
        repeats += EnergyPanel.totalCount;
        for (int i = 0; i < this.magicNumber + repeats; ++i) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p,block));
            m = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng);
            addToBot(new ApplyPowerAction(m,p,new Scorchpower(m,p,defaultSecondMagicNumber)));
        }
        EnergyPanel.useEnergy(EnergyPanel.totalCount);
        repeats = 0;
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();

            initializeDescription();
        }
    }
}
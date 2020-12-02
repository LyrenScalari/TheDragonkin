package theDragonkin.cards.Gremory.Skills.Magic;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.actions.NagaCure;
import theDragonkin.cards.Gremory.AbstractMagicGremoryCard;
import theDragonkin.characters.TheGremory;
import theDragonkin.powers.ResistancePower;

import static theDragonkin.DefaultMod.makeCardPath;

public class Loptous extends AbstractMagicGremoryCard implements BranchingUpgradesCard {
    public static final String ID = DefaultMod.makeID(Loptous.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheGremory.Enums.Gremory_Purple_Color;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 2;
    private static float animx;
    private static float animy;

    public Loptous() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(CustomTags.Dark);
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[5] + cardStrings.DESCRIPTION;
        initializeDescription();
        MagDamage = baseMagDamage = 10;
        magicNumber = baseMagicNumber = 6;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.timesUpgraded % 2 == 0) {
            for (AbstractMonster mo: AbstractDungeon.getCurrRoom().monsters.monsters){
                addToBot(new ApplyPowerAction(mo,p,new ResistancePower(mo,magicNumber,99)));
            }
            for (AbstractCard card: AllCards.group){
                if (card.hasTag(CustomTags.Light)){
                    card.misc = 0;
                    ((AbstractMagicGremoryCard) card).onDepleted();
                }
            }
        } else {
            addToBot(new NagaCure(p));
            for (AbstractMonster mo: AbstractDungeon.getCurrRoom().monsters.monsters){
                addToBot(new NagaCure(mo));
            }
        }
        super.use(p, m);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            if (isBranchUpgrade()) {
                branchUpgrade();
            } else {
                baseUpgrade();
            }
        }
    }

    public void baseUpgrade() {
        name = cardStrings.EXTENDED_DESCRIPTION[0];
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[5] + UPGRADE_DESCRIPTION;
        upgradeMagicNumber(1);
        initializeDescription();
    }

    public void branchUpgrade() {
        name = cardStrings.EXTENDED_DESCRIPTION[2];
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[4] + cardStrings.EXTENDED_DESCRIPTION[3];
        tags.remove(CustomTags.Dark);
        tags.add(CustomTags.Light);
        MagDamageUpgraded = true;
        initializeDescription();

    }
}

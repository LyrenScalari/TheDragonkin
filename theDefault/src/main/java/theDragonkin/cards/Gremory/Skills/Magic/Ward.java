package theDragonkin.cards.Gremory.Skills.Magic;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.cards.Gremory.AbstractMagicGremoryCard;
import theDragonkin.characters.TheGremory;
import theDragonkin.powers.WardPower;

import static theDragonkin.DefaultMod.makeCardPath;

public class Ward extends AbstractMagicGremoryCard implements BranchingUpgradesCard {
    public static final String ID = DefaultMod.makeID(Ward.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheGremory.Enums.Gremory_Purple_Color;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static float animx;
    private static float animy;

    public Ward() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(CustomTags.Light);
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[4] +  cardStrings.DESCRIPTION;
        initializeDescription();
        MagDamage = baseMagDamage = 5;
        magicNumber = baseMagicNumber = 8;
        ExhaustiveVariable.setBaseValue(this,2);
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.timesUpgraded % 2 == 0){
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, MagDamage));
            addToBot(new ApplyPowerAction(p,p,new WardPower(p,p,magicNumber)));
        }
        else {
            for (AbstractPower po :m.powers){
                if (po.ID.equals(PoisonPower.POWER_ID)){
                    addToBot(new GainBlockAction(p,po.amount/2));
                    addToBot(new RemoveSpecificPowerAction(m,p,po));
                }
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
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[4] + UPGRADE_DESCRIPTION;
        baseMagDamage += 2;
        initializeDescription();
    }

    public void branchUpgrade() {
        name = cardStrings.EXTENDED_DESCRIPTION[2];
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[5] + cardStrings.EXTENDED_DESCRIPTION[3];
        tags.remove(CustomTags.Light);
        tags.add(CustomTags.Dark);
        this.target = CardTarget.ENEMY;
        MagDamageUpgraded = true;
        initializeDescription();

    }
}

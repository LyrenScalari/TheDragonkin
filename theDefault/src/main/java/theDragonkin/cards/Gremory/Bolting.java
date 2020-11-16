package theDragonkin.cards.Gremory;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheGremory;
import theDragonkin.powers.ChargedUp;
import theDragonkin.powers.JoltedPower;

import static theDragonkin.DefaultMod.makeCardPath;

public class Bolting extends AbstractMagicGremoryCard implements BranchingUpgradesCard {
    public static final String ID = DefaultMod.makeID(Bolting.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheGremory.Enums.Gremory_Purple_Color;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int MAGIC = 2;

    public Bolting() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(CustomTags.Thunder);
        magicNumber = baseMagicNumber = MAGIC;
        MagDamage = baseMagDamage = 16;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.hasTag(CustomTags.Thunder)){
            if (m.hasPower(JoltedPower.POWER_ID)){
                addToBot(new ApplyPowerAction(p,p,new ChargedUp(p,magicNumber,true)));
            }
            addToBot(new DamageAction(m,new DamageInfo(p,MagDamage, DamageInfo.DamageType.NORMAL)));
        }
        else {
            addToBot(new DamageAction(m, new DamageInfo(p,MagDamage, DamageInfo.DamageType.NORMAL)));
            if (this.upgraded && Tailwind){
                GaleforceBonus += 4;
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
        this.rawDescription = UPGRADE_DESCRIPTION;
        upgradeMagicNumber(2);
        initializeDescription();
    }

    public void branchUpgrade() {
        name = cardStrings.EXTENDED_DESCRIPTION[2];
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[3];
        tags.remove(CustomTags.Thunder);
        tags.add(CustomTags.Wind);
        baseMagDamage -= 6;
        MagDamageUpgraded = true;
        initializeDescription();
    }
}


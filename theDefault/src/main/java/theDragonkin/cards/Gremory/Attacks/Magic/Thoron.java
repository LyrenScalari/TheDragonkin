package theDragonkin.cards.Gremory.Attacks.Magic;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.cards.Gremory.AbstractMagicGremoryCard;
import theDragonkin.util.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheGremory;
import theDragonkin.powers.ArcanaPower;
import theDragonkin.powers.JoltedPower;

import static theDragonkin.DefaultMod.makeCardPath;

public class Thoron extends AbstractMagicGremoryCard implements BranchingUpgradesCard {
    public static final String ID = DefaultMod.makeID(Thoron.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheGremory.Enums.Gremory_Purple_Color;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 2;
    private static final int MAGIC = 1;

    public Thoron() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(CustomTags.Thunder);
        magicNumber = baseMagicNumber = MAGIC;
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[5] +  cardStrings.DESCRIPTION;
        initializeDescription();
        MagDamage = baseMagDamage = 10;
        isMultiDamage = true;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.timesUpgraded % 2 == 0){
            for (final AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!mo.hasPower(JoltedPower.POWER_ID)){
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new ArcanaPower(p,magicNumber),magicNumber));
                }
            }
            addToBot(new DamageAllEnemiesAction(p,MagDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.LIGHTNING));
        }
        else {
            addToBot(new DamageAction(m, new DamageInfo(p,MagDamage, DamageInfo.DamageType.NORMAL)));
            if (this.upgraded && Tailwind){
                addToBot(new GainBlockAction(p,MagDamage));
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
        initializeDescription();
    }

    public void branchUpgrade() {
        name = cardStrings.EXTENDED_DESCRIPTION[2];
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[6] + cardStrings.EXTENDED_DESCRIPTION[3];
        tags.remove(CustomTags.Thunder);
        tags.add(CustomTags.Wind);
        baseMagDamage -= 6;
        MagDamageUpgraded = true;
        initializeDescription();
    }
}

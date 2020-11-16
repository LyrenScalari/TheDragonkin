package theDragonkin.cards.Gremory;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheGremory;

import static theDragonkin.DefaultMod.makeCardPath;

public class Fire extends AbstractMagicGremoryCard implements BranchingUpgradesCard {
    public static final String ID = DefaultMod.makeID(Fire.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheGremory.Enums.Gremory_Purple_Color;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;

    public Fire() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(CustomTags.Fire);
        MagDamage = baseMagDamage = 8;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.hasTag(CustomTags.Fire)){
            addToBot(new DamageAction(m, new DamageInfo(p,MagDamage, DamageInfo.DamageType.NORMAL)));
            if (this.upgraded && HotStreak){
                addToBot(new MakeTempCardInHandAction(new FollowUpFire()));
            }
        }
        else {
            addToBot(new DamageAction(m, new DamageInfo(p,MagDamage, DamageInfo.DamageType.NORMAL)));
            addToBot(new DamageAction(m, new DamageInfo(p,MagDamage, DamageInfo.DamageType.NORMAL)));
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
        initializeDescription();
    }

    public void branchUpgrade() {
        name = cardStrings.EXTENDED_DESCRIPTION[2];
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[3];
        tags.remove(CustomTags.Fire);
        tags.add(CustomTags.Ice);
        baseMagDamage -= 5;
        MagDamageUpgraded = true;
        initializeDescription();

    }
}

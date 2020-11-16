package theDragonkin.cards.Gremory;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheGremory;
import theDragonkin.powers.FreezePower;

import static theDragonkin.DefaultMod.makeCardPath;

public class Fimbulvetr extends AbstractMagicGremoryCard implements BranchingUpgradesCard {
    public static final String ID = DefaultMod.makeID(Fimbulvetr.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheGremory.Enums.Gremory_Purple_Color;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 2;

    public Fimbulvetr() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(CustomTags.Ice);
        MagDamage = baseMagDamage = 6;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.hasTag(CustomTags.Ice)){
            addToBot(new DamageAction(m, new DamageInfo(p,MagDamage, DamageInfo.DamageType.NORMAL)));
            addToBot(new DamageAction(m, new DamageInfo(p,MagDamage, DamageInfo.DamageType.NORMAL)));
            addToBot(new DamageAction(m, new DamageInfo(p,MagDamage, DamageInfo.DamageType.NORMAL)));
            addToBot(new DamageAction(m, new DamageInfo(p,MagDamage, DamageInfo.DamageType.NORMAL)));
            addToBot(new DamageAction(m, new DamageInfo(p,MagDamage, DamageInfo.DamageType.NORMAL)));
            if (m.hasPower(FreezePower.POWER_ID)) {
                if (this.upgraded){
                    addToBot(new LoseHPAction(m,p,MagDamage*2));
                    addToBot(new MakeTempCardInHandAction(new FollowUpFimbulvetr()));
                }
                else {addToBot(new LoseHPAction(m,p,MagDamage));}
            }
        }
        else {
            addToBot(new DamageAction(m, new DamageInfo(p,MagDamage, DamageInfo.DamageType.NORMAL)));
            if (this.upgraded && HotStreak){
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
        this.rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    public void branchUpgrade() {
        name = cardStrings.EXTENDED_DESCRIPTION[2];
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[3];
        tags.remove(CustomTags.Ice);
        tags.add(CustomTags.Fire);
        baseMagDamage += 3;
        MagDamageUpgraded = true;
        initializeDescription();

    }
}

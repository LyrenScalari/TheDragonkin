package theDragonkin.cards.Gremory;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.util.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.actions.WitherAction;
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
    private static AbstractCard FollowUp;

    public Fire() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(CustomTags.Fire);
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[5] +  cardStrings.DESCRIPTION;
        initializeDescription();
        MagDamage = baseMagDamage = 4;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new WitherAction(this));
        if (this.timesUpgraded % 2 == 0){
            addToBot(new DamageAction(m, new DamageInfo(p,MagDamage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
            if (AbstractDungeon.actionManager.cardsPlayedThisCombat.size() >= 2
                    && ((AbstractCard)AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 2)).hasTag(CustomTags.Fire)) {
                    FollowUp = new FollowUpFire();
                    if (!upgraded) {
                        addToBot(new MakeTempCardInHandAction(FollowUp));
                    } else addToBot(new MakeTempCardInHandAction(FollowUp)); FollowUp.upgrade();
                    AllCards.addToBottom(FollowUp);
            }
        }
        else {
            addToBot(new DamageAction(m, new DamageInfo(p,MagDamage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
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
        baseMagDamage += 3;
        MagDamageUpgraded = true;
        initializeDescription();
    }

    public void branchUpgrade() {
        name = cardStrings.EXTENDED_DESCRIPTION[2];
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[6] + cardStrings.EXTENDED_DESCRIPTION[3];
        tags.remove(CustomTags.Fire);
        tags.add(CustomTags.Ice);
        baseMagDamage += 2;
        MagDamageUpgraded = true;
        initializeDescription();

    }
}

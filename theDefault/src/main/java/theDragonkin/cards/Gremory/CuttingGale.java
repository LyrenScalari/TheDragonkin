package theDragonkin.cards.Gremory;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheGremory;
import theDragonkin.powers.JoltedPower;

import static theDragonkin.DefaultMod.makeCardPath;

public class CuttingGale extends AbstractMagicGremoryCard implements BranchingUpgradesCard {
    public static final String ID = DefaultMod.makeID(CuttingGale.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheGremory.Enums.Gremory_Purple_Color;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;

    public CuttingGale() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(CustomTags.Wind);
        MagDamage = baseMagDamage = 8;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.hasTag(CustomTags.Wind)) {
            addToBot(new DamageAction(m, new DamageInfo(p, MagDamage, DamageInfo.DamageType.NORMAL)));
            addToBot(new MakeTempCardInHandAction(new FollowUpGale()));
            if (upgraded && Tailwind) {
                addToBot(new MakeTempCardInHandAction(new FollowUpGale()));
            }
        } else {
                for (final AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (mo.hasPower(JoltedPower.POWER_ID)) {
                        addToBot(new DamageAllEnemiesAction(p, MagDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.LIGHTNING));
                        return;
                    }
                }
                addToBot(new DamageAllEnemiesAction(p, MagDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.LIGHTNING));
                AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(2));
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
        tags.remove(CustomTags.Wind);
        tags.add(CustomTags.Thunder);
        baseMagDamage += 6;
        MagDamageUpgraded = true;
        initializeDescription();
    }
}

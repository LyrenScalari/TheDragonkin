package theDragonkin.cards.Gremory;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import theDragonkin.util.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheGremory;

import static theDragonkin.DefaultMod.makeCardPath;
@AutoAdd.Ignore
public class VeninFollowUp extends AbstractGremoryCard {
    public static final String ID = DefaultMod.makeID(VeninFollowUp.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.SPECIAL;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    public static final AbstractCard.CardColor COLOR = TheGremory.Enums.Gremory_Purple_Color;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(VeninSlash.ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 0;
    private static int UPGRADE_PLUS_DMG = 2;
    private static final int DAMAGE = 8;

    public VeninFollowUp() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        purgeOnUse = true;
        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = 3;
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
        this.name = cardStrings.EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            this.tags.add(CustomTags.Physical);
            upgradeMagicNumber(2);
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p,damage, DamageInfo.DamageType.NORMAL)));
        addToBot(new ApplyPowerAction(m,p,new PoisonPower(m,p,magicNumber)));
    }
}

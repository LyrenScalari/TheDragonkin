package theDragonkin.cards.Gremory;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheGremory;

import static theDragonkin.DefaultMod.makeCardPath;
@AutoAdd.Ignore
public class FollowUpWind extends AbstractMagicGremoryCard {
    public static final String ID = DefaultMod.makeID(FollowUpWind.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.SPECIAL;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    public static final AbstractCard.CardColor COLOR = TheGremory.Enums.Gremory_Purple_Color;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(Wind.ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 0;

    public FollowUpWind() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(CustomTags.Wind);
        purgeOnUse = true;
        MagDamage = baseMagDamage = 6;
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[4];
        this.name = cardStrings.EXTENDED_DESCRIPTION[1];
    }

    @Override
    public void upgrade() {

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p,MagDamage, DamageInfo.DamageType.NORMAL)));
    }
}

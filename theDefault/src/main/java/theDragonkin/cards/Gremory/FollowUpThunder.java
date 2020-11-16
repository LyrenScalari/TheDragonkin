package theDragonkin.cards.Gremory;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.common.DamageAction;
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
public class FollowUpThunder extends AbstractMagicGremoryCard {
    public static final String ID = DefaultMod.makeID(FollowUpThunder.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheGremory.Enums.Gremory_Purple_Color;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(Thunder.ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 0;

    public FollowUpThunder() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(CustomTags.Thunder);
        purgeOnUse = true;
        MagDamage = baseMagDamage = 12;
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

package theDragonkin.cards.Gremory.Attacks;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.CardMods.BeastFangEnchantmentCardmod;
import theDragonkin.cards.Gremory.AbstractGremoryCard;
import theDragonkin.util.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheGremory;

import static theDragonkin.DefaultMod.makeCardPath;
import static theDragonkin.cards.Gremory.AbstractMagicGremoryCard.AllCards;

public class BeastFang extends AbstractGremoryCard {


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(BeastFang.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("Strike.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION
    private static AbstractCard FollowUp;
    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheGremory.Enums.Gremory_Purple_Color;

    private static final int COST = 2;  // COST = 1
    private static final int UPGRADED_COST = 1; // UPGRADED_COST = 1

    private static final int DAMAGE = 14;    // DAMAGE = 6
    private static final int UPGRADE_PLUS_DMG = 4;  // UPGRADE_PLUS_DMG = 4

    // /STAT DECLARATION/


    public BeastFang(){
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
        this.tags.add(CustomTags.Physical);
        this.tags.add(CustomTags.Enchanted);
        magicNumber = baseMagicNumber = 2;
        baseDamage =DAMAGE;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m,new DamageInfo(p,damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        for (AbstractCard c : AllCards.group){
            if (c.hasTag(CustomTags.Physical) && !(c.hasTag(CustomTags.Enchanted))){
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        CardModifierManager.addModifier(c, new BeastFangEnchantmentCardmod(magicNumber));
                    }
                });
            }
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}

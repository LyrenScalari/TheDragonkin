package theDragonkin.cards.GroveKeeper.Skills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theDragonkin.CustomTags;
import theDragonkin.DragonkinMod;
import theDragonkin.cards.GroveKeeper.AbstractGroveKeeperCard;
import theDragonkin.characters.TheGroveKeeper;
import theDragonkin.powers.GroveKeeper.AlignmentPower;

import static theDragonkin.DragonkinMod.makeCardPath;

public class SolarFlare extends AbstractGroveKeeperCard {


    // TEXT DECLARATION

    public static final String ID = DragonkinMod.makeID(SolarFlare.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Skill.png");// "public static final String IMG = makeCardPath("FlameweaverStrike.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheGroveKeeper.Enums.GroveKeeper_Forest_Color;

    private static final int COST = 1;  // COST = 1
    private static final int UPGRADED_COST = 1; // UPGRADED_COST = 1

    private static final int DAMAGE = 5;    // DAMAGE = 6
    private static final int UPGRADE_PLUS_DMG = 3;  // UPGRADE_PLUS_DMG = 4

    // /STAT DECLARATION/


    public SolarFlare(){
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = 2;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 3;
        this.tags.add(CustomTags.Solar);
        this.setOrbTexture(DragonkinMod.Solar_SMALL_ORB, DragonkinMod.Solar_LARGE_ORB);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m,p,new StrengthPower(m,-magicNumber)));
        addToBot(new ApplyPowerAction(m,p,new GainStrengthPower(m,magicNumber)));
        if (p.hasPower(AlignmentPower.POWER_ID)) {
            if (p.getPower(AlignmentPower.POWER_ID).amount >= 3) {
                for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters){
                    addToBot(new ApplyPowerAction(mo,p,new StrengthPower(m,-defaultSecondMagicNumber)));
                    addToBot(new ApplyPowerAction(mo,p,new GainStrengthPower(m,defaultSecondMagicNumber)));
                }
            }
        }
    }
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (AbstractDungeon.player.hasPower(AlignmentPower.POWER_ID) && AbstractDungeon.player.getPower(AlignmentPower.POWER_ID).amount >= 3) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            upgradeDefaultSecondMagicNumber(1);
            initializeDescription();
        }
    }
}

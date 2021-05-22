package theDragonkin.cards.GroveKeeper.Attacks;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import theDragonkin.CustomTags;
import theDragonkin.DragonkinMod;
import theDragonkin.cards.GroveKeeper.AbstractGroveKeeperCard;
import theDragonkin.characters.TheGroveKeeper;
import theDragonkin.orbs.AbstractGrovekeeperOrb;
import theDragonkin.orbs.LifeBloom;
import theDragonkin.orbs.ThornBloom;
import theDragonkin.powers.GroveKeeper.AlignmentPower;

import static theDragonkin.DragonkinMod.makeCardPath;

public class CresentCulling extends AbstractGroveKeeperCard {


    // TEXT DECLARATION

    public static final String ID = DragonkinMod.makeID(CresentCulling.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("FlameweaverStrike.png");// "public static final String IMG = makeCardPath("FlameweaverStrike.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheGroveKeeper.Enums.GroveKeeper_Forest_Color;

    private static final int COST = 1;  // COST = 1
    private static final int UPGRADED_COST = 1; // UPGRADED_COST = 1

    private static final int DAMAGE = 5;    // DAMAGE = 6
    private static final int UPGRADE_PLUS_DMG = 4;  // UPGRADE_PLUS_DMG = 4

    // /STAT DECLARATION/


    public CresentCulling(){
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = 3;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 5;
        this.tags.add(CustomTags.Lunar);
        this.isMultiDamage = true;
        this.setOrbTexture(DragonkinMod.Lunar_SMALL_ORB, DragonkinMod.Lunar_LARGE_ORB);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAllEnemiesAction(p, multiDamage , DamageInfo.DamageType.NORMAL , AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        for (AbstractOrb orb : p.orbs){
            if (orb instanceof ThornBloom || orb instanceof LifeBloom){
                ((AbstractGrovekeeperOrb) orb).onHarvest(magicNumber);
                break;
            }
        }
        if (AbstractDungeon.player.hasPower(AlignmentPower.POWER_ID) && ((TwoAmountPower)AbstractDungeon.player.getPower(AlignmentPower.POWER_ID)).amount2 >= 2) {
            for (AbstractOrb orb : p.orbs){
                if (orb instanceof ThornBloom || orb instanceof LifeBloom){
                    ((AbstractGrovekeeperOrb) orb).onHarvest(magicNumber);
                    break;
                }
            }
        }
    }
    public void triggerOnGlowCheck() {
        if (AbstractDungeon.player.hasPower(AlignmentPower.POWER_ID) && ((TwoAmountPower)AbstractDungeon.player.getPower(AlignmentPower.POWER_ID)).amount2 >= 2) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
            this.setOrbTexture(DragonkinMod.Lunar_SMALL_ORB, DragonkinMod.Lunar_LARGE_ORB);
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
            this.setOrbTexture(DragonkinMod.Neutral_SMALL_ORB, DragonkinMod.Neutral_LARGE_ORB);
        }
    }
    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}

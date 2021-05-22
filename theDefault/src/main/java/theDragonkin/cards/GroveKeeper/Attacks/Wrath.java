package theDragonkin.cards.GroveKeeper.Attacks;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.CollectorCurseEffect;
import theDragonkin.CustomTags;
import theDragonkin.DragonkinMod;
import theDragonkin.cards.GroveKeeper.AbstractGroveKeeperCard;
import theDragonkin.characters.TheGroveKeeper;
import theDragonkin.powers.GroveKeeper.AlignmentPower;
import theDragonkin.powers.GroveKeeper.Moonstruck;

import static theDragonkin.DragonkinMod.makeCardPath;

public class Wrath extends AbstractGroveKeeperCard {


    // TEXT DECLARATION

    public static final String ID = DragonkinMod.makeID(Wrath.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("FlameweaverStrike.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final AbstractCard.CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final AbstractCard.CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;       //
    public static final AbstractCard.CardColor COLOR = TheGroveKeeper.Enums.GroveKeeper_Forest_Color;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 2;  // COST = 1
    private static final int UPGRADED_COST = 0; // UPGRADED_COST = 1

    private static final int DAMAGE = 12;    // DAMAGE = 6
    private static final int UPGRADE_PLUS_DMG = 4;  // UPGRADE_PLUS_DMG = 4

    // /STAT DECLARATION/


    public Wrath(){
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = 2;
        this.tags.add(CustomTags.Neutral);
        this.setOrbTexture(DragonkinMod.Neutral_SMALL_ORB, DragonkinMod.Neutral_LARGE_ORB);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p,damage), AbstractGameAction.AttackEffect.SMASH));
        if (p.hasPower(AlignmentPower.POWER_ID)) {
            if (((TwoAmountPower)p.getPower(AlignmentPower.POWER_ID)).amount2 >= 2) {
                addToBot(new VFXAction(new CollectorCurseEffect(m.drawX,m.drawY)));
                addToBot(new DamageAction(m, new DamageInfo(p,damage), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                addToBot(new ApplyPowerAction(m,p,new Moonstruck(m,p,magicNumber)));
            } else if (p.getPower(AlignmentPower.POWER_ID).amount >= 2){
                addToBot(new ApplyPowerAction(m,p,new WeakPower(m,magicNumber,false)));
            }
        }
    }

    public void triggerOnGlowCheck() {
        if (AbstractDungeon.player.hasPower(AlignmentPower.POWER_ID) && ((TwoAmountPower)AbstractDungeon.player.getPower(AlignmentPower.POWER_ID)).amount2 >= 2) {
            this.name = cardStrings.EXTENDED_DESCRIPTION[1];
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[3];
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
            if (!this.hasTag(CustomTags.Lunar)){
                this.tags.remove(CustomTags.Solar);
                this.tags.remove(CustomTags.Neutral);
                this.tags.add(CustomTags.Lunar);
                this.setOrbTexture(DragonkinMod.Lunar_SMALL_ORB, DragonkinMod.Lunar_LARGE_ORB);
                initializeDescription();
            }
        } else if (AbstractDungeon.player.hasPower(AlignmentPower.POWER_ID) && AbstractDungeon.player.getPower(AlignmentPower.POWER_ID).amount >= 2){
            this.name = cardStrings.EXTENDED_DESCRIPTION[0];
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[2];
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
            if (!this.hasTag(CustomTags.Solar)){
                this.tags.remove(CustomTags.Lunar);
                this.tags.remove(CustomTags.Neutral);
                this.tags.add(CustomTags.Solar);
                this.setOrbTexture(DragonkinMod.Solar_SMALL_ORB, DragonkinMod.Solar_LARGE_ORB);
                initializeDescription();
            }
        } else {
            this.name = cardStrings.NAME;
            this.rawDescription = cardStrings.DESCRIPTION;
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
            if (!this.hasTag(CustomTags.Neutral)){
                this.tags.remove(CustomTags.Lunar);
                this.tags.remove(CustomTags.Solar);
                this.tags.add(CustomTags.Neutral);
                this.setOrbTexture(DragonkinMod.Neutral_SMALL_ORB, DragonkinMod.Neutral_LARGE_ORB);
                initializeDescription();
            }
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

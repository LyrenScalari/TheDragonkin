package theDragonkin.cards.Gremory.Attacks;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FallingIceEffect;
import com.megacrit.cardcrawl.vfx.combat.IceShatterEffect;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.cards.Gremory.AbstractGremoryCard;
import theDragonkin.characters.TheGremory;
import theDragonkin.powers.ChillPower;

import static theDragonkin.DefaultMod.makeCardPath;
@AutoAdd.Ignore
public class Hoarfrost extends AbstractGremoryCard {


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Hoarfrost.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("Strike.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION
    private static AbstractCard FollowUp;
    private static final CardRarity RARITY = CardRarity.SPECIAL; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheGremory.Enums.Gremory_Purple_Color;

    private static final int COST = 2;  // COST = 1
    private static final int UPGRADED_COST = 1; // UPGRADED_COST = 1

    private static final int DAMAGE = 12;    // DAMAGE = 6
    private static final int UPGRADE_PLUS_DMG = 4;  // UPGRADE_PLUS_DMG = 4

    // /STAT DECLARATION/


    public Hoarfrost(){
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
        this.tags.add(CustomTags.Physical);
        setBackgroundTexture(DefaultMod.WINTER_SMALL_ATTACK_FRAME, DefaultMod.WINTER_LARGE_ATTACK_FRAME);
        setOrbTexture(DefaultMod.WINTER_SMALL_ORB,DefaultMod.WINTER_LARGE_ORB);
        baseDamage =DAMAGE;
        magicNumber = baseMagicNumber = 3;
        tags.add(CustomTags.Winter);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new FallingIceEffect(50,false)));
        addToBot(new VFXAction(new FallingIceEffect(50,false)));
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            addToBot(new VFXAction(new IceShatterEffect(mo.drawX,mo.drawY)));
            addToBot(new ApplyPowerAction(mo,p,new ChillPower(mo,p,magicNumber)));
            addToBot(new LoseHPAction(mo,p,mo.getPower(ChillPower.POWER_ID).amount));
            addToBot(new GainBlockAction(p,mo,mo.getPower(ChillPower.POWER_ID).amount));
        }
    }
    @Override
    public void triggerWhenDrawn() {
        addToBot(new VFXAction(new FallingIceEffect(50,false)));
        addToBot(new VFXAction(new FallingIceEffect(50,false)));
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            addToBot(new VFXAction(new IceShatterEffect(mo.drawX,mo.drawY)));
            addToBot(new ApplyPowerAction(mo,AbstractDungeon.player,new ChillPower(mo,AbstractDungeon.player,magicNumber)));
            addToBot(new LoseHPAction(mo,AbstractDungeon.player,mo.getPower(ChillPower.POWER_ID).amount));
            addToBot(new GainBlockAction(AbstractDungeon.player,mo,mo.getPower(ChillPower.POWER_ID).amount));
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

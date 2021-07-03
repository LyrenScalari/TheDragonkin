package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import com.megacrit.cardcrawl.vfx.stance.DivinityParticleEffect;
import theDragonkin.DragonkinMod;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.Dragonkin.DivineRetributionPower;

import static theDragonkin.DragonkinMod.makeCardPath;

public class DivineRetribution extends AbstractHolyCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Hold Place Gain 1(2) Keywords(s).
     */


    // TEXT DECLARATION

    public static final String ID = DragonkinMod.makeID(DivineRetribution.class.getSimpleName());
    public static final String IMG = makeCardPath("Power.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;

    private static final int COST = 2;

    // Hey want a second magic/damage/block/unique number??? Great!
    // Go check out DefaultAttackWithVariable and theDefault.variable.DefaultCustomVariable
    // that's how you get your own custom variable that you can use for anything you like.
    // Feel free to explore other mods to see what variables they personally have and create your own ones.

    // /STAT DECLARATION/


    public DivineRetribution() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        CardCrawlGame.sound.play("ORB_LIGHTNING_EVOKE");
        addToBot(new VFXAction(new LightningEffect(p.drawX,p.drawY)));
        CardCrawlGame.sound.play("POWER_MANTRA", 0.05F);
        addToBot(new VFXAction(new DivinityParticleEffect()));
        addToBot(new VFXAction(new DivinityParticleEffect()));
        addToBot(new VFXAction(new DivinityParticleEffect()));
        addToBot(new VFXAction(new DivinityParticleEffect()));
        addToBot(new VFXAction(new DivinityParticleEffect()));
        addToBot(new VFXAction(new DivinityParticleEffect()));
        addToBot(new VFXAction(new DivinityParticleEffect()));
        addToBot(new VFXAction(new DivinityParticleEffect()));
        addToBot(new VFXAction(new DivinityParticleEffect()));
        addToBot(new VFXAction(new DivinityParticleEffect()));
        addToBot(new VFXAction(new DivinityParticleEffect()));
        addToBot(new VFXAction(new DivinityParticleEffect()));
        addToBot(new VFXAction(new DivinityParticleEffect()));
        addToBot(new VFXAction(new DivinityParticleEffect()));
        addToBot(new VFXAction(new DivinityParticleEffect()));
        addToBot(new VFXAction(new DivinityParticleEffect()));
        addToBot(new ApplyPowerAction(p,p,new DivineRetributionPower(p,p)));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
            initializeDescription();
        }
    }
}
package theDragonkin.cards.Dragonkin;

import com.evacipated.cardcrawl.mod.stslib.actions.common.DamageCallbackAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import org.lwjgl.Sys;
import theDragonkin.DragonkinMod;
import theDragonkin.cards.Dragonkin.interfaces.ReciveDamageEffect;
import theDragonkin.characters.TheDefault;

import static theDragonkin.DragonkinMod.makeCardPath;

public class SacredTrial extends AbstractHolyCard {


    // TEXT DECLARATION

    public static final String ID = DragonkinMod.makeID(SacredTrial.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("HolySmite.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int DAMAGE = 7;
    private static final int UPGRADE_PLUS_DMG = 0;
    // /STAT DECLARATION/


    public SacredTrial() {
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
        baseDamage = damage = DAMAGE;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        CardCrawlGame.sound.play("POWER_MANTRA", 0.05F);
        AbstractDungeon.actionManager.addToBottom(new SFXAction("ORB_LIGHTNING_EVOKE"));
        addToBot(new VFXAction(new LightningEffect(p.drawX,p.drawY)));
        addToBot(new DamageCallbackAction(p, new DamageInfo(p,damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE, integer -> {
            System.out.println(integer);
              for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters){
                  AbstractDungeon.actionManager.addToBottom(new SFXAction("ORB_LIGHTNING_EVOKE"));
                  addToBot(new VFXAction(new LightningEffect(mo.drawX,mo.drawY)));
                  addToBot(new DamageAction(mo,new DamageInfo(p,damage*2, DamageInfo.DamageType.NORMAL)));
              }
        }));
    }

    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        float runningbaseDamage = realBaseDamage;
        for (AbstractPower p : AbstractDungeon.player.powers){
            runningbaseDamage =  p.atDamageGive(runningbaseDamage, DamageInfo.DamageType.NORMAL);
            runningbaseDamage =  p.atDamageReceive(runningbaseDamage, DamageInfo.DamageType.NORMAL);
            runningbaseDamage =  p.atDamageFinalGive(runningbaseDamage, DamageInfo.DamageType.NORMAL);
            runningbaseDamage =  p.atDamageFinalReceive(runningbaseDamage, DamageInfo.DamageType.NORMAL);
        }
        this.baseDamage = (int)runningbaseDamage;
        damage = baseDamage;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;

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
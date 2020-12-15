package theDragonkin.cards.Gremory.Attacks;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.ViceCrushEffect;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.cards.Gremory.AbstractGremoryCard;
import theDragonkin.characters.TheGremory;
import theDragonkin.powers.Gremory.ArcanaPower;

import static theDragonkin.DefaultMod.makeCardPath;
@AutoAdd.Ignore
public class Aymr extends AbstractGremoryCard {


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Aymr.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
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


    public Aymr(){
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
        this.tags.add(CustomTags.Physical);
        setBackgroundTexture(DefaultMod.SPRING_SMALL_ATTACK_FRAME, DefaultMod.SPRING_LARGE_ATTACK_FRAME);
        setOrbTexture(DefaultMod.SPRING_SMALL_ORB,DefaultMod.SPRING_LARGE_ORB);
        baseDamage =DAMAGE;
        magicNumber = baseMagicNumber = 2;
        tags.add(CustomTags.Spring);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            addToBot(new VFXAction(new ViceCrushEffect(mo.drawX,mo.drawY)));
        }
        addToBot(new DamageAllEnemiesAction(p,damage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            addToBot(new ApplyPowerAction(mo,p,new WeakPower(mo,magicNumber,false)));
            addToBot(new ApplyPowerAction(mo,p,new VulnerablePower(mo,magicNumber,false)));
        }
        addToBot(new ApplyPowerAction(p,p,new StrengthPower(p,2)));
        addToBot(new ApplyPowerAction(p,p,new ArcanaPower(p,2)));
    }
    @Override
    public void triggerWhenDrawn() {
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            addToBot(new VFXAction(new ViceCrushEffect(mo.drawX,mo.drawY)));
        }
        addToBot(new DamageAllEnemiesAction(AbstractDungeon.player,damage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
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

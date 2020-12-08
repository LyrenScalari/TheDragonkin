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
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.GoldenSlashEffect;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.cards.Gremory.AbstractGremoryCard;
import theDragonkin.characters.TheGremory;

import static theDragonkin.DefaultMod.makeCardPath;
@AutoAdd.Ignore
public class Atrocity extends AbstractGremoryCard {


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Atrocity.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("Strike.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION
    private static AbstractCard FollowUp;
    private static final CardRarity RARITY = CardRarity.SPECIAL; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheGremory.Enums.Gremory_Purple_Color;

    private static final int COST = 2;  // COST = 1

    private static final int DAMAGE = 16;    // DAMAGE = 6
    private static final int UPGRADE_PLUS_DMG = 8;  // UPGRADE_PLUS_DMG = 4

    // /STAT DECLARATION/


    public Atrocity(){
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
        this.tags.add(CustomTags.Physical);
        setBackgroundTexture(DefaultMod.AUTUMN_SMALL_ATTACK_FRAME, DefaultMod.AUTUMN_LARGE_ATTACK_FRAME);
        setOrbTexture(DefaultMod.AUTUMN_SMALL_ORB,DefaultMod.AUTUMN_LARGE_ORB);
        baseDamage =DAMAGE;
        magicNumber = baseMagicNumber = 4;
        tags.add(CustomTags.Autumn);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new CleaveEffect()));
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            addToBot(new VFXAction(new GoldenSlashEffect(mo.drawX,mo.drawY,false)));
            addToBot(new VFXAction(new GoldenSlashEffect(mo.drawX,mo.drawY,true)));
        }
        addToBot(new DamageAllEnemiesAction(p,damage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new ApplyPowerAction(p,p,new StrengthPower(p,magicNumber)));
        addToBot(new ApplyPowerAction(p,p,new VigorPower(p,magicNumber)));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(2);
            initializeDescription();
        }
    }
}

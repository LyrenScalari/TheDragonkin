package theDragonkin.cards.GroveKeeper.Choices;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.CollectorCurseEffect;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.cards.GroveKeeper.AbstractChooseOneCard;
import theDragonkin.cards.GroveKeeper.AbstractGroveKeeperCard;
import theDragonkin.characters.TheGroveKeeper;

import static theDragonkin.DefaultMod.makeCardPath;

public class Starlord extends AbstractGroveKeeperCard {
    public static final String ID = DefaultMod.makeID(Starlord.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.SPECIAL;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;
    public static final AbstractCard.CardColor COLOR = TheGroveKeeper.Enums.GroveKeeper_Forest_Color;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public AbstractMonster Target;
    public DamageInfo.DamageType dmgType = DamageInfo.DamageType.NORMAL;
    private static final int COST = 2;
    private static final int DAMAGE = 22;    // DAMAGE = 6
    private static final int UPGRADE_PLUS_DMG = 4;  // UPGRADE_PLUS_DMG = 4
    public Starlord() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
        this.tags.add(CustomTags.Lunar);
        this.setOrbTexture(DefaultMod.Lunar_SMALL_ORB,DefaultMod.Lunar_LARGE_ORB);
    }
    public Starlord(int dmg, AbstractMonster enemy) {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(CustomTags.Lunar);
        this.setOrbTexture(DefaultMod.Lunar_SMALL_ORB,DefaultMod.Lunar_LARGE_ORB);
        damage = baseDamage = dmg;
        Target = enemy;
    }
    public void onChoseThisOption() {
        AbstractChooseOneCard.lastchoice.addToBottom(new Starlord());
        AbstractChooseOneCard.Roadsuntraveled.addToBottom(new StellarDrift());
        AbstractChooseOneCard.lastchoices.addToBottom(new Starlord());
        AbstractChooseOneCard.lastchoices.addToBottom(new StellarDrift());
        addToBot(new DamageAction(Target,new DamageInfo(AbstractDungeon.player,damage,dmgType), AbstractGameAction.AttackEffect.LIGHTNING));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new CollectorCurseEffect(Target.drawX,Target.drawY)));
        addToBot(new DamageAction(Target,new DamageInfo(AbstractDungeon.player,damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.LIGHTNING));
    }

    @Override
    public void upgrade() {
        upgradeDamage(UPGRADE_PLUS_DMG);
    }
}

package theDragonkin.cards.GroveKeeper.Choices;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.CollectorCurseEffect;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.cards.GroveKeeper.AbstractChooseOneCard;
import theDragonkin.cards.GroveKeeper.AbstractGroveKeeperCard;
import theDragonkin.characters.TheGroveKeeper;

import static theDragonkin.DefaultMod.makeCardPath;
import basemod.AutoAdd;

@AutoAdd.Ignore
public class KickintheDoor extends AbstractGroveKeeperCard {
    public static final String ID = DefaultMod.makeID(KickintheDoor.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.SPECIAL;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;
    public static final AbstractCard.CardColor COLOR = TheGroveKeeper.Enums.GroveKeeper_Forest_Color;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public AbstractMonster Target;
    public DamageInfo.DamageType dmgType = DamageInfo.DamageType.NORMAL;
    private static final int COST = 1;
    private static final int DAMAGE = 15;    // DAMAGE = 6
    private static final int UPGRADE_PLUS_DMG = 4;  // UPGRADE_PLUS_DMG = 4
    public KickintheDoor() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
        this.tags.add(CustomTags.Lunar);
        this.setOrbTexture(DefaultMod.Lunar_SMALL_ORB,DefaultMod.Lunar_LARGE_ORB);
    }
    public KickintheDoor(int dmg, AbstractMonster enemy) {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(CustomTags.Neutral);
        damage = baseDamage = dmg;
        Target = enemy;
    }
    public void onChoseThisOption() {
        AbstractChooseOneCard.lastchoice.addToBottom(new KickintheDoor());
        AbstractChooseOneCard.Roadsuntraveled.addToBottom(new LoottheRoom());
        AbstractChooseOneCard.Roadsuntraveled.addToBottom(new FindtheSecret());
        AbstractChooseOneCard.lastchoices.addToBottom(new KickintheDoor());
        AbstractChooseOneCard.lastchoices.addToBottom(new LoottheRoom());
        AbstractChooseOneCard.lastchoice.addToBottom(new FindtheSecret());
        addToTop(new VFXAction(new WhirlwindEffect()));
        addToTop(new DamageAction(Target,new DamageInfo(AbstractDungeon.player,damage,dmgType), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToTop(new WaitAction((float) .02));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new WhirlwindEffect()));
        addToBot(new DamageAction(Target,new DamageInfo(AbstractDungeon.player,damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    @Override
    public void upgrade() {
        upgradeDamage(UPGRADE_PLUS_DMG);
    }
}

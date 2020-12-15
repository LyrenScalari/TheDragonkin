package theDragonkin.cards.GroveKeeper.Choices;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
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
import theDragonkin.cards.GroveKeeper.AbstractGroveKeeperCard;
import theDragonkin.characters.TheGroveKeeper;
import theDragonkin.orbs.InvigoratingBloom;

import static theDragonkin.DefaultMod.makeCardPath;

public class StellarDrift extends AbstractGroveKeeperCard {
    public static final String ID = DefaultMod.makeID(StellarDrift.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.SPECIAL;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;
    public static final AbstractCard.CardColor COLOR = TheGroveKeeper.Enums.GroveKeeper_Forest_Color;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 2;
    private static final int DAMAGE = 10;    // DAMAGE = 6
    private static final int UPGRADE_PLUS_DMG = 4;  // UPGRADE_PLUS_DMG = 4
    public StellarDrift() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(CustomTags.Lunar);
        damage = baseDamage = DAMAGE;
        isMultiDamage = true;
        this.setOrbTexture(DefaultMod.Lunar_SMALL_ORB,DefaultMod.Lunar_LARGE_ORB);
    }
    public void onChoseThisOption() {
        addToBot(new VFXAction(new CollectorCurseEffect(AbstractDungeon.player.drawX,AbstractDungeon.player.drawY)));
        addToBot(new DamageAllEnemiesAction(AbstractDungeon.player,multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.onChoseThisOption();
    }
    @Override
    public void upgrade() {
        upgradeDamage(UPGRADE_PLUS_DMG);
    }
}

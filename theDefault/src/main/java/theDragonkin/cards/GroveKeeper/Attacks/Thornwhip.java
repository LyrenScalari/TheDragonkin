package theDragonkin.cards.GroveKeeper.Attacks;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.cards.GroveKeeper.AbstractGroveKeeperCard;
import theDragonkin.cards.GroveKeeper.Skills.InvigoratingSeeds;
import theDragonkin.characters.TheGroveKeeper;
import theDragonkin.orbs.InvigoratingBloom;
import theDragonkin.orbs.ThornBloom;

import static theDragonkin.DefaultMod.makeCardPath;

public class Thornwhip extends AbstractGroveKeeperCard {


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Thornwhip.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheGroveKeeper.Enums.GroveKeeper_Forest_Color;

    private static final int COST = 1;
    private static final int BLOCK = 7;
    private static final int UPGRADE_PLUS_BLOCK = 4;


    // /STAT DECLARATION/


    public Thornwhip() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        damage = baseDamage = BLOCK;
        magicNumber = baseMagicNumber = 1;
        this.tags.add(CustomTags.Neutral);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m,new DamageInfo(p,damage)));
        addToBot(new ChannelAction(new ThornBloom()));
    }
    public void triggerOnGlowCheck() {
    }
    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_BLOCK);
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}

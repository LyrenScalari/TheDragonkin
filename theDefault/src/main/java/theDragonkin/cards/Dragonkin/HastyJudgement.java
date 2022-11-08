package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import theDragonkin.CustomTags;
import theDragonkin.DragonkinMod;
import theDragonkin.actions.SmiteAction;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.Dragonkin.PenancePower;
import theDragonkin.powers.Dragonkin.Scorchpower;

import java.util.ArrayList;

import static theDragonkin.DragonkinMod.makeCardPath;

public class HastyJudgement extends AbstractHolyCard {


    // TEXT DECLARATION

    public static final String ID = DragonkinMod.makeID(HastyJudgement.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Skill.png");// "public static final String IMG = makeCardPath("HolySmite.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;

    private static final int DAMAGE = 8;
    private static final int UPGRADE_PLUS_DMG = 0;

    // /STAT DECLARATION/


    public HastyJudgement() {
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
        baseDamage = damage = DAMAGE;
        magicNumber = baseMagicNumber = 3;
        tags.add(CustomTags.Smite);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < (magicNumber + DragonkinMod.Seals.size()); ++i) {
            m = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng);
            if (m != null) {
                AbstractMonster finalM = m;
                addToBot(new SmiteAction(m, new DamageInfo(p, damage, damageTypeForTurn)));
            }
        }
        super.use(p,m);
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(2);
            initializeDescription();
        }
    }
}
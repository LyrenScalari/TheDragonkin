package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import theDragonkin.CustomTags;
import theDragonkin.DragonkinMod;
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
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int DAMAGE = 5;
    private static final int UPGRADE_PLUS_DMG = 0;

    // /STAT DECLARATION/


    public HastyJudgement() {
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
        baseDamage = damage = DAMAGE;
        magicNumber = baseMagicNumber = 4;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 2;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractPower> Burdens = new ArrayList<>();
        Burdens.add(new WeakPower(p,magicNumber,false));
        Burdens.add(new VulnerablePower(p,magicNumber,false));
        Burdens.add(new FrailPower(p,magicNumber,false));
        Burdens.add(new Scorchpower(p,p,magicNumber));
        Burdens.add(new PenancePower(p,p,magicNumber));
        Burdens.add(new ConstrictedPower(p,p,magicNumber));
        addToBot(new ApplyPowerAction(p,p,Burdens.get(AbstractDungeon.miscRng.random(Burdens.size()-1))));
        for (AbstractMonster mo: AbstractDungeon.getCurrRoom().monsters.monsters){
            if(!mo.isDeadOrEscaped()){
                for (AbstractPower power : mo.powers){
                    if (power.type == AbstractPower.PowerType.DEBUFF){
                        power.amount += defaultSecondMagicNumber;
                    }
                }
            }
        }
        super.use(p,m);
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(-1);
            upgradeDefaultSecondMagicNumber(1);
            initializeDescription();
        }
    }
}
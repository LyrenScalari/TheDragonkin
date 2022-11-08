package theDragonkin.cards.Dragonkin;

import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import theDragonkin.CardMods.StormEffect;
import theDragonkin.CustomTags;
import theDragonkin.DragonkinMod;
import theDragonkin.actions.InfernoWardAction;
import theDragonkin.cards.Dragonkin.interfaces.StormCard;
import theDragonkin.characters.TheDefault;
import theDragonkin.orbs.BlazeRune;
import theDragonkin.orbs.SparkGlyph;
import theDragonkin.powers.Dragonkin.HeatPower;
import theDragonkin.powers.Dragonkin.PenancePower;
import theDragonkin.powers.Dragonkin.SacrificePower;
import theDragonkin.powers.Dragonkin.Scorchpower;

import java.util.ArrayList;
import java.util.List;

import static theDragonkin.DragonkinMod.makeCardPath;

public class Flashpoint extends AbstractPrimalCard {

    public static final String ID = DragonkinMod.makeID(Flashpoint.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;

    private static final int COST = 0;
    private static final int UPGRADED_COST = 0;
    public Flashpoint() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = 3;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 5;
    }

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
        addToBot(new ApplyPowerAction(p,p,new SacrificePower(p,p,defaultSecondMagicNumber)));
        for (int i = 0; i < magicNumber; i++){
                addToBot(new GainEnergyAction(1));
        }
        super.use(p,m);
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDefaultSecondMagicNumber(2);
            initializeDescription();
        }
    }
}
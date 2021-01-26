package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.Dragonkin.DragonBreaths.SmaugsSmogEffect;

import static theDragonkin.DefaultMod.makeCardPath;

public class Smaugs_Smog extends AbstractPrimalCard {

public static final String ID=DefaultMod.makeID(Smaugs_Smog.class.getSimpleName());
public static final String IMG=makeCardPath("Attack.png");
private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

private static final CardRarity RARITY=CardRarity.RARE;
private static final CardTarget TARGET=CardTarget.SELF;
private static final CardType TYPE=CardType.ATTACK;
public static final CardColor COLOR=TheDefault.Enums.Dragonkin_Red_COLOR;

private static final int COST=2;
private static final int UPGRADED_COST=2;


public Smaugs_Smog() {
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
        damage=baseDamage=12;
        block=baseBlock=6;
        baseMagicNumber=magicNumber=2;
        tags.add(CustomTags.Dragon_Breath);
}

@Override
public void use(AbstractPlayer p,AbstractMonster m) {
        addToBot(new TalkAction(true,cardStrings.EXTENDED_DESCRIPTION[1],(float) 0.5,(float) 2.0));
        addToBot(new ApplyPowerAction(p,p,new SmaugsSmogEffect(baseDamage,block,baseMagicNumber,this)));
}
        @Override
        public void calculateCardDamage(AbstractMonster mo) {
                if (AbstractDungeon.getCurrRoom().monsters.monsters.stream().filter(it -> !it.isDeadOrEscaped()).count() > 1){
                        isMultiDamage = true;
                        int realBaseDamage = this.baseDamage;
                        this.baseDamage =  (int)Math.ceil(((float)realBaseDamage / AbstractDungeon.getCurrRoom().monsters.monsters.stream().filter(it -> !it.isDeadOrEscaped()).count()));
                        int realBaseMagic = this.baseMagicNumber;
                        this.baseMagicNumber = (int) Math.ceil(((float)realBaseMagic / AbstractDungeon.getCurrRoom().monsters.monsters.stream().filter(it -> !it.isDeadOrEscaped()).count()));
                        rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
                        initializeDescription();
                } else {
                        isMultiDamage = false;
                        rawDescription = cardStrings.DESCRIPTION;
                        initializeDescription();
                }
                super.calculateCardDamage(mo);
        }
        @Override
        public void applyPowers() {
                if (AbstractDungeon.getCurrRoom().monsters.monsters.stream().filter(it -> !it.isDeadOrEscaped()).count() > 1){
                        isMultiDamage = true;
                        int realBaseDamage = this.baseDamage;
                        this.baseDamage =  (int)Math.ceil(((float)realBaseDamage / AbstractDungeon.getCurrRoom().monsters.monsters.stream().filter(it -> !it.isDeadOrEscaped()).count()));
                        int realBaseMagic = this.baseMagicNumber;
                        this.baseMagicNumber = (int) Math.ceil(((float)realBaseMagic / AbstractDungeon.getCurrRoom().monsters.monsters.stream().filter(it -> !it.isDeadOrEscaped()).count()));
                        rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
                        initializeDescription();
                } else {
                        isMultiDamage = false;
                        rawDescription = cardStrings.DESCRIPTION;
                        initializeDescription();
                }
                super.applyPowers();
        }
@Override
public void upgrade(){
        if(!upgraded){
        upgradeName();
        upgradeDamage(2);
        upgradeBlock(2);
        upgradeMagicNumber(2);
        initializeDescription();
        }
        }
}
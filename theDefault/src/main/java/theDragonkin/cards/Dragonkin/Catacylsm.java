package theDragonkin.cards.Dragonkin;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.watcher.TriggerMarksAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.AcidMarkPower;
import theDragonkin.powers.Scorchpower;

import static theDragonkin.DefaultMod.makeCardPath;

public class Catacylsm extends AbstractDragonkinCard {

    public static final String ID = DefaultMod.makeID(Catacylsm.class.getSimpleName());
    public static final String IMG = makeCardPath("Catacylsm.png");


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Dragonkin_Red_COLOR;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int POTENCY = 12;
    private static final int UPGRADE_PLUS_POTENCY = 6;
    private static final int MAGIC = 12;
    private static final int UPGRADE_MAGIC = 6;

    public Catacylsm() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = POTENCY;
        block = baseBlock = POTENCY;
        heal = baseHeal = POTENCY;
        baseMagicNumber = magicNumber = MAGIC;
        this.purgeOnUse = true;
        cardsToPreview = new Insanity();

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (mo.hasPower(Scorchpower.POWER_ID)){
                addToBot(new ApplyPowerAction(mo,p,new Scorchpower(mo,p,mo.getPower(Scorchpower.POWER_ID).amount),mo.getPower(Scorchpower.POWER_ID).amount));
            }
            if (mo.hasPower(AcidMarkPower.POWER_ID)){
                addToBot(new ApplyPowerAction(mo,p,new AcidMarkPower(mo,p,mo.getPower(AcidMarkPower.POWER_ID).amount),mo.getPower(AcidMarkPower.POWER_ID).amount));
            }
        }
        AbstractDungeon.actionManager.addToBottom(new TriggerMarksAction(this));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Insanity(),1,false,true));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            initializeDescription();
        }
    }
}
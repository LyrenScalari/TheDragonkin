package theDragonkin.cards.Gremory.Skills;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.cards.Gremory.AbstractGremoryCard;
import theDragonkin.characters.TheGremory;

import java.util.stream.Stream;

import static theDragonkin.DefaultMod.makeCardPath;

public class CautiousStance extends AbstractGremoryCard {


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(CautiousStance.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    public int attackingcount = 0;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheGremory.Enums.Gremory_Purple_Color;

    private static final int COST = 1;
    private static final int BLOCK = 9;
    private static final int UPGRADE_PLUS_BLOCK = 4;


    // /STAT DECLARATION/


    public CautiousStance() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        this.tags.add(CustomTags.Physical);
        this.tags.add(CardTags.STARTER_DEFEND); //Tag your strike, defend and form (Wraith form, Demon form, Echo form, etc.) cards so that they function correctly.
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        for (AbstractMonster mo: AbstractDungeon.getCurrRoom().monsters.monsters){
            if (mo.getIntentBaseDmg() > 0){
                attackingcount += 1;
            }
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    if (AbstractDungeon.getCurrRoom().monsters.monsters.size() > 1 && attackingcount < 1) {
                        addToBot(new ApplyPowerAction(p,p,new BlurPower(p,1),1));
                        isDone = true;
                    }
                    isDone = true;
                }
            });
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }
}

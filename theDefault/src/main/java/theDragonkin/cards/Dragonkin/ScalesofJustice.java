package theDragonkin.cards.Dragonkin;

import basemod.BaseMod;
import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.mod.stslib.actions.common.DamageCallbackAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.DragonkinMod;
import theDragonkin.actions.SmiteAction;
import theDragonkin.characters.TheDefault;
import theDragonkin.util.Wiz;

import java.util.ArrayList;

import static theDragonkin.DragonkinMod.makeCardPath;

public class ScalesofJustice extends AbstractPrimalCard {

    public static final String ID = DragonkinMod.makeID(ScalesofJustice.class.getSimpleName());
    public static final String IMG = makeCardPath("Pandemic.png");


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;

    private static final int COST = 0;
    private static final int UPGRADED_COST = 1;

    private static final int POTENCY = 8;
    private static final int UPGRADE_PLUS_POTENCY = 0;
    private static final int UPGRADE_MAGIC = 0;

    public ScalesofJustice() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = POTENCY;
        magicNumber = baseMagicNumber = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.dmg(m,new DamageInfo(p,damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE);
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                final int[] drawn = {0};
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                            if (c.type == CardType.STATUS && drawn[0] < magicNumber) {
                                AbstractDungeon.player.drawPile.group.remove(c);
                                AbstractDungeon.player.drawPile.addToTop(c);
                                AbstractDungeon.player.draw(1);
                                drawn[0] += 1;
                            }
                        }
                    }});
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        if (drawn[0] < magicNumber){
                            for (AbstractCard c : AbstractDungeon.player.discardPile.group){
                                if (c.type == CardType.STATUS && drawn[0] < magicNumber) {
                                    AbstractDungeon.player.discardPile.group.remove(c);
                                    AbstractDungeon.player.drawPile.addToTop(c);
                                    AbstractDungeon.player.draw(1);
                                    drawn[0] += 1;
                                }
                            }
                        }
                        isDone = true;
                    }
                });
                isDone = true;
            }});
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(2);
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}


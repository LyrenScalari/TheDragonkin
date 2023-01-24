package theDragonkin.cards.Dragonkin;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.vfx.stance.DivinityParticleEffect;
import theDragonkin.CustomTags;
import theDragonkin.DragonkinMod;
import theDragonkin.cards.Dragonkin.interfaces.ReciveDamageEffect;
import theDragonkin.characters.TheDefault;
import theDragonkin.orbs.WisdomSeal;
import theDragonkin.orbs.WrathSeal;

import java.util.ArrayList;
import java.util.List;

import static theDragonkin.DragonkinMod.makeCardPath;

public class ShadowVision extends AbstractPrimalCard {

    public static final String ID = DragonkinMod.makeID(ShadowVision.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private CardGroup ShadowVisons = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;

    private static final int COST = 0;
    private static final int UPGRADED_COST = 1;

    private static final int UPGRADE_PLUS_POTENCY = 0;
    private static final int MAGIC = 5;
    private static final int UPGRADE_MAGIC = 0;
    public ShadowVision() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 2;
        block = baseBlock = 8;
        cardsToPreview = new Burn();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (AbstractDungeon.player.drawPile.size() > defaultSecondMagicNumber){
                    ArrayList<AbstractCard> Visions = new ArrayList<>();
                    for (int i = 0; i < magicNumber; i++){
                        if (i < AbstractDungeon.player.drawPile.size()) {
                            Visions.add(AbstractDungeon.player.drawPile.getNCardFromTop(i));
                        } else {
                            break;
                        }
                    }
                    addToBot(new SelectCardsCenteredAction(Visions,defaultSecondMagicNumber,"Choose "+ defaultSecondMagicNumber + " cards to Add to hand, discard the rest",true,(card)->true,abstractCards ->{
                        for (AbstractCard c : abstractCards) {
                            Visions.remove(c);
                            AbstractDungeon.player.drawPile.removeCard(c);
                            if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE){
                                AbstractDungeon.player.hand.addToTop(c);
                            } else addToTop(new MakeTempCardInDiscardAction(cardsToPreview.makeStatEquivalentCopy(),1));
                        }
                        for (AbstractCard Vision : Visions){
                            AbstractDungeon.player.drawPile.removeCard(Vision);
                            addToTop(new MakeTempCardInDiscardAction(cardsToPreview.makeStatEquivalentCopy(),1));
                        }
                        Visions.clear();
                    }));
                    addToBot(new AbstractGameAction() {
                        @Override
                        public void update() {
                            if (!Visions.isEmpty()){
                                for (AbstractCard Vision : Visions){
                                    AbstractDungeon.player.drawPile.removeCard(Vision);
                                    addToTop(new MakeTempCardInDiscardAction(cardsToPreview.makeStatEquivalentCopy(),1));
                                }
                            }
                            isDone = true;
                        }
                    });
                }
                isDone = true;
            }
        });
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDefaultSecondMagicNumber(1);
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}
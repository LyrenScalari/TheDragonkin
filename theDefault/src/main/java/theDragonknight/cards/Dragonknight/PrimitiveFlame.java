package theDragonknight.cards.Dragonknight;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonknight.CustomTags;
import theDragonknight.DragonknightMod;
import theDragonknight.cards.Dragonknight.Dragonclaws.*;
import theDragonknight.characters.TheDragonknight;
import theDragonknight.orbs.DragonShouts.FlameMark;
import theDragonknight.orbs.DragonShouts.MagmaMark;
import theDragonknight.orbs.DragonShouts.PlagueMark;
import theDragonknight.orbs.DragonShouts.StormMark;
import theDragonknight.util.AbstractDragonMark;
import theDragonknight.util.AbstractNotOrb;
import theDragonknight.util.Wiz;

import java.util.ArrayList;
import java.util.List;

import static theDragonknight.DragonknightMod.*;
import static theDragonknight.DragonknightMod.Seals;

public class PrimitiveFlame extends AbstractDragonknightCard {

    public static final String ID = DragonknightMod.makeID(PrimitiveFlame.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("WindwalkerStrike.png");// "public static final String IMG = makeCardPath("FlameweaverStrike.png");

    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheDragonknight.Enums.Dragonknight_Crimson_Color;
    public AbstractDragonMark brokenscale;
    private static final int COST = 2;  // COST = 1
    private static final int UPGRADED_COST = 1; // UPGRADED_COST = 1

    private static final int DAMAGE = 13;    // DAMAGE = 6
    private static final int UPGRADE_PLUS_DMG = 2;  // UPGRADE_PLUS_DMG = 4

    // /STAT DECLARATION/
    @Override
    public List<String> getCardDescriptors() {
        List<String> tags = new ArrayList<>();
        tags.add(BaseMod.getKeywordTitle("thedragonknight:Draconic"));
        tags.addAll(super.getCardDescriptors());
        return tags;
    }
    public PrimitiveFlame(){
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
        baseDamage =DAMAGE;
        tags.add(CustomTags.Draconic);
        setOrbTexture(DRACONIC_512,DRACONIC_1024);
        cardToPreview.add(new Dragonclaw());
        cardToPreview.add(new ToxicDragonclaw());
        cardToPreview.add(new FlamingDragonclaw());
        cardToPreview.add(new MagmaDragonclaw());
        cardToPreview.add(new StormDragonclaw());
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        brokenscale = null;
        Wiz.dmg(m,new DamageInfo(p,damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE);
        addToBot(new ExhaustAction(1,false,false,false));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (!Seals.isEmpty()) {
                    AbstractDragonMark Scales = (AbstractDragonMark) Seals.get(AbstractDungeon.miscRng.random(0, Seals.size()-1));
                    Scales.WhenRemoved();
                    brokenscale = Scales;
                }
                isDone = true;
            }
        });
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (brokenscale instanceof FlameMark) {
                    addToBot(new MakeTempCardInHandAction(new FlamingDragonclaw()));
                } else if (brokenscale instanceof StormMark) {
                    addToBot(new MakeTempCardInHandAction(new StormDragonclaw()));
                } else if (brokenscale instanceof PlagueMark) {
                    addToBot(new MakeTempCardInHandAction(new ToxicDragonclaw()));
                } else if (brokenscale instanceof MagmaMark) {
                    addToBot(new MakeTempCardInHandAction(new MagmaDragonclaw()));
                } else {
                    addToBot(new MakeTempCardInHandAction(new Dragonclaw()));
                }
                isDone = true;
            }});
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}

package theDragonknight.cards.Dragonknight;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import theDragonknight.CustomTags;
import theDragonknight.DragonknightMod;
import theDragonknight.characters.TheDragonknight;
import theDragonknight.orbs.DragonShouts.PlagueMark;
import theDragonknight.orbs.DragonShouts.StormMark;
import theDragonknight.powers.RotPower;
import theDragonknight.util.AbstractDragonMark;
import theDragonknight.util.AbstractNotOrb;
import theDragonknight.util.Wiz;

import java.util.ArrayList;
import java.util.List;

import static theDragonknight.DragonknightMod.*;
import static theDragonknight.DragonknightMod.DRACONIC_1024;

public class VenomShot extends AbstractDragonknightCard {

    public static final String ID = DragonknightMod.makeID(VenomShot.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("WindwalkerStrike.png");// "public static final String IMG = makeCardPath("FlameweaverStrike.png");

    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheDragonknight.Enums.Dragonknight_Crimson_Color;

    private static final int COST = 0;  // COST = 1
    private static final int UPGRADED_COST = 1; // UPGRADED_COST = 1

    private static final int DAMAGE = 14;    // DAMAGE = 6
    private static final int UPGRADE_PLUS_DMG = 4;  // UPGRADE_PLUS_DMG = 4

    // /STAT DECLARATION/
    @Override
    public List<String> getCardDescriptors() {
        List<String> tags = new ArrayList<>();
        tags.add(BaseMod.getKeywordTitle("thedragonknight:Draconic"));
        tags.addAll(super.getCardDescriptors());
        return tags;
    }
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo("[theDragonknight:PoisonIcon] " + TipHelper.capitalize(GameDictionary.POISON.NAMES[0]), GameDictionary.keywords.get(GameDictionary.POISON.NAMES[0])));
        return retVal;
    }
    public VenomShot(){
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
        baseDamage =DAMAGE;
        magicNumber = baseMagicNumber = 4;
        tags.add(CustomTags.Draconic);
        setOrbTexture(DRACONIC_512,DRACONIC_1024);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToEnemy(m,new PoisonPower(m,AbstractDungeon.player,defaultSecondMagicNumber));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractNotOrb Mark : Seals){
                    if (Mark instanceof PlagueMark){
                        addToBot(new AbstractGameAction() {
                            @Override
                            public void update() {
                                ((PlagueMark) Mark).WhenRemoved();
                                isDone = true;
                            }
                        });
                       isDone = true;
                    }
                }
                if (!isDone && !Seals.isEmpty()) {
                    AbstractDragonMark Scales = (AbstractDragonMark) Seals.get(AbstractDungeon.miscRng.random(0, Seals.size()-1));
                    Scales.WhenRemoved();
                }
                isDone = true;
            }
        });
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
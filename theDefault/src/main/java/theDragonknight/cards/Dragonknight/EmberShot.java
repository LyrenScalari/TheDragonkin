package theDragonknight.cards.Dragonknight;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import theDragonknight.CustomTags;
import theDragonknight.DragonknightMod;
import theDragonknight.characters.TheDragonknight;
import theDragonknight.orbs.DragonShouts.FlameMark;
import theDragonknight.orbs.DragonShouts.MagmaMark;
import theDragonknight.orbs.DragonShouts.StormMark;
import theDragonknight.util.AbstractDragonMark;
import theDragonknight.util.AbstractNotOrb;
import theDragonknight.util.Wiz;

import java.util.ArrayList;
import java.util.List;

import static theDragonknight.DragonknightMod.*;
import static theDragonknight.DragonknightMod.DRACONIC_1024;

public class EmberShot extends AbstractDragonknightCard {

    public static final String ID = DragonknightMod.makeID(EmberShot.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("WindwalkerStrike.png");// "public static final String IMG = makeCardPath("FlameweaverStrike.png");

    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheDragonknight.Enums.Dragonknight_Crimson_Color;

    private static final int COST = 1;  // COST = 1
    private static final int UPGRADED_COST = 1; // UPGRADED_COST = 1

    private static final int DAMAGE = 4;    // DAMAGE = 6
    private static final int UPGRADE_PLUS_DMG = 4;  // UPGRADE_PLUS_DMG = 4

    // /STAT DECLARATION/
    @Override
    public List<String> getCardDescriptors() {
        List<String> tags = new ArrayList<>();
        tags.add(BaseMod.getKeywordTitle("thedragonknight:Draconic"));
        tags.addAll(super.getCardDescriptors());
        return tags;
    }
    public EmberShot(){
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
        baseDamage =DAMAGE;
        tags.add(CustomTags.Draconic);
        setOrbTexture(DRACONIC_512,DRACONIC_1024);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.dmg(m,new DamageInfo(p,damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE);
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractNotOrb Mark : Seals){
                    if (Mark instanceof FlameMark){
                        addToBot(new AbstractGameAction() {
                            @Override
                            public void update() {
                                ((FlameMark) Mark).WhenRemoved();
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
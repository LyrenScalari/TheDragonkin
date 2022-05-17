package theDragonknight.cards.Dragonknight;

import basemod.BaseMod;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.CommonKeywordIconsField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonknight.CustomTags;
import theDragonknight.DragonknightMod;
import theDragonknight.characters.TheDragonknight;
import theDragonknight.orbs.DragonShouts.FlameMark;
import theDragonknight.orbs.DragonShouts.MagmaMark;
import theDragonknight.util.AbstractNotOrb;

import java.util.ArrayList;
import java.util.List;

import static theDragonknight.DragonknightMod.*;
import static theDragonknight.DragonknightMod.DRACONIC_1024;

public class WorldinFlames extends AbstractDragonknightCard {

    public static final String ID = DragonknightMod.makeID(WorldinFlames.class.getSimpleName());
    public static final String IMG = makeCardPath("WindWalkerDefend.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDragonknight.Enums.Dragonknight_Crimson_Color;

    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int UPGRADE_PLUS_BLOCK = 3;

    public WorldinFlames() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        tags.add(CustomTags.Draconic);
        CommonKeywordIconsField.useIcons.set(this,true);
        setOrbTexture(DRACONIC_512,DRACONIC_1024);
        exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractNotOrb mark : DragonknightMod.Seals){
                    if (mark instanceof FlameMark){
                        for (int i = 1 ; i < mark.PainAmount ; i++){
                            ((FlameMark) mark).TriggerPassive();
                            ((FlameMark) mark).WhenRemoved();
                        }
                    }
                    if (mark instanceof MagmaMark){
                        for (int i = 1 ; i < mark.PainAmount ; i++){
                            ((MagmaMark) mark).TriggerPassive();
                            ((MagmaMark) mark).WhenRemoved();
                        }
                    }
                }
                isDone = true;
            }
        });
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            initializeDescription();
        }
    }
}
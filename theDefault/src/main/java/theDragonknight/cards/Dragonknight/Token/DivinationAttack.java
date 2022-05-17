package theDragonknight.cards.Dragonknight.Token;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.CommonKeywordIconsField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.ExhaustToHandAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.MiracleEffect;
import theDragonknight.CustomTags;
import theDragonknight.DragonknightMod;
import theDragonknight.cards.Dragonknight.AbstractDragonknightCard;
import theDragonknight.cards.Dragonknight.LightningStrike;
import theDragonknight.characters.TheDragonknight;
import theDragonknight.orbs.DragonShouts.StormMark;
import theDragonknight.powers.ProfaneDivitation;
import theDragonknight.util.AbstractNotOrb;

import java.util.ArrayList;
import java.util.List;

import static theDragonknight.DragonknightMod.makeCardPath;

public class DivinationAttack extends AbstractDragonknightCard {

    public static final String ID = DragonknightMod.makeID(DivinationAttack.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("WindwalkerStrike.png");// "public static final String IMG = makeCardPath("FlameweaverStrike.png");

    private static final CardRarity RARITY = CardRarity.SPECIAL; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -2;  // COST = 1
    private static final int UPGRADED_COST = 1; // UPGRADED_COST = 1

    private static final int DAMAGE = 4;    // DAMAGE = 6
    private static final int UPGRADE_PLUS_DMG = 4;  // UPGRADE_PLUS_DMG = 4

    // /STAT DECLARATION/
    public DivinationAttack(){
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
    }


    // Actions the card should do.
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.onChoseThisOption();
    }

    public void onChoseThisOption() {
        ProfaneDivitation.DivinedType.add(this.makeStatEquivalentCopy());
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
    }
}
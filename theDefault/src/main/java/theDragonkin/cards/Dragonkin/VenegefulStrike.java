package theDragonkin.cards.Dragonkin;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ViolentAttackEffect;
import theDragonkin.DragonkinMod;
import theDragonkin.cards.Dragonkin.interfaces.ReciveDamageEffect;
import theDragonkin.characters.TheDefault;

import java.util.ArrayList;
import java.util.List;

import static theDragonkin.DragonkinMod.makeCardPath;

public class VenegefulStrike extends AbstractHolyCard implements ReciveDamageEffect {

    public static final String ID = DragonkinMod.makeID(VenegefulStrike.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int UPGRADE_PLUS_POTENCY = 0;
    private static final int MAGIC = 3;
    private static final int UPGRADE_MAGIC = 0;

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Blessing"),BaseMod.getKeywordDescription("thedragonkin:Blessing")));
        return retVal;
    }
    public VenegefulStrike() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        damage = baseDamage = 8;
        selfRetain = true;
        this.tags.add(CardTags.STRIKE);

    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new ViolentAttackEffect(m.drawX,m.drawY, Color.GOLD)));
        addToBot(new DamageAction(m,new DamageInfo(p,damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SMASH));
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

    @Override
    public void onReciveDamage(int damage) {
        if (AbstractDungeon.player.hand.contains(this)) {
            this.baseDamage += magicNumber;
            this.damage = baseDamage;
        }
    }
}
package theDragonkin.cards.Dragonkin;

import IconsAddon.cardmods.AddIconToDescriptionMod;
import IconsAddon.icons.FireIcon;
import IconsAddon.util.DamageModifierManager;
import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NoDrawPower;
import theDragonkin.DamageModifiers.FireDamage;
import theDragonkin.DragonkinMod;
import theDragonkin.characters.TheDefault;
import theDragonkin.util.TriggerOnCycleEffect;

import static theDragonkin.DragonkinMod.makeCardPath;

public class Cinderstrike extends AbstractPrimalCard implements TriggerOnCycleEffect {

    public static final String ID = DragonkinMod.makeID(Cinderstrike.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.Justicar_Red_COLOR;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int POTENCY = 8;
    private static final int UPGRADE_PLUS_DMG = 2;
    private static final int MAGIC = 2;
    private static final int UPGRADE_MAGIC = 2;
    public static int burnCount = 0;
    public Cinderstrike() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = POTENCY;
        block = baseBlock = POTENCY;
        heal = baseHeal = POTENCY;
        baseMagicNumber = magicNumber = MAGIC;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 2;
        tags.add(CardTags.STRIKE);
        DamageModifierManager.addModifier(this, new FireDamage(true,true));
        CardModifierManager.addModifier(this,new AddIconToDescriptionMod(AddIconToDescriptionMod.DAMAGE, FireIcon.get()));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL)));
        if (!(AbstractDungeon.player.hand.size() >= BaseMod.MAX_HAND_SIZE) || !AbstractDungeon.player.hasPower(NoDrawPower.POWER_ID)){
            int burncount = 0;
            for (AbstractCard c : AbstractDungeon.player.drawPile.group){
                if (c instanceof Burn){
                    burncount++;
                    if (burncount >= magicNumber){
                        break;
                    }
                    addToBot(new AbstractGameAction() {
                        @Override
                        public void update() {
                            AbstractDungeon.player.drawPile.group.remove(c);
                            AbstractDungeon.player.drawPile.addToTop(c);
                            isDone = true; }
                    });
                    addToBot(new DrawCardAction(1));
                }
            }
        }
        super.use(p,m);
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void TriggerOnCycle(AbstractCard ca) {
        if (ca.type == CardType.STATUS) {
            baseDamage += magicNumber;
        }
    }
}
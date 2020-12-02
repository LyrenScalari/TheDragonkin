package theDragonkin.cards.Gremory.Powers;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.CardMods.BlutgangEnchantment;
import theDragonkin.DefaultMod;
import theDragonkin.cards.Gremory.AbstractGremoryCard;
import theDragonkin.cards.Gremory.AbstractMagicGremoryCard;
import theDragonkin.characters.TheGremory;
import theDragonkin.CustomTags;

import static theDragonkin.DefaultMod.makeCardPath;

public class Blutgang extends AbstractGremoryCard {


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Blutgang.class.getSimpleName());
    public static final String IMG = makeCardPath("Power.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheGremory.Enums.Gremory_Purple_Color;

    private static final int COST = 2;
    private static final int BLOCK = 5;
    private static final int UPGRADE_PLUS_BLOCK = 3;


    // /STAT DECLARATION/


    public Blutgang() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        this.tags.add(CustomTags.Physical);
        this.tags.add(CustomTags.Relic);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsInHandAction(1,"Enchant",false,true, c -> !(c.hasTag(CustomTags.Enchanted) || c instanceof AbstractMagicGremoryCard),
                list -> list.forEach(this::enchant)));
        addToBot(new ApplyPowerAction(p,p,new theDragonkin.powers.Blutgang(p,p)));
        for (AbstractPower po : p.powers){
            if (po.ID.equals(theDragonkin.powers.Blutgang.POWER_ID)){
                addToBot(new RemoveSpecificPowerAction(p,p,po));
            }
        }
    }
    public AbstractCard enchant(AbstractCard c){

        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                CardModifierManager.addModifier(c, new BlutgangEnchantment());
                isDone = true;
            }
        });
        if (StSLib.getMasterDeckEquivalent(c) != null) {
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    CardModifierManager.addModifier(StSLib.getMasterDeckEquivalent(c), new BlutgangEnchantment());
                    isDone = true;
                }
            });
        }
        return c;
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

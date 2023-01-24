package theDragonkin.cards.Dragonkin;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.CommonKeywordIconsField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.ExceptionHandler;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.FireBurstParticleEffect;
import theDragonkin.CustomTags;
import theDragonkin.DragonkinMod;
import theDragonkin.cards.AbstractDefaultCard;
import theDragonkin.cards.Dragonkin.interfaces.StormCard;
import theDragonkin.powers.Dragonkin.DivineConvictionpower;
import theDragonkin.powers.Dragonkin.FuryPower;
import theDragonkin.util.TypeEnergyHelper;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public abstract class AbstractDragonkinCard extends AbstractDefaultCard {

    // "How come BlazingBreath extends CustomCard and not DynamicCard like all the rest?"

    // Well every card, at the end of the day, extends CustomCard.
    // Abstract Default Card extends CustomCard and builds up on it, adding a second magic number. Your card can extend it and
    // bam - you can have a second magic number in that card (Learn Java inheritance if you want to know how that works).
    // Abstract Dynamic Card builds up on Abstract Default Card even more and makes it so that you don't need to add
    // the NAME and the DESCRIPTION into your card - it'll get it automatically. Of course, this functionality could have easily
    // Been added to the default card rather than creating a new Dynamic one, but was done so to deliberately.
    public static CardStrings tooltip = CardCrawlGame.languagePack.getCardStrings("theDragonkin:AbstractDragonkinCard");
    public AbstractDragonkinCard(final String id,
                                 final String img,
                                 final int cost,
                                 final CardType type,
                                 final CardColor color,
                                 final CardRarity rarity,
                                 final CardTarget target) {

        super(id, languagePack.getCardStrings(id).NAME, img, cost, languagePack.getCardStrings(id).DESCRIPTION, type, color, rarity, target);
        CommonKeywordIconsField.useIcons.set(this,true);
    }
    public int realBaseDamage;
    public int realBaseMagic;
    public EnumMap<TypeEnergyHelper.Mana, Integer> energyCosts = new EnumMap<>(TypeEnergyHelper.Mana.class);
    protected int[] elementCost = new int[8];
    private boolean needsArtRefresh = false;
    private static Texture Bloodcast, Soulshatter;
    public Color renderColor = Color.WHITE.cpy();
    public static boolean alwaysFreeToCast = false;
    public boolean freeManaOnce = false;
    public static boolean alwaysFreeToShatter = false;
    public boolean freeShatterOnce = false;
    @Override
    public void atTurnStart() {
        super.atTurnStart();
        DragonkinMod.WasDrained = false;
    }

    @Override
    public void applyPowers(){
        if (baseSecondDamage > -1) {
            secondDamage = baseSecondDamage;

            int tmp = baseDamage;
            baseDamage = baseSecondDamage;

            super.applyPowers();

            secondDamage = damage;
            baseDamage = tmp;

            super.applyPowers();

            isSecondDamageModified = (secondDamage != baseSecondDamage);
        } else super.applyPowers();
    }
    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        if (baseSecondDamage > -1) {
            secondDamage = baseSecondDamage;

            int tmp = baseDamage;
            baseDamage = baseSecondDamage;

            super.calculateCardDamage(mo);

            secondDamage = damage;
            baseDamage = tmp;

            super.calculateCardDamage(mo);

            isSecondDamageModified = (secondDamage != baseSecondDamage);
        } else super.calculateCardDamage(mo);
    }
    public void resetAttributes() {
        super.resetAttributes();
        secondDamage = baseSecondDamage;
        isSecondDamageModified = false;
    }
    protected void upgradeSecondDamage(int amount) {
        baseSecondDamage += amount;
        secondDamage = baseSecondDamage;
        upgradedSecondDamage = true;
    }
    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedSecondDamage) {
            secondDamage = baseSecondDamage;
            isSecondDamageModified = true;
        }
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this instanceof StormCard && this.costForTurn == 0){
            ((StormCard) this).onStorm();
        }
    }
    public int[] getManaCost(){ return elementCost;}
    public static void renderManaCost(AbstractDragonkinCard card, SpriteBatch sb){
        int hasEnoughExalt = TypeEnergyHelper.getManaByEnum(TypeEnergyHelper.Mana.Exalt);
        float drawX = card.current_x - 256.0F;
        float drawY = card.current_y - 256.0F;
        boolean loop1 = false;
        boolean loop2 = false;
        if(Bloodcast == null){
            Bloodcast = ImageMaster.loadImage("theDragonkinResources/images/512/ExaltIcon.png");
        }

        if(!card.isLocked && card.isSeen) {
            float yOffset = 30.0F * Settings.scale * card.drawScale;
            int counter = 0;
            //logger.info("attempting render");
            for(Map.Entry<TypeEnergyHelper.Mana,Integer> e : card.energyCosts.entrySet()){
                TypeEnergyHelper.Mana mana = e.getKey();
                Texture tex;
                switch (mana) {
                    case Exalt:
                        tex = Bloodcast;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected mana type");
                }
                if (card.energyCosts.get(mana) != 0) {
                    Vector2 offset = new Vector2(0, -yOffset * counter);
                    offset.rotate(card.angle);
                    card.renderHelper(sb, card.renderColor, tex, drawX + offset.x, drawY + offset.y);
                    String msg;
                    msg = card.energyCosts.get(mana) + "";

                    Color costColor = Color.WHITE.cpy();
                    if (AbstractDungeon.player != null && AbstractDungeon.player.hand.contains(card)){
                            if (AbstractDungeon.player.hasPower(DivineConvictionpower.POWER_ID)) {
                                if (AbstractDungeon.player.getPower(DivineConvictionpower.POWER_ID).amount <= hasEnoughExalt){
                                    costColor = Color.RED.cpy();
                                } else if (alwaysFreeToCast || card.freeManaOnce) {
                                    msg = "0";
                                    costColor = Color.GREEN.cpy();
                                } else costColor = Color.WHITE.cpy();
                            } else if (alwaysFreeToCast || card.freeManaOnce) {
                                msg = "0";
                                costColor = Color.GREEN.cpy();
                            } else costColor = Color.RED.cpy();
                    }
                    FontHelper.renderRotatedText(sb, getElementFont(card), msg, card.current_x,
                            card.current_y, -136.0F * card.drawScale * Settings.scale,
                            139.0F * card.drawScale * Settings.scale - yOffset * counter, card.angle,
                            true, costColor);
                    counter++;
                }
            }
        }
    }
    private void renderHelper(SpriteBatch sb, Color color, Texture img, float drawX, float drawY) {
        sb.setColor(color);
        try {
            sb.draw(img, drawX, drawY,
                    256.0F, 256.0F, 512.0F, 512.0F,
                    this.drawScale * Settings.scale, this.drawScale * Settings.scale,
                    this.angle, 0, 0, 512, 512, false, false);

        } catch (Exception e) {
            ExceptionHandler.handleException(e, DragonkinMod.logger);
        }
    }
    public static BitmapFont getElementFont(AbstractCard card) {
        FontHelper.cardEnergyFont_L.getData().setScale(card.drawScale * 0.75f);
        return FontHelper.cardEnergyFont_L;
    }
}
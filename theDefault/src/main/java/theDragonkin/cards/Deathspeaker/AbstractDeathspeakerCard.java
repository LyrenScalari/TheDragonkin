package theDragonkin.cards.Deathspeaker;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.evacipated.cardcrawl.modthespire.lib.SpireSuper;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.FireBurstParticleEffect;
import jdk.nashorn.internal.ir.annotations.Ignore;
import theDragonkin.DragonkinMod;
import theDragonkin.cards.AbstractDefaultCard;
import theDragonkin.cards.Dragonkin.interfaces.StormCard;
import theDragonkin.powers.Deathspeaker.ManaPower;
import theDragonkin.powers.Dragonkin.FuryPower;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static theDragonkin.DragonkinMod.Power_Acolyte;
import static theDragonkin.DragonkinMod.Power_Acolyte_PORTRAIT;

public abstract class AbstractDeathspeakerCard extends AbstractDefaultCard {

    // "How come BlazingBreath extends CustomCard and not DynamicCard like all the rest?"

    // Well every card, at the end of the day, extends CustomCard.
    // Abstract Default Card extends CustomCard and builds up on it, adding a second magic number. Your card can extend it and
    // bam - you can have a second magic number in that card (Learn Java inheritance if you want to know how that works).
    // Abstract Dynamic Card builds up on Abstract Default Card even more and makes it so that you don't need to add
    // the NAME and the DESCRIPTION into your card - it'll get it automatically. Of course, this functionality could have easily
    // Been added to the default card rather than creating a new Dynamic one, but was done so to deliberately.
    private Color typeColor; //defined in your abstract
    private Color renderColor;
    public AbstractDeathspeakerCard(final String id, final String img, final int cost, final CardType type, final CardColor color, final CardRarity rarity, final CardTarget target) {
        super(id, languagePack.getCardStrings(id).NAME, img, cost, languagePack.getCardStrings(id).DESCRIPTION, type, color, rarity, target);
        typeColor = ReflectionHacks.getPrivate(this,AbstractCard.class,"typeColor");
        renderColor = typeColor;
        if (this.type == Enums.SPELL) {
            setBackgroundTexture(Power_Acolyte,Power_Acolyte_PORTRAIT);
        }

    }
    public static class Enums {
        @SpireEnum
        public static AbstractCard.CardType SPELL;
    }
    public int realBaseDamage;
    public int realBaseMagic;
    public int secondDamage;
    public int baseSecondDamage;
    public boolean upgradedSecondDamage;
    public boolean isSecondDamageModified;
    public UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("TheDragonkin:SpellCard");
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
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        AbstractPower mana = p.getPower(ManaPower.POWER_ID);
        if (this.type == Enums.SPELL && mana == null) {
            return false;
        } else if (this.type == Enums.SPELL && mana.amount < this.costForTurn) {
            return false;
        } else if (this.type == Enums.SPELL) {
            return true;
        } else {
            super.canUse(p,m);
        }
        return false;
    }
    @SpireOverride
    private void renderType(SpriteBatch sb) {
        if (this.type == Enums.SPELL){
            String text = uiStrings.TEXT[0];
            BitmapFont font = FontHelper.cardTypeFont;
            font.getData().setScale(this.drawScale);
            this.typeColor.a = this.renderColor.a;
            FontHelper.renderRotatedText(sb, font, text, this.current_x, this.current_y - 22.0F * this.drawScale * Settings.scale, 0.0F, -1.0F * this.drawScale * Settings.scale, this.angle, false, this.typeColor);
        } else {
            SpireSuper.call(sb);
        }
    }
    public void setDisplayRarity(CardRarity rarity) {
        switch(rarity) {
            case BASIC:
            case CURSE:
            case SPECIAL:
            case COMMON:
                this.bannerSmallRegion = ImageMaster.CARD_BANNER_COMMON;
                this.bannerLargeRegion = ImageMaster.CARD_BANNER_COMMON_L;
                if (this.type == CardType.ATTACK){
                    this.frameSmallRegion = ImageMaster.CARD_FRAME_ATTACK_COMMON;
                    this.frameLargeRegion = ImageMaster.CARD_FRAME_ATTACK_COMMON_L;
                } else if (this.type == CardType.POWER || this.type == Enums.SPELL){
                    this.frameSmallRegion = ImageMaster.CARD_FRAME_POWER_COMMON;
                    this.frameLargeRegion = ImageMaster.CARD_FRAME_POWER_COMMON_L;
                } else {
                    this.frameSmallRegion = ImageMaster.CARD_FRAME_SKILL_COMMON;
                    this.frameLargeRegion = ImageMaster.CARD_FRAME_SKILL_COMMON_L;
                }
                this.frameMiddleRegion = ImageMaster.CARD_COMMON_FRAME_MID;
                this.frameLeftRegion = ImageMaster.CARD_COMMON_FRAME_LEFT;
                this.frameRightRegion = ImageMaster.CARD_COMMON_FRAME_RIGHT;
                this.frameMiddleLargeRegion = ImageMaster.CARD_COMMON_FRAME_MID_L;
                this.frameLeftLargeRegion = ImageMaster.CARD_COMMON_FRAME_LEFT_L;
                this.frameRightLargeRegion = ImageMaster.CARD_COMMON_FRAME_RIGHT_L;
                break;
            case UNCOMMON:
                this.bannerSmallRegion = ImageMaster.CARD_BANNER_UNCOMMON;
                this.bannerLargeRegion = ImageMaster.CARD_BANNER_UNCOMMON_L;
                if (this.type == CardType.ATTACK){
                    this.frameSmallRegion = ImageMaster.CARD_FRAME_ATTACK_UNCOMMON;
                    this.frameLargeRegion = ImageMaster.CARD_FRAME_ATTACK_UNCOMMON_L;
                } else if (this.type == CardType.POWER || this.type == Enums.SPELL){
                    this.frameSmallRegion = ImageMaster.CARD_FRAME_POWER_UNCOMMON;
                    this.frameLargeRegion = ImageMaster.CARD_FRAME_POWER_COMMON_L;
                } else {
                    this.frameSmallRegion = ImageMaster.CARD_FRAME_SKILL_UNCOMMON;
                    this.frameLargeRegion = ImageMaster.CARD_FRAME_SKILL_UNCOMMON_L;
                }

                this.frameMiddleRegion = ImageMaster.CARD_UNCOMMON_FRAME_MID;
                this.frameLeftRegion = ImageMaster.CARD_UNCOMMON_FRAME_LEFT;
                this.frameRightRegion = ImageMaster.CARD_UNCOMMON_FRAME_RIGHT;
                this.frameMiddleLargeRegion = ImageMaster.CARD_UNCOMMON_FRAME_MID_L;
                this.frameLeftLargeRegion = ImageMaster.CARD_UNCOMMON_FRAME_LEFT_L;
                this.frameRightLargeRegion = ImageMaster.CARD_UNCOMMON_FRAME_RIGHT_L;
                break;
            case RARE:
                this.bannerSmallRegion = ImageMaster.CARD_BANNER_RARE;
                this.bannerLargeRegion = ImageMaster.CARD_BANNER_RARE_L;
                if (this.type == CardType.ATTACK){
                    this.frameSmallRegion = ImageMaster.CARD_FRAME_ATTACK_RARE;
                    this.frameLargeRegion = ImageMaster.CARD_FRAME_ATTACK_RARE_L;
                } else if (this.type == CardType.POWER || this.type == Enums.SPELL){
                    this.frameSmallRegion = ImageMaster.CARD_FRAME_POWER_RARE;
                    this.frameLargeRegion = ImageMaster.CARD_FRAME_POWER_RARE_L;
                } else {
                    this.frameSmallRegion = ImageMaster.CARD_FRAME_SKILL_RARE;
                    this.frameLargeRegion = ImageMaster.CARD_FRAME_SKILL_RARE_L;
                }
                this.frameMiddleRegion = ImageMaster.CARD_RARE_FRAME_MID;
                this.frameLeftRegion = ImageMaster.CARD_RARE_FRAME_LEFT;
                this.frameRightRegion = ImageMaster.CARD_RARE_FRAME_RIGHT;
                this.frameMiddleLargeRegion = ImageMaster.CARD_RARE_FRAME_MID_L;
                this.frameLeftLargeRegion = ImageMaster.CARD_RARE_FRAME_LEFT_L;
                this.frameRightLargeRegion = ImageMaster.CARD_RARE_FRAME_RIGHT_L;
                break;
            default:
                System.out.println("Attempted to set display rarity to an unknown rarity: " + rarity.name());
        }

    }
}
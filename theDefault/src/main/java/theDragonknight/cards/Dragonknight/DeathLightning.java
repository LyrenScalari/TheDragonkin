package theDragonknight.cards.Dragonknight;

import basemod.BaseMod;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.CommonKeywordIconsField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import theDragonknight.CustomTags;
import theDragonknight.DragonknightMod;
import theDragonknight.characters.TheDragonknight;
import theDragonknight.orbs.DragonShouts.StormMark;
import theDragonknight.powers.DeathBlightPower;
import theDragonknight.powers.FrostbitePower;
import theDragonknight.util.AbstractDragonMark;
import theDragonknight.util.AbstractNotOrb;
import theDragonknight.util.Wiz;

import java.util.ArrayList;
import java.util.List;

import static theDragonknight.DragonknightMod.*;
import static theDragonknight.DragonknightMod.Seals;

public class DeathLightning extends AbstractDragonknightCard {

    public static final String ID = DragonknightMod.makeID(DeathLightning.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("WindwalkerStrike.png");// "public static final String IMG = makeCardPath("FlameweaverStrike.png");

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheDragonknight.Enums.Dragonknight_Crimson_Color;

    private static final int COST = 1;  // COST = 1
    private static final int UPGRADED_COST = 1; // UPGRADED_COST = 1

    private static final int DAMAGE = 8;    // DAMAGE = 6
    private static final int UPGRADE_PLUS_DMG = 4;  // UPGRADE_PLUS_DMG = 4

    // /STAT DECLARATION/
    @Override
    public List<String> getCardDescriptors() {
        List<String> tags = new ArrayList<>();
        tags.add(BaseMod.getKeywordTitle("thedragonknight:Draconic"));
        tags.addAll(super.getCardDescriptors());
        return tags;
    }
    public DeathLightning(){
        super(ID,IMG,COST,TYPE,COLOR,RARITY,TARGET);
        baseDamage =DAMAGE;
        magicNumber = baseMagicNumber = 10;
        tags.add(CustomTags.Draconic);
        setOrbTexture(DRACONIC_512,DRACONIC_1024);
        exhaust = true;
        CommonKeywordIconsField.useIcons.set(this,true);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractNotOrb Mark : Seals){
                    if (Mark instanceof StormMark){
                        addToBot(new AbstractGameAction() {
                            @Override
                            public void update() {
                                AbstractMonster target = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
                                CardCrawlGame.sound.play("ORB_LIGHTNING_EVOKE", 0.0f);
                                addToBot(new VFXAction(new LightningEffect(target.hb.cX,target.hb.cY)));
                                ((StormMark)Mark).TriggerPassive(target);
                                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                                    if (m.hasPower(FrostbitePower.POWER_ID)) {
                                        AbstractDungeon.actionManager.addToBottom(new VFXAction(new LightningEffect(m.hb.cX, m.hb.cY)));
                                        Wiz.applyToEnemy(m,new DeathBlightPower(m,p,magicNumber));
                                    }
                                }
                                ((StormMark)Mark).WhenRemoved();
                                isDone = true;
                            }
                        });
                        isDone = true;
                    }
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